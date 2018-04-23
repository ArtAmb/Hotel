package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Hotel;

@Named
@Stateless
@LocalBean
public class HotelDAO extends CrudDAO<Long, Hotel> {
	
	
}
