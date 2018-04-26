package hotel.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.StageDAO;
import hotel.domain.Party;
import hotel.domain.Stage;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class StageController {

private Party party;
	
	private String type;
	private String state;
	private String description;
	

	private Stage query = new Stage();
	
	@EJB
	private StageDAO stageDAO;

	public String saveStage() {

		Stage stage = new Stage();
		stage.setDescription(description);
		stage.setType(type);
		stage.setState(state);
		

		stageDAO.save(stage);
		
		return "stage";
	}
	
	public List<Stage> findAllstages(){
		return stageDAO.findAll();
	}
	
	public Stage findOne(Long id){
		return stageDAO.findOne(id);
	}
	
	public List<Stage> findByQuery(){
		return stageDAO.findByQuery(query);
	}
	
	public String findStage() {
		return "stages-results";
	}	
	

}
