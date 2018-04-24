package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Employee;

@Named
@Stateless
@LocalBean
public class EmployeeDAO extends CrudDAO<Long, Employee> {
	
	
}
