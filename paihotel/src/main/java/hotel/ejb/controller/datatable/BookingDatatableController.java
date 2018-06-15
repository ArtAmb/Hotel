package hotel.ejb.controller.datatable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.BookingDAO;
import hotel.domain.Booking;
import hotel.ejb.services.DatatableService.Row;
import hotel.ejb.services.DatatableService.Value;
import lombok.Getter;
import lombok.Setter;

@Stateless
@Named
@LocalBean
public class BookingDatatableController extends BaseDataTableController<Long, Booking>{
	
	@EJB
	private BookingDAO BookingDAO;
	
	@Getter
	@Setter
	public Booking query = new Booking();
	
	
	public void beforeQueryAction() {
		this.crudDAO = BookingDAO;
		this.queryDTO = query;
		
	}
	
	@Override
	protected List<String> getHeader() {
		return Arrays.asList("ID", "Hotel", "Pokoj ID","Imie", "Nazwisko", "Status", "Data pocz.", "Data koniec");
	}

	@Override
	protected Row createRow(Booking myTest) {
		Row row = new Row();
		List<Value> values = new LinkedList<>();

		values.add(prepareValue(myTest.getId()));
		values.add(prepareValue(myTest.getRoom().getHotel().getName()));
		values.add(prepareValue(myTest.getRoom().getId()));
		values.add(prepareValue(myTest.getClient().getName()));
		values.add(prepareValue(myTest.getClient().getSurname()));
		values.add(prepareValue(myTest.getStatus()));
		values.add(prepareValue(myTest.getStartDate()));
		values.add(prepareValue(myTest.getEndDate()));
		row.setValues(values);
		return row;

	}

	@Override
	public String redirectToQueryResult() {
		return "visits-results";
	}


}
