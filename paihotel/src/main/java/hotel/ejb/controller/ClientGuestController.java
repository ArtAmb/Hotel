package hotel.ejb.controller;

import java.io.Serializable;
import java.util.LinkedList;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mysql.jdbc.StringUtils;

import hotel.Utils;
import hotel.dao.BookingDAO;
import hotel.dao.ClientDAO;
import hotel.domain.Booking;
import hotel.domain.Client;
import hotel.domain.Room;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@Named
@SessionScoped
public class ClientGuestController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ClientDAO clientDAO;

	@EJB
	private BookingDAO bookingDAO;

	@Getter
	@Setter
	private Client newClient = new Client();

	@Getter
	@Setter
	private Long bookingId;

	@Getter
	@Setter
	private Booking booking;

	@Getter
	@Setter
	private String message;

	@Getter
	@Setter
	private String identityCardNumber;

	@Getter
	@Setter
	private Integer maxGuests;

	@Getter
	@Setter
	private Integer currentNumberOfGuests;
	
	@Inject
	private BookingDetailControler bookingDetailControler;

	public String signInSomeGuests(Long bookingId) {
		this.bookingId = bookingId;
		booking = bookingDAO.findOne(bookingId);

		maxGuests = booking.getRooms().stream().map(Room::getMaxNumberOfPeople).reduce((r1, r2) -> {
			return r1 += r2;
		}).get();

		return Utils.getTemplateUri("booking/booking-add-guest");
	}

	public String addNewGuest() {
		try {

			if (booking.getGuests() == null) {
				booking.setGuests(new LinkedList<>());
			}
			if (booking.getGuests().size() == maxGuests) {
				message = "Osiagnieto maksymalna liczbe gosci.";
				return null;
			}
			
			validate();
			Client client = clientDAO.save(newClient);

			booking.getGuests().add(client);
			booking = bookingDAO.save(booking);
			restart();
			message = "Dodano klienta";
		} catch (Exception e) {
			message = e.getMessage();
		}
		return null;
	}

	public void restart() {
		newClient = new Client();
		message = "";
		identityCardNumber = null;
		currentNumberOfGuests = booking.getGuests().size();
	}

	public String finishSigningGuests() {
		booking = bookingDAO.save(booking);
		return bookingDetailControler.visitDetail(bookingId);
	}

	void validate() {
		if (StringUtils.isNullOrEmpty(newClient.getName())) {
			throw new IllegalStateException("Imie jest wymagane");
		}
		if (StringUtils.isNullOrEmpty(newClient.getSurname())) {
			throw new IllegalStateException("Nazwisko jest wymagane");
		}
		if (StringUtils.isNullOrEmpty(newClient.getIdentityCardNumber())) {
			throw new IllegalStateException("Nr dowodu jest wymagany");
		}
	}

	public String findByIdentityCardNumber() {
		if(identityCardNumber == null) {
			message="Prosze uzupelnic numer dowodu";
			return null;
		}
		Client client = clientDAO.findByQuery(Client.builder().identityCardNumber(identityCardNumber).build()).stream()
				.findFirst().orElse(null);

		newClient = client;
		if (newClient == null) {
			newClient = new Client();
			newClient.setIdentityCardNumber(identityCardNumber);
			message = "Brak klienta w bazie, prosze uzupelnic dane.";
		}

		return null;
	}

}
