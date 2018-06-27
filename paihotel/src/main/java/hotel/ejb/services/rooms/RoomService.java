package hotel.ejb.services.rooms;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.BookingDAO;
import hotel.domain.Booking;
import hotel.domain.Room;
import hotel.ejb.services.rooms.dto.RoomDTO;

@Stateless
@Named
public class RoomService {
	
	@EJB
	private BookingDAO bookingDAO;
	
	public RoomDTO findRoomBookings(Room room, Date startDate, Date endDate){
		if(room == null || startDate == null || endDate == null) {
			return null;
		}
		List<Booking> bookings = bookingDAO.findByRoomAndBetweenStartDateAndEndDate(room.getId(), startDate, endDate);
		
		return RoomDTO.builder()
				.room(room)
				.bookings(bookings)
				.available(bookings.isEmpty())
				.build();
	}
	
}
