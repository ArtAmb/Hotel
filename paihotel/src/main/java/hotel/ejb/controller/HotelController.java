package hotel.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.HotelDAO;
import hotel.domain.Hotel;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class HotelController {

	private Long id;	
	 
	private String name;
	private String address;
	
	private Hotel query = new Hotel();
	
	@EJB
	private HotelDAO hotelDAO;

	public String saveHotel() {

		Hotel hotel = new Hotel();
		hotel.setName(name);
		hotel.setAddress(address);
		
		hotelDAO.save(hotel);
		
		return "hotel";
	}
	
	public List<Hotel> findAllHotels(){
		return hotelDAO.findAll();
	}
	
	public Hotel findOne(Long id){
		return hotelDAO.findOne(id);
	}
	
	public List<Hotel> findByQuery(){
		return hotelDAO.findByQuery(query);
	}
	
	public String findHotel() {
		return "hotels-results";
	}
	
	
	
	

}
