package hotel.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Named;

import hotel.Utils;
import hotel.dao.HotelDAO;
import hotel.domain.Hotel;
import lombok.Getter;
import lombok.Setter;

@Stateful
@LocalBean
@Named
public class HotelUpdateController {

	@EJB
	private HotelDAO hotelDAO;
	
	@Getter
	@Setter
	private Hotel hotel;

	
	public String editHotel(Long hotelId) {
		hotel = hotelDAO.findOne(hotelId);
		return Utils.getTemplateUri("config/edit-hotel.xhtml");
	}
	
	public String saveHotel() {
		if(hotel == null)
			return null;
		hotelDAO.save(hotel);
		
		return Utils.getTemplateUri("config/hotel-config.xhtml");
	}
	
}
