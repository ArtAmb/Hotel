package hotel.ejb.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.mysql.jdbc.StringUtils;

import hotel.Utils;
import hotel.dao.BookingDAO;
import hotel.dao.ClientDAO;
import hotel.dao.RoomDAO;
import hotel.dao.UserDAO;
import hotel.domain.Booking;
import hotel.domain.BookingStatus;
import hotel.domain.Client;
import hotel.domain.Feature;
import hotel.domain.Room;
import hotel.domain.User;
import hotel.domain.UserRole;
import hotel.ejb.services.SessionManagerService;
import hotel.ejb.services.SessionObject;
import hotel.ejb.services.payment.PayuService;
import hotel.ejb.services.payment.dto.PayuOrderResponse;
import hotel.ejb.services.rooms.RoomService;
import hotel.ejb.services.rooms.dto.RoomDTO;
import hotel.ejb.services.utils.RandomStringGenerator;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Artur Ambrolewicz
 */
@SessionScoped
@Named
@Data
public class BookingController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date startDateDATE = new Date(System.currentTimeMillis());
	private Date endDateDATE = Utils.addDays(startDateDATE, 5);
	
	private String startDate = startDateDATE.toString();
	private String endDate = endDateDATE.toString();
	
	private List<Long> features = new LinkedList<>();

	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	
	private Booking query = new Booking();
	private Client clientData = new Client();
	private String errorMessage = "";
	private String message = "";
	
	private BigDecimal totalCost = new BigDecimal(0);
	private BigDecimal fee = new BigDecimal(totalCost.toString()).multiply(new BigDecimal(0.10));
	
	@EJB
	private UserDAO userDAO;
	@EJB
	private BookingDAO bookingDAO;

	@EJB
	private RoomService roomService;

	@EJB
	private PayuService payuService;

	@EJB
	private RoomDAO roomDAO;

	@EJB
	private ClientDAO clientDAO;

	@EJB
	private SessionManagerService sessionManagerService;
	
	private List<Room> rooms;
	
	@Inject
	private MainMenuController mainMenuController;

	public String saveBooking() {
		Booking booking = new Booking();
		booking.setStartDate(startDateDATE);
		booking.setEndDate(endDateDATE);

		bookingDAO.save(booking);

		return "booking";
	}

	public String toDefaultValue() {
		startDateDATE = new Date(System.currentTimeMillis());
		endDateDATE = Utils.addDays(startDateDATE, 5);
		startDate = startDateDATE.toString();
		endDate = endDateDATE.toString(); 
		
		return null;
	}
	
	public List<Booking> findAllBookings() {
		return bookingDAO.findByHotel(mainMenuController.getChosenHotel());
	}

	public Booking findOne(Long id) {
		return bookingDAO.findOne(id);
	}

	public List<RoomDTO> findByQuery() {

		List<Room> rooms = roomDAO.findByQuery(Room.builder().hotel(mainMenuController.getChosenHotel()).build());

		List<RoomDTO> avaliableRooms = rooms.stream().filter(
				r -> r.getFeatures().stream().map(Feature::getId).collect(Collectors.toList()).containsAll(features))
				.map(r -> roomService.findRoomBookings(r, startDateDATE, endDateDATE)).filter(dto->dto!=null).collect(Collectors.toList());

		return avaliableRooms;
	}

	public String findBooking() {
		return Utils.getViewUrl("visits/visits-results");
	}

	public String refreshView() {
		try {
			startDateDATE = StringUtils.isNullOrEmpty(startDate) ? null : new Date(sdf.parse(startDate).getTime());
			endDateDATE = StringUtils.isNullOrEmpty(endDate) ? null : new Date(sdf.parse(endDate).getTime());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		startDate = startDateDATE.toString();
		endDate = endDateDATE.toString(); 
		
		return "booking";
	}

	void validate() {
		if (StringUtils.isNullOrEmpty(clientData.getName()))
			throw new IllegalStateException("Imie jest wymagane");
		if (StringUtils.isNullOrEmpty(clientData.getSurname()))
			throw new IllegalStateException("Nazwisko jest wymagane");
		if (StringUtils.isNullOrEmpty(clientData.getEmail()))
			throw new IllegalStateException("Emial jest wymagany");
		if (StringUtils.isNullOrEmpty(clientData.getPhone()))
			throw new IllegalStateException("Telefon jest wymagany");
		if (StringUtils.isNullOrEmpty(clientData.getStreet()))
			throw new IllegalStateException("Ulica jest wymagana");
		if (StringUtils.isNullOrEmpty(clientData.getHomeNr()))
			throw new IllegalStateException("Numer domu jest wymagany");
		if (StringUtils.isNullOrEmpty(clientData.getPhone()))
			throw new IllegalStateException("Telefon jest wymagany");
		if (StringUtils.isNullOrEmpty(clientData.getCity()))
			throw new IllegalStateException("Miasto jest wymagany");
	}

	public String makeBooking() throws IOException {
		User user = AuthorizationController.getUser();
		try {
			validate();

			clientData.setUser(user);
			Optional<Client> optionalClient = clientDAO
					.findByQuery(Client.builder().email(clientData.getEmail()).build()).stream().findFirst();

			Client newClient = null;
			if (optionalClient.isPresent()) {
				newClient = optionalClient.get();
			} else
				newClient = clientDAO.save(clientData);

			sessionManagerService.saveInSession(SessionObject.BOOKING_CLIENT, newClient);
			String controlParam = new RandomStringGenerator(8).rand();
			sessionManagerService.saveInSession(SessionObject.CONTROL_BOOKING_PARAM, controlParam);

			PayuOrderResponse response = payuService.getPaymentRedirect(user, controlParam);

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect(response.getRedirectUri());
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
		return null;
	}

	public void makeRealBooking() throws IOException {
		Client client = (Client) sessionManagerService.getFromSession(SessionObject.BOOKING_CLIENT);
		sessionManagerService.removeFromSession(SessionObject.BOOKING_CLIENT.getName());

		String pass = new RandomStringGenerator(8).rand();

		if (client.getUser() == null) {
			User user = User.builder().active(true).login(client.getEmail()).password(pass).role(UserRole.CLIENT)
					.build();

			userDAO.save(user);
		}

		Booking booking = Booking.builder()
				.client(clientData)
				.startDate(startDateDATE)
				.endDate(endDateDATE)
				.status(BookingStatus.BOOKED)
				.build();
		
		if (booking.getRooms() == null) {
			booking.setRooms(new LinkedList<>());
		}
		booking.setRooms(rooms);

		bookingDAO.save(booking);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect("http://localhost:8080/paihotel/success-book.xhtml");
	}

	public String chooseRoomToBook(Long roomId) {
		sessionManagerService.saveInSession(SessionObject.ROOM_ID, roomId);

		return Utils.getTemplateUriRedirect("booking/booking-person-data");
	}
	
	public String submitBookingAndPay() {
		if(rooms== null || rooms.isEmpty())
		{
			message = "Wymagana jest przynajmniej rezerwacja jednego pokoju";
			return null;
		}
		return Utils.getTemplateUriRedirect("booking/booking-person-data");
	}

	public String makeRealBookingWithoutPayu() throws IOException {
		User user = AuthorizationController.getUser();
		try {
			validate();

			clientData.setUser(user);
			Optional<Client> optionalClient = clientDAO
					.findByQuery(Client.builder().email(clientData.getEmail()).build()).stream().findFirst();

			Client newClient = null;
			if (optionalClient.isPresent()) {
				newClient = optionalClient.get();
			} else
				newClient = clientDAO.save(clientData);

			String pass = new RandomStringGenerator(8).rand();

			if (newClient.getUser() == null) {
				User newUser = User.builder().active(true).login(newClient.getEmail()).password(pass)
						.role(UserRole.CLIENT).build();

				userDAO.save(newUser);
			}

			Booking booking = Booking.builder()
					.client(newClient)
					.startDate(startDateDATE)
					.endDate(endDateDATE)
					.status(BookingStatus.BOOKED)
					.build();
			if (booking.getRooms() == null) {
				booking.setRooms(new LinkedList<>());
			}
			booking.setRooms(rooms);

			bookingDAO.save(booking);

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect("http://localhost:8080/paihotel/success-book.xhtml");
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
		return null;
	}
	
	public String addNewRoomToBook(Long roomId) {
		if(rooms == null) {
			rooms = new LinkedList<>();
		}
		Optional<Room> optRoom =  rooms.stream().filter(r->r.getId().equals(roomId)).findFirst();
		if(optRoom.isPresent()) {
			message="Ten pokoj zostal juz dodany do rezerwacji";
			return null;
		}
		
		rooms.add(roomDAO.findOne(roomId));
		totalCost = new BigDecimal(0);
		for(Room room : rooms)
			totalCost = totalCost.add(room.getPrice());
		
		totalCost = totalCost.setScale(2, RoundingMode.CEILING);
		fee = new BigDecimal(totalCost.toString()).multiply(new BigDecimal(0.10));
		fee = fee.setScale(2, RoundingMode.CEILING);
		return null;
	}
	
	public String restartForm() {
		rooms = null;
		message = null;
		return toDefaultValue();
	}

}
