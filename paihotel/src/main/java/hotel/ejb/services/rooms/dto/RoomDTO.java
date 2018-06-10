package hotel.ejb.services.rooms.dto;

import java.util.List;

import hotel.domain.Booking;
import hotel.domain.Room;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomDTO {

	private Room room;
	private List<Booking> bookings;
}
