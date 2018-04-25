package hotel.ejb.controller;

import java.math.BigDecimal;
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
	private String name;
	private String description;
	private BigDecimal price;

	private Room query = new Room();
	
	@EJB
	private RoomDAO roomDAO;

	public String saveRoom() {

		Room room = new Room();
		room.setName(name);
		room.setPrice(price);
		room.setDescription(description);

		roomDAO.save(room);
		
		return "room";
	}
	
	public List<Room> findAllRooms(){
		return roomDAO.findAll();
	}
	
	public Room findOne(Long id){
		return roomDAO.findOne(id);
	}
	
	public List<Room> findByQuery(){
		return roomDAO.findByQuery(query);
	}
	
	public String findRoom() {
		return "rooms-results";
	}
	
	
	
	

}
