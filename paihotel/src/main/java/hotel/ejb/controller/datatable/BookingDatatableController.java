package hotel.ejb.controller.datatable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import hotel.dao.BookingDAO;
import hotel.domain.Booking;
import hotel.domain.Room;
import hotel.ejb.services.DatatableService.Row;
import hotel.ejb.services.DatatableService.Value;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
@ManagedBean
public class BookingDatatableController extends BaseDataTableController<Long, Booking>{
	
	@EJB
	private BookingDAO bookingDAO;
	
	@Getter
	@Setter
	private Booking query = new Booking();
	
	
	public void beforeQueryAction() {
		this.crudDAO = bookingDAO;
		this.queryDTO = query;
		
	}
	
	@Override
	protected List<String> getHeader() {
		return Arrays.asList("ID", "Pokoj ID","Imie", "Nazwisko", "Status", "Data pocz.", "Data koniec");
	}

	@Override
	protected Row createRow(Booking myTest) {
		Row row = new Row();
		List<Value> values = new LinkedList<>();

		values.add(prepareValue(myTest.getId()));
		values.add(prepareValue(myTest.getRooms() != null ? myTest.getRooms().stream().map(Room::getId).collect(Collectors.toList()) : null));
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
