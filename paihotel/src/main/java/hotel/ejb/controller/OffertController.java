package hotel.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.OffertDAO;
import hotel.domain.Offert;
import lombok.Data;
/**
 * @author Karolina B¹tkowska
 */
@Stateless
@Named
@Data
@LocalBean
public class OffertController {

	private Integer id;
	private Integer priceDiscount;
	private Integer pepoleNumber; 

	private Offert query = new Offert();

	@EJB
	private OffertDAO offertDAO;

	public String saveOffert() {

		Offert offert = new Offert();
		offert.setPriceDiscount(priceDiscount);
		offert.setPepoleNumber(pepoleNumber);

		offertDAO.save(offert);

		return "offert";
	}

	public List<Offert> findAllOfferts() {
		return offertDAO.findAll();
	}

	public Offert findOne(Long id) {
		return offertDAO.findOne(id);
	}

	public List<Offert> findByQuery() {
		return offertDAO.findByQuery(query);
	}

}
