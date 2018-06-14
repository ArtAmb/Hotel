package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Offert;

/**
 * @author Karolina B¹tkowska
 */
@Named
@Stateless
@LocalBean
public class OffertDAO extends CrudDAO<Long, Offert> {
	
	
}
