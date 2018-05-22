package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.News;

@Named
@Stateless
@LocalBean
public class NewsDAO extends CrudDAO<Long, News> {
	
	
}
