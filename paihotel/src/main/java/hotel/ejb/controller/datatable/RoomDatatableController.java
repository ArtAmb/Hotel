package hotel.ejb.controller.datatable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import hotel.dao.RoomDAO;
import hotel.domain.Room;
import hotel.ejb.controller.MainMenuController;
import hotel.ejb.services.DatatableService.Row;
import hotel.ejb.services.DatatableService.Value;
import lombok.Getter;
import lombok.Setter;

@Stateless
@Named
@LocalBean
public class RoomDatatableController extends BaseDataTableController<Long, Room>{
	
	@EJB
	private RoomDAO RoomDAO;
	
	@Inject
	private MainMenuController mainMenuController;
	
	@Getter
	@Setter
	public Room query = new Room();
	
	
	public void beforeQueryAction() {
		this.crudDAO = RoomDAO;
		query.setHotel(mainMenuController.getChosenHotel());
		this.queryDTO = query;
		
	}
	
	@Override
	protected List<String> getHeader() {
		return Arrays.asList("ID", "Nazwa", "Numer");
	}

	@Override
	protected Row createRow(Room Room) {
		Row row = new Row();
		List<Value> values = new LinkedList<>();

		values.add(prepareValue(Room.getId()));
		values.add(prepareValue(Room.getName()));
		values.add(prepareValue(Room.getNumber()));
		
		
		row.setValues(values);
		return row;

	}

	@Override
	public String redirectToQueryResult() {
		return "rooms-results";
	}


}
