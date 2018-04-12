package hotel.ejb.controller;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
@LocalBean
public class NavigationController {

	
	public String moveToRooms() {
		return "room";
	}
}
