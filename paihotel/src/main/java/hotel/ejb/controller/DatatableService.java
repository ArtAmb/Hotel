package hotel.ejb.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.MyTest;
import lombok.Data;

@Stateless
@Named
@LocalBean
public class DatatableService {

	public TableData prepareDataMyTest(List<MyTest> myTests) {
		TableData tableData = new TableData();
		tableData.setHeaders(Arrays.asList("ID", "Int1", "Int2", "String1", "String2"));

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
		private List<String> headers;
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
