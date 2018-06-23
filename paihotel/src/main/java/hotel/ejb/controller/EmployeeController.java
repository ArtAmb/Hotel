package hotel.ejb.controller;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.EmployeeDAO;
import hotel.dao.HotelDAO;
import hotel.domain.Employee;
import hotel.domain.Hotel;
import lombok.Data;
/**
 * @author Karolina B¹tkowska
 */
@Stateless
@Named
@Data
@LocalBean
public class EmployeeController {

	private long id;
	private String name;
	private String surname;
	private long hotelID;
	
	

	private Employee query = new Employee();
	
	@EJB
	private EmployeeDAO employeeDAO;
	
	@EJB
	private HotelDAO hotelDAO;

	public String saveEmployee() {
		
		
		Hotel hotel = hotelDAO.findOne(hotelID);
		

		Employee employee = new Employee();
		employee.setName(name);
		employee.setSurname(surname);
		employee.setHotel(hotel);
		

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
