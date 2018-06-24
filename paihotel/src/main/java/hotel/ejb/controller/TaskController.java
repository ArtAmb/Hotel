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

	void validate() {
		if (date == null) {
			throw new IllegalStateException("Data nie moze byc pusta");
		}
		if (description == null && description.equals("")) {
			throw new IllegalStateException("Opis nie moze byc pusty");
		}
	}

	public String saveTask() {

		try {
			validate();
			Task task = new Task();

			task.setDate(date);
			task.setDescription(description);
			task.setType(type);
			task.setHotel(mainMenuController.getChosenHotel());

			taskDAO.save(task);
		} catch (Exception e) {

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

}
