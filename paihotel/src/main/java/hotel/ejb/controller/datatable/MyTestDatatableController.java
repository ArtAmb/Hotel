package hotel.ejb.controller.datatable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.MyTestDAO;
import hotel.domain.MyTest;
import hotel.ejb.services.DatatableService.Row;
import hotel.ejb.services.DatatableService.Value;
import lombok.Getter;
import lombok.Setter;

@Stateless
@Named
@LocalBean
public class MyTestDatatableController extends BaseDataTableController<Long, MyTest>{
	
	@EJB
	private MyTestDAO myTestDAO;
	
	@Getter
	@Setter
	public MyTest query = new MyTest();
	
	
	public void beforeQueryAction() {
		this.crudDAO = myTestDAO;
		this.queryDTO = query;
		
	}
	
	@Override
	protected List<String> getHeader() {
		return Arrays.asList("ID", "TestInt", "TestInt2", "TestString", "TestString2");
	}

	@Override
	protected Row createRow(MyTest myTest) {
		Row row = new Row();
		List<Value> values = new LinkedList<>();

		values.add(prepareValue(myTest.getId()));
		values.add(prepareValue(myTest.getTestInt()));
		values.add(prepareValue(myTest.getTestInt2()));
		values.add(prepareValue(myTest.getTestString()));
		values.add(prepareValue(myTest.getTestString2()));
		row.setValues(values);
		return row;

	}

	@Override
	public String redirectToQueryResult() {
		return "myTests-results";
	}


}
