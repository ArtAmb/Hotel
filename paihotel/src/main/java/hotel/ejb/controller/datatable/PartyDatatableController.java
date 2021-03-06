package hotel.ejb.controller.datatable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.PartyDAO;
import hotel.domain.Party;
import hotel.ejb.services.DatatableService.Row;
import hotel.ejb.services.DatatableService.Value;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Karolina B�tkowska
 */
@Stateless
@Named
@LocalBean
public class PartyDatatableController extends BaseDataTableController<Long, Party> {

	@EJB
	private PartyDAO PartyDAO;

	@Getter
	@Setter
	public Party query = new Party();

	public void beforeQueryAction() {
		this.crudDAO = PartyDAO;
		this.queryDTO = query;

	}

	@Override
	protected List<String> getHeader() {
		return Arrays.asList("ID", "Sala ID", "Nazwa sali", "Status", "Imie", "Nazwisko");
	}

	@Override
	protected Row createRow(Party party) {
		Row row = new Row();
		List<Value> values = new LinkedList<>();

		values.add(prepareValue(party.getId()));
		values.add(prepareValue(party.getState()));
		if (party.getRoom() != null) {
			values.add(prepareValue(party.getRoom().getId()));
			values.add(prepareValue(party.getRoom().getName()));
		}
		if (party.getClient() != null) {
			values.add(prepareValue(party.getClient().getName()));
			values.add(prepareValue(party.getClient().getSurname()));
		}

		row.setValues(values);
		return row;

	}

	@Override
	public String redirectToQueryResult() {
		return "parties-results";
	}

}
