package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.KeyCardUsage;
/**
 * @author Karolina B¹tkowska
 */
@Named
@Stateless
@LocalBean
public class KeyCardUsageDAO extends CrudDAO<Long, KeyCardUsage> {
	
	
}
