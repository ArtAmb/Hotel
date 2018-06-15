package hotel.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.KeyCardDAO;
import hotel.domain.KeyCard;
import lombok.Data;
/**
 * @author Karolina B¹tkowska
 */
@Stateless
@Named
@Data
@LocalBean
public class KeyCardController {

	private KeyCard query = new KeyCard();
	
	private Long id;

	@EJB
	private KeyCardDAO keyCardDAO;

	public String saveKeyCard() {

		KeyCard keyCard = new KeyCard();
		

		keyCardDAO.save(keyCard);

		return "KeyCard";
	}

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

}
