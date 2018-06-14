package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Feature;
/**
 * @author Karolina B¹tkowska
 */
@Named
@Stateless
@LocalBean
public class FeatureDAO extends CrudDAO<Long, Feature> {
	
	
}
