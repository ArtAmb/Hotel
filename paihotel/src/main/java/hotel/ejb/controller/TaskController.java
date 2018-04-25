package hotel.ejb.controller;


import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.TaskDAO;
import hotel.domain.Hotel;
import hotel.domain.Task;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class TaskController {

	private Integer id;
	private Date date;
	private String description;
	private String state;
	private String type;
	private Hotel hotel;

	private Task query = new Task();

	@EJB
	private TaskDAO taskDAO;

	public String saveTask() {

		Task task = new Task();
		
		task.setDate(date);
		task.setDescription(description);
		task.setType(type);

		taskDAO.save(task);
		

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

}
