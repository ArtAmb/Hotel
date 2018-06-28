package hotel.ejb.controller;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import hotel.Utils;
import hotel.dao.KeyCardDAO;
import hotel.domain.KeyCard;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/**
 * @author Karolina B¹tkowska
 */
@RequestScoped
@Named
@Data
@ManagedBean
public class KeyCardController {

	private KeyCard query = new KeyCard();
	private KeyCard queryResponse = null;
	
	private Long id;

	public String findByCodeNumber() {
		queryResponse = keyCardDAO.findByQuery(query).stream().findFirst().orElse(null);
		return null;
	}
	
	@EJB
	private KeyCardDAO keyCardDAO;
	
	@Getter
	@Setter
	private KeyCard choosenCard = new KeyCard();

	public List<KeyCard> findAllKeyCards() {
		return keyCardDAO.findAll();
	}

	public KeyCard findOne(Long id) {
		return keyCardDAO.findOne(id);
	}

	public List<KeyCard> findByQuery() {
		return keyCardDAO.findByQuery(query);
	}

	public String findKeyCard() {
		return "cards-results";
	}
	public String showKeyCardDetail(long id) {
		choosenCard = keyCardDAO.findOne(id);
		return Utils.getTemplateUriRedirect("cards/card-detail");
	}
	public String activate(long id)
	{
		KeyCard card = keyCardDAO.findOne(id);
		if(card != null)
		{
			card.setActive(true);
			keyCardDAO.save(card);
		}
		

		return "card";
	}
	public String deactivate(long id)
	{
		KeyCard card = keyCardDAO.findOne(id);
		if(card != null)
		{
			card.setActive(false);
			keyCardDAO.save(card);
		}
		

		return "card";
	}

}
