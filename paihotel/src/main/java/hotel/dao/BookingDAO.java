package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Booking;

@Named
@Stateless
@LocalBean
public class BookingDAO extends CrudDAO<Long, Booking> {
	
	
}
