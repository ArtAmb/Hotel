package hotel.ejb.controller;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mysql.jdbc.StringUtils;

import hotel.dao.FeatureDAO;
import hotel.dao.HotelDAO;
import hotel.dao.RoomDAO;
import hotel.domain.Room;
import hotel.domain.RoomType;
import lombok.Data;

/**
 * @author Artur Ambrolewicz
 */
@RequestScoped
@Named
@Data
@ManagedBean
public class RoomController {

	private String name;
	private String description;
	private String state;
	private String type = RoomType.ROOM.name();

	private Integer number;
	private Integer floor;
	private Integer maxNumberOfPeople;
	private String message = "";

	private BigDecimal price;

	List<Long> features = new LinkedList<>();

	private Room query = new Room();

	@Inject
	private MainMenuController mainMenuController;

	@EJB
	private RoomDAO roomDAO;
	@EJB
	private HotelDAO hotelDAO;

	@EJB
	private FeatureDAO featureDAO;

	void validate() {
		if (StringUtils.isNullOrEmpty(name)) {
			throw new IllegalStateException("Prosze podac nazwe pokoju");
		}
		if (price == null) {
			throw new IllegalStateException("Prosze podac cene pokoju");
		}
		if (StringUtils.isNullOrEmpty(description)) {
			throw new IllegalStateException("Prosze podac opis pokoju");
		}
		if (floor == null) {
			throw new IllegalStateException("Prosze podac pietro pokoju");
		}
		if (number == null) {
			throw new IllegalStateException("Prosze podac numer pokoju");
		}
		if (maxNumberOfPeople == null) {
			throw new IllegalStateException("Prosze podac maksymalna liczbe osob w pokoju");
		}
	}

	void reset() {
		name = null;
		description = null;
		state = null;
		type = null;
		number = null;
		floor = null;
		maxNumberOfPeople = null;
		message = "Dodano pok�j";
	}

	public String saveRoom() {
		try {
			validate();

			Room room = new Room();
			room.setName(name);
			room.setPrice(price);
			room.setDescription(description);
			room.setType(type);
			room.setState(RoomType.valueOf(state));
			room.setFloor(floor);
			room.setNumber(number);
			room.setMaxNumberOfPeople(maxNumberOfPeople);
			room.setHotel(mainMenuController.getChosenHotel());
			room.setFeatures(features.stream().map(id -> featureDAO.findOne(id)).collect(Collectors.toList()));

			roomDAO.save(room);
		} catch (Exception e) {
			message = e.getMessage();
			return null;
		}
		reset();
		return null;
	}

	public List<Room> findAllRooms() {
		return roomDAO.findByQuery(Room.builder().hotel(mainMenuController.getChosenHotel()).build());
	}

	public Room findOne(Long id) {
		return roomDAO.findOne(id);
	}

	public List<Room> findByQuery() {
		query.setHotel(mainMenuController.getChosenHotel());
		return roomDAO.findByQuery(query);
	}

	public String findRoom() {
		return "rooms-results";
	}

}
