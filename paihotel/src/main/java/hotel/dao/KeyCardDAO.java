package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.KeyCard;
/**
 * @author Karolina B¹tkowska
 */
@Named
@Stateless
@LocalBean
public class KeyCardDAO extends CrudDAO<Long, KeyCard> {
	
	
}
