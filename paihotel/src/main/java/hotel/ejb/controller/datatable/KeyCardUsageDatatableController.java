package hotel.ejb.controller.datatable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.KeyCardUsageDAO;
import hotel.domain.KeyCardUsage;
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
public class KeyCardUsageDatatableController extends BaseDataTableController<Long, KeyCardUsage>{
	
	@EJB
	private KeyCardUsageDAO KeyCardUsageDAO;
	
	@Getter
	@Setter
	public KeyCardUsage query = new KeyCardUsage();
	
	
	public void beforeQueryAction() {
		this.crudDAO = KeyCardUsageDAO;
		this.queryDTO = query;
		
	}
	
	@Override
	protected List<String> getHeader() {
		return Arrays.asList("ID", "Opis", "Rezerwacja");
	}

	@Override
	protected Row createRow(KeyCardUsage KeyCardUsage) {
		Row row = new Row();
		List<Value> values = new LinkedList<>();

		values.add(prepareValue(KeyCardUsage.getId()));
		values.add(prepareValue(KeyCardUsage.getDescription()));
		values.add(prepareValue(KeyCardUsage.getBooking()));
		
		row.setValues(values);
		return row;

	}

	@Override
	public String redirectToQueryResult() {
		return "card-details";
	}


}
