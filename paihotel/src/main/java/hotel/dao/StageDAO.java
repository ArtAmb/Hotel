package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Stage;

@Named
@Stateless
@LocalBean
public class StageDAO extends CrudDAO<Long, Stage> {
	
	
}
