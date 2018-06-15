package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Hotel;
/**
 * @author Karolina B¹tkowska
 */
@Named
@Stateless
@LocalBean
public class HotelDAO extends CrudDAO<Long, Hotel> {
	
	
}
