package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Offert;


@Named
@Stateless
@LocalBean
public class OffertDAO extends CrudDAO<Long, Offert> {
	
	
}
