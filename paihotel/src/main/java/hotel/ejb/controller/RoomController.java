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
public class RoomController {

	private String roomName;

	@EJB
	private HotelDAO hotelDAO;

	public String saveRoom() {

		Hotel hotel = new Hotel();
		hotel.setName(roomName);

		hotelDAO.save(hotel);
		
		return "room";
	}
	
	public List<Hotel> findAllRooms(){
		return hotelDAO.findAll();
	}

}
