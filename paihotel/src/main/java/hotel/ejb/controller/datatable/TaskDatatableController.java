package hotel.ejb.controller.datatable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import hotel.dao.TaskDAO;
import hotel.domain.Task;
import hotel.ejb.controller.MainMenuController;
import hotel.ejb.services.DatatableService.Row;
import hotel.ejb.services.DatatableService.Value;
import lombok.Getter;
import lombok.Setter;

@Stateless
@Named
@LocalBean
public class TaskDatatableController extends BaseDataTableController<Long, Task>{
	
	@EJB
	private TaskDAO taskDAO;
	
	@Getter
	@Setter
	public Task query = new Task();
	
	@Inject
	private MainMenuController mainMenuController;
	
	public void beforeQueryAction() {
		query.setHotel(mainMenuController.getChosenHotel());
		this.crudDAO = taskDAO;
		this.queryDTO = query;
		
	}
	
	@Override
	protected List<String> getHeader() {
		return Arrays.asList("ID", "Typ", "Do kiedy", "Opis", "Rezerwacja", "Pokój");
	}

	@Override
	protected Row createRow(Task task) {
		Row row = new Row();
		List<Value> values = new LinkedList<>();

		values.add(prepareValue(task.getId()));
		values.add(prepareValue(task.getType()));
		values.add(prepareValue(task.getDate()));
		values.add(prepareValue(task.getDescription()));
		values.add(prepareValue(task.getBooking() != null ? task.getBooking().getId() : null));
		values.add(prepareValue(task.getRoom() != null ? task.getRoom().getName(): null));
		
		
		row.setValues(values);
		return row;

	}

	@Override
	public String redirectToQueryResult() {
		return "tasks-results";
	}


}
