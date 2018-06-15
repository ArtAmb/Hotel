package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;


import hotel.domain.Employee;
/**
 * @author Karolina B¹tkowska
 */
@Named
@Stateless
@LocalBean
public class EmployeeDAO extends CrudDAO<Long, Employee> {
	
	
}
