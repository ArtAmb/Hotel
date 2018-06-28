package hotel.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Employee;
import hotel.domain.User;
/**
 * @author Karolina B¹tkowska
 */
@Named
@Stateless
@LocalBean
public class EmployeeDAO extends CrudDAO<Long, Employee> {
	
	public List<Employee> findByHotelId(Long hotelId){

		String query = String.format(" where tmp.hotel.id = %s " );

		return entityManager.createQuery("SELECT tmp from " + entityClass.getName() + " tmp" + query, entityClass).getResultList();
	}
	
	public Employee findByUser(User user) {
		return findByQuery(Employee
				.builder()
				.user(user)
				.build()).stream()
				.findFirst()
				.orElse(null);
	}
}
