package hotel.ejb.controller.datatable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.KeyCardDAO;
import hotel.domain.KeyCard;
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
public class KeyCardDatatableController extends BaseDataTableController<Long, KeyCard>{
	
	@EJB
	private KeyCardDAO KeyCardDAO;
	
	@Getter
	@Setter
	public KeyCard query = new KeyCard();
	
	
	public void beforeQueryAction() {
		this.crudDAO = KeyCardDAO;
		this.queryDTO = query;
		
	}
	
	@Override
	protected List<String> getHeader() {
		return Arrays.asList("ID", "Number");
	}

	@Override
	protected Row createRow(KeyCard KeyCard) {
		Row row = new Row();
		List<Value> values = new LinkedList<>();

		values.add(prepareValue(KeyCard.getId()));
		values.add(prepareValue(KeyCard.getCodeNumber()));
		
		row.setValues(values);
		return row;

	}

	@Override
	public String redirectToQueryResult() {
		return "keyCards-results";
	}


}
