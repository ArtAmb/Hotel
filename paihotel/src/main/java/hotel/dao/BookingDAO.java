package hotel.dao;

import java.sql.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Booking;

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

}
