package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Task;

@Named
@Stateless
@LocalBean
public class TaskDAO extends CrudDAO<Long, Task> {
	
	
}
