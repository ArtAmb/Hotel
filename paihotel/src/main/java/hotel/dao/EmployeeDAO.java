package hotel.dao;

import java.util.List;

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
	
	public List<Employee> findByHotelId(Long hotelId){

		String query = String.format(" where tmp.hotel.id = %s " );

		return entityManager.createQuery("SELECT tmp from " + entityClass.getName() + " tmp" + query, entityClass).getResultList();
	}
}
