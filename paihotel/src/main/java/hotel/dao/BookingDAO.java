package hotel.dao;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import org.springframework.util.Assert;

import hotel.domain.Booking;
import hotel.domain.Hotel;
import hotel.domain.Room;

@Named
@Stateless
@LocalBean
public class BookingDAO extends CrudDAO<Long, Booking> {

	public List<Booking> findByRoomAndBetweenStartDateAndEndDate(Long roomId, Date startDate, Date endDate) {

		String query = String.format(" where  1=1"
				+ " AND ((tmp.startDate >= '%s' AND tmp.endDate <= '%s')"
				+ " OR (tmp.startDate >= '%s' AND tmp.endDate <= '%s')"
				+ " OR (tmp.startDate >= '%s' AND tmp.endDate <= '%s'))",
				 startDate, endDate, endDate, endDate, startDate, startDate);

		List<Booking> result = entityManager.createQuery("SELECT tmp from " + entityClass.getName() + " tmp" + query, entityClass)
				.getResultList();
		
		return result.stream()
				.filter(b->b.getRooms() != null)
				.filter(b->b.getRooms().stream().map(Room::getId).collect(Collectors.toList()).contains(roomId))
				.collect(Collectors.toList());
	}
	
	public List<Booking> findByHotel(Hotel hotel) {
		Assert.notNull(hotel, "Hotel cannot be null");
		Assert.notNull(hotel.getId(), "Hotel id cannot be null");
		
		//String query = String.format(" where tmp.room.hotel.id = %s ", hotel.getId());
		//return entityManager.createQuery("SELECT tmp from " + entityClass.getName() + " tmp" + query, entityClass)
			//	.getResultList();
		
		return findAll().stream().filter(b->b.getRooms().stream()
				.filter(r->r.getHotel()!=null)
				.findFirst().get()
				.getHotel().getId()
				.equals(hotel.getId()))
				.collect(Collectors.toList());
	}

}
