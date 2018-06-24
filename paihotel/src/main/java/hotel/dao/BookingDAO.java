package hotel.dao;

import java.sql.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import org.springframework.util.Assert;

import hotel.domain.Booking;
import hotel.domain.Hotel;

@Named
@Stateless
@LocalBean
public class BookingDAO extends CrudDAO<Long, Booking> {

	public List<Booking> findByRoomAndBetweenStartDateAndEndDate(Long roomId, Date startDate, Date endDate) {

		String query = String.format(" where tmp.room.id = %s "
				+ "and ((tmp.startDate >= '%s' AND tmp.endDate <= '%s')"
				+ " OR (tmp.startDate >= '%s' AND tmp.endDate <= '%s')"
				+ " OR (tmp.startDate >= '%s' AND tmp.endDate <= '%s'))",
				roomId, startDate, endDate, endDate, endDate, startDate, startDate);

		return entityManager.createQuery("SELECT tmp from " + entityClass.getName() + " tmp" + query, entityClass)
				.getResultList();
	}
	
	public List<Booking> findByHotel(Hotel hotel) {
		Assert.notNull(hotel, "Hotel cannot be null");
		Assert.notNull(hotel.getId(), "Hotel id cannot be null");
		
		String query = String.format(" where tmp.room.hotel.id = %s ", hotel.getId());
		return entityManager.createQuery("SELECT tmp from " + entityClass.getName() + " tmp" + query, entityClass)
				.getResultList();
	}

}
