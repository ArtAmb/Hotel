package hotel.ejb.controller;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Named;

import hotel.Utils;
import hotel.dao.BookingDAO;
import hotel.dao.RoomDAO;
import hotel.domain.Booking;
import hotel.domain.Client;
import hotel.domain.Feature;
import hotel.domain.Room;
import hotel.ejb.services.rooms.RoomService;
import hotel.ejb.services.rooms.dto.RoomDTO;
import lombok.Data;

@Stateful
@Named
@Data
@LocalBean
public class BookingController {

	private Long roomToBookId;
	private Date startDate = new Date(System.currentTimeMillis());
	private Date endDate = Utils.addDays(startDate, 5);
	private List<Long> features = new LinkedList<>();

	private Booking query = new Booking();
	private Client clientData;
	@EJB
	private BookingDAO bookingDAO;

	@EJB
	private RoomService roomService;

	@EJB
	private RoomDAO roomDAO;

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
		List<RoomDTO> avaliableRooms = rooms.stream()
				.filter(r -> r.getFeatures().stream().map(Feature::getId).collect(Collectors.toList()).containsAll(features))
				.map(r -> roomService.findRoomBookings(r, startDate, endDate)).collect(Collectors.toList());

		return avaliableRooms;
	}

	public String findBooking() {
		return Utils.getViewUrl("visits/visits-results");
	}
	
	public String refreshView() {
		return null;
	}

	public String makeBooking() {
		
		Room bookingRoom = roomDAO.findOne(roomToBookId);
		
		Booking booking = Booking.builder()
				.client(clientData)
				.room(bookingRoom)
				.build();
		
		bookingDAO.save(booking);
		
		return "hello"; 
	}
	
}
