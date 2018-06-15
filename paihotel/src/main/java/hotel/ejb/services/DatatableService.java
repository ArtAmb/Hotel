package hotel.ejb.services;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Client;
import hotel.domain.MyTest;
import lombok.Data;

/**
 * @author Artur Ambrolewicz
 */
@Stateless
@Named
@LocalBean
public class DatatableService {

	public TableData prepareDataMyTest(List<MyTest> myTests) {
		TableData tableData = new TableData();
		tableData.setTabHeaders(Arrays.asList("ID", "Int1", "Int2", "String1", "String2"));
		

		List<Row> rows = new LinkedList<>();

		for (MyTest myTest : myTests) {
			Row row = new Row();

			List<Value> values = new LinkedList<>();

			values.add(prepareValue(myTest.getId()));
			values.add(prepareValue(myTest.getTestInt()));
			values.add(prepareValue(myTest.getTestInt2()));
			values.add(prepareValue(myTest.getTestString()));
			values.add(prepareValue(myTest.getTestString2()));
			row.setValues(values);

			rows.add(row);
		}
		tableData.setRows(rows);

		return tableData;
	}
	
	public TableData prepareDataForClient(List<Client> clients) {
		TableData tableData = new TableData();
		tableData.setTabHeaders(Arrays.asList("ID", "Imie", "Nazwisko"));

		List<Row> rows = new LinkedList<>();
		for (Client client : clients) {
			Row row = new Row();

			List<Value> values = new LinkedList<>();

			values.add(prepareValue(client.getName()));
			values.add(prepareValue(client.getSurname()));
			row.setValues(values);

			rows.add(row);
		}
		tableData.setRows(rows);

		return tableData;
	}

	public Value prepareValue(Object object) {
		Value value = new Value();
		if (object != null)
			value.setValue(object.toString());
		else
			value.setValue("");

		return value;
	}

	@Data
	public static class TableData {
		private List<String> tabHeaders;
		private List<Row> rows;
	}

	@Data
	public static class Row {
		private List<Value> values;
	}

	@Data
	public static class Value {
		private String value;
	}

}
