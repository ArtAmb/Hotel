package hotel.ejb.controller.datatable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import hotel.dao.EmployeeDAO;
import hotel.domain.Employee;
import hotel.ejb.controller.MainMenuController;
import hotel.ejb.services.DatatableService.Row;
import hotel.ejb.services.DatatableService.Value;
import lombok.Getter;
import lombok.Setter;
/**
 * @author Karolina B¹tkowska
 */
@Stateless
@Named
@LocalBean
public class EmployeeDatatableController extends BaseDataTableController<Long, Employee>{
	
	@EJB
	private EmployeeDAO employeeDAO;
	
	@Inject
	private MainMenuController mainMenuController;
	
	@Getter
	@Setter
	public Employee query = new Employee();
	
	
	public void beforeQueryAction() {
		query.setHotel(mainMenuController.getChosenHotel());
		this.crudDAO = employeeDAO;
		this.queryDTO = query;
	}
	
	@Override
	protected List<String> getHeader() {
		return Arrays.asList("ID", "Imie", "Nazwisko");
	}

	@Override
	protected Row createRow(Employee myTest) {
		Row row = new Row();
		List<Value> values = new LinkedList<>();

		values.add(prepareValue(myTest.getId()));
		values.add(prepareValue(myTest.getName()));
		values.add(prepareValue(myTest.getSurname()));
		row.setValues(values);
		return row;

	}

	@Override
	public String redirectToQueryResult() {
		return null;
	}
	
	


}
