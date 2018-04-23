package hotel.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.MyTestDAO;
import hotel.domain.MyTest;
import lombok.Data;

@Stateless
@LocalBean
@Named
@Data
public class TestController {

	@EJB
	private MyTestDAO myTestDAO;

	private MyTest query = new MyTest();
	private MyTest newMyTest = new MyTest();

	public List<MyTest> findByQuery() {
		System.out.println("LOG INFO: " + query);
		return myTestDAO.findByQuery(query);
	}
	
	public List<MyTest> findAll() {
		return myTestDAO.findAll();
	}
	
	public String findMyTest() {
		return "myTests-results";
	}
	
	public String saveMyTest() {
		myTestDAO.save(newMyTest);
		return null;
	}
	
	

}
