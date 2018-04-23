package hotel.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;



import hotel.dao.RoomDAO;
import hotel.domain.Room;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class RoomController {

	private Integer id;
	private String roomName;
	private String description;
	private float price;

	@EJB
	private RoomDAO roomDAO;

	public String saveRoom() {

		Room room = new Room();
		room.setNameRoom(roomName);
		room.setPriceRoom(price);
		room.setDescriptionRoom(description);

		roomDAO.save(room);
		
		return "room";
	}
	
	public List<Room> findAllRooms(){
		return roomDAO.findAll();
	}
	
	public Room findOne(Integer id){
		return roomDAO.findOne(id);
	}
	

}
