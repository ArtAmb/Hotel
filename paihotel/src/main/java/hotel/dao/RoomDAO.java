package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Room;

@Named
@Stateless
@LocalBean
public class RoomDAO extends CrudDAO<Integer, Room> {
	
	
}
