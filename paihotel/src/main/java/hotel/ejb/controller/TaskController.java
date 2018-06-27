package hotel.ejb.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hotel.dao.TaskDAO;
import hotel.domain.Task;
import hotel.domain.TaskStatus;
import lombok.Data;

@Named
@Data
@ManagedBean
@RequestScoped
public class TaskController {

	private Integer id;
	private Date date;
	private String description;
	private String state;
	private String type;

	private Task query = new Task();

	@Inject
	private MainMenuController mainMenuController;

	@EJB
	private TaskDAO taskDAO;

	private String message = "";
	void validate() {
		/*if (date == null) {
			throw new IllegalStateException("Data nie moze byc pusta");
		}*/
		if (description == null && description.equals("")) {
			throw new IllegalStateException("Opis nie moze byc pusty");
		}
	}
	
	void reset() {
		
		date = null;
		description = null;
		state = null;
		type = null;
	    message = "Dodano zadanie";
	}

	public String saveTask() {

		try {
			validate();
			Task task = new Task();

			//task.setDate(date);
			task.setDescription(description);
			task.setType(type);
			task.setState(TaskStatus.TODO);
			task.setHotel(mainMenuController.getChosenHotel());

			taskDAO.save(task);
		} catch (Exception e) {
			message = e.getMessage();
			return null;
		}

		return "task";
	}

	public List<Task> findAllTasks() {
		return taskDAO.findAll();
	}

	public Task findOne(Long id) {
		return taskDAO.findOne(id);
	}

	public List<Task> findByQuery() {
		return taskDAO.findByQuery(query);
	}

	public String findTask() {
		return "tasks-results";
	}

	public String finishTask(Long id) {
		Task task = taskDAO.findOne(id);

		if (task != null) {
			task.setState(TaskStatus.FINISHED);

			taskDAO.save(task);
		}
		reset();
		return "task";
	}
	
	public String startTask(Long id) {
		Task task = taskDAO.findOne(id);

		if (task != null) {
			task.setState(TaskStatus.DURING);

			taskDAO.save(task);
		}
		reset();
		return "task";
	}

}
