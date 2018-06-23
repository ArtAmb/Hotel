package hotel.ejb.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.mysql.jdbc.StringUtils;

import hotel.Utils;
import hotel.dao.BookingDAO;
import hotel.dao.ClientDAO;
import hotel.dao.RoomDAO;
import hotel.dao.UserDAO;
import hotel.domain.Booking;
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

/**
 * @author Artur Ambrolewicz
 */
@RequestScoped
@Named
@Data
@LocalBean
public class BookingController {

	private Date startDate = new Date(System.currentTimeMillis());
	private Date endDate = Utils.addDays(startDate, 5);
	private List<Long> features = new LinkedList<>();

	private Booking query = new Booking();
	private Client clientData = new Client();
	private String errorMessage = "";
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

	public String saveBooking() {
		Booking booking = new Booking();
		booking.setStartDate(startDate);
		booking.setEndDate(endDate);

		bookingDAO.save(booking);

		return "booking";
	}

	public List<Booking> findAllBookings() {
		return bookingDAO.findAll();
	}

	public Booking findOne(Long id) {
		return bookingDAO.findOne(id);
	}

	public List<RoomDTO> findByQuery() {

		List<Room> rooms = roomDAO.findAll();
		List<RoomDTO> avaliableRooms = rooms.stream().filter(
				r -> r.getFeatures().stream().map(Feature::getId).collect(Collectors.toList()).containsAll(features))
				.map(r -> roomService.findRoomBookings(r, startDate, endDate)).collect(Collectors.toList());

		return avaliableRooms;
	}

	public String findBooking() {
		return Utils.getViewUrl("visits/visits-results");
	}

	public String refreshView() {
		return null;
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
			if (!optionalClient.isPresent()) {
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
		Long roomID = (Long) sessionManagerService.getFromSession(SessionObject.ROOM_ID);
		Room bookingRoom = roomDAO.findOne(roomID);
		sessionManagerService.removeFromSession(SessionObject.ROOM_ID.getName());
		Client client = (Client) sessionManagerService.getFromSession(SessionObject.BOOKING_CLIENT);
		sessionManagerService.removeFromSession(SessionObject.BOOKING_CLIENT.getName());
		
		String pass = new RandomStringGenerator(8).rand();

		if (client.getUser() == null) {
			User user = User.builder()
					.active(true)
					.login(client.getEmail())
					.password(pass)
					.role(UserRole.CLIENT)
					.build();
			
			userDAO.save(user);
		}

		Booking booking = Booking.builder()
				.client(clientData)
				.startDate(startDate)
				.endDate(endDate)
				.room(bookingRoom)
				.build();

		bookingDAO.save(booking);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect("http://localhost:8080/paihotel/success-book.xhtml");
	}

	public String chooseRoomToBook(Long roomId) {
		sessionManagerService.saveInSession(SessionObject.ROOM_ID, roomId);

		return Utils.getTemplateUriRedirect("booking/booking-person-data");
	}
}
