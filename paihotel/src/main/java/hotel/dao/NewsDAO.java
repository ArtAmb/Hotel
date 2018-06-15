package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.News;
/**
 * @author Karolina B¹tkowska
 */
@Named
@Stateless
@LocalBean
public class NewsDAO extends CrudDAO<Long, News> {
	
	
}
