package hotel.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.MyTestDAO;
import hotel.domain.MyTest;
import hotel.ejb.controller.DatatableService.TableData;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Stateless
@LocalBean
@Named
@Data
public class TestController {

	@EJB
	private MyTestDAO myTestDAO;

	@EJB
	private DatatableService datatableService;

	private MyTest query = new MyTest();
	private MyTest newMyTest = new MyTest();

	@Getter
	@Setter
	private TableData dataTableQueryResult;

	public TableData findByQuery() {
		System.out.println("LOG INFO: " + query);

		dataTableQueryResult = datatableService.prepareDataMyTest(myTestDAO.findByQuery(query));
		return dataTableQueryResult;
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
