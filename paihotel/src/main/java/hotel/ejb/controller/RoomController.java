package hotel.ejb.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.RoomDAO;
import hotel.domain.Hotel;
import hotel.domain.Room;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class RoomController {

	private String name;
	private String description;
	private String state;
	private String type;
	
	private Integer number;
	private Integer floor;	
	private Integer maxNumberOfPeople;
	
	private BigDecimal price;
	
	private Hotel hotel;
	

	private Room query = new Room();
	
	@EJB
	private RoomDAO roomDAO;

	public String saveRoom() {

		Room room = new Room();
		room.setName(name);
		room.setPrice(price);
		room.setDescription(description);
		room.setType(type);
		room.setState(state);
		room.setFloor(floor);
		room.setNumber(number);
		room.setMaxNumberOfPeople(maxNumberOfPeople);
		

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
