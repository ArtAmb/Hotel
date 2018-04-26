package hotel.ejb.controller.datatable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.ClientDAO;
import hotel.domain.Client;
import hotel.ejb.controller.DatatableService.Row;
import hotel.ejb.controller.DatatableService.Value;
import lombok.Getter;
import lombok.Setter;

@Stateless
@Named
@LocalBean
public class ClientDatatableController extends BaseDataTableController<Long, Client>{
	
	@EJB
	private ClientDAO clientDAO;
	
	@Getter
	@Setter
	public Client query = new Client();
	
	
	public void beforeQueryAction() {
		this.crudDAO = clientDAO;
		this.queryDTO = query;
		
	}
	
	@Override
	protected List<String> getHeader() {
		return Arrays.asList("ID", "Imie", "Nazwisko");
	}

	@Override
	protected Row createRow(Client myTest) {
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
