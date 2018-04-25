package hotel.ejb.controller;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.EmployeeDAO;
import hotel.domain.Employee;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class EmployeeController {

	private Integer id;
	private String name;
	private String surname;
	
	

	private Employee query = new Employee();
	
	@EJB
	private EmployeeDAO employeeDAO;

	public String saveEmployee() {

		Employee employee = new Employee();
		employee.setName(name);
		employee.setSurname(surname);
		

		employeeDAO.save(employee);
		
		return "employee";
	}
	
	public List<Employee> findAllEmployees(){
		return employeeDAO.findAll();
	}
	
	public Employee findOne(Long id){
		return employeeDAO.findOne(id);
	}
	
	public List<Employee> findByQuery(){
		return employeeDAO.findByQuery(query);
	}
	public String findEmployee() {
		return "employees-results";
	}
	
	

}
