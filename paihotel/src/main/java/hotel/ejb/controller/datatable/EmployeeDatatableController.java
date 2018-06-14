package hotel.ejb.controller.datatable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.EmployeeDAO;
import hotel.domain.Employee;
import hotel.ejb.services.DatatableService.Row;
import hotel.ejb.services.DatatableService.Value;
import lombok.Getter;
import lombok.Setter;

@Stateless
@Named
@LocalBean
public class EmployeeDatatableController extends BaseDataTableController<Long, Employee>{
	
	@EJB
	private EmployeeDAO employeeDAO;
	
	@Getter
	@Setter
	public Employee query = new Employee();
	
	
	public void beforeQueryAction() {
		this.crudDAO = employeeDAO;
		this.queryDTO = query;
		
	}
	
	@Override
	protected List<String> getHeader() {
		return Arrays.asList("ID", "Imie", "Nazwisko", "Hotel");
	}

	@Override
	protected Row createRow(Employee myTest) {
		Row row = new Row();
		List<Value> values = new LinkedList<>();

		values.add(prepareValue(myTest.getId()));
		values.add(prepareValue(myTest.getName()));
		values.add(prepareValue(myTest.getSurname()));
		values.add(prepareValue(myTest.getHotel().getName()));
		row.setValues(values);
		return row;

	}

	@Override
	public String redirectToQueryResult() {
		return null;
	}
	
	


}
