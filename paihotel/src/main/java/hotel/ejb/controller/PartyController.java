package hotel.ejb.controller;


import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.PartyDAO;
import hotel.domain.Party;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class PartyController {

	private Integer id;
	private String state;
	

	private Party query = new Party();
	
	@EJB
	private PartyDAO partyDAO;

	public String saveParty() {

		Party party = new Party();
		party.setState(state);
	

		partyDAO.save(party);
		
		return "party";
	}
	
	public List<Party> findAllParties(){
		return partyDAO.findAll();
	}
	
	public Party findOne(Long id){
		return partyDAO.findOne(id);
	}
	
	public List<Party> findByQuery(){
		return partyDAO.findByQuery(query);
	}
	
	public String findParty() {
		return "parties-results";
	}
	
	

}
