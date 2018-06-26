package hotel.ejb.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import hotel.Utils;
import hotel.dao.HotelDAO;
import hotel.domain.Hotel;
import hotel.domain.User;
import hotel.domain.UserRole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Karolina B¹tkowska
 */

@Named
@SessionScoped
@ManagedBean
public class MainMenuController implements Serializable {

	private static final long serialVersionUID = -7364807293944827972L;

	@EJB
	private ComponentController componentController;

	@EJB
	private HotelDAO hotelDAO;
	
	@Getter
	@Setter
	private Long hotelId; 
	
	@Getter
	@Setter
	private Hotel chosenHotel;
	
	public String switchHotel() {
		chosenHotel = hotelDAO.findOne(hotelId);
		return null;
	}
	
	@PostConstruct
	private void init() {
		chosenHotel = hotelDAO.findAll().stream().findFirst().orElse(null);
	}
	
	private ArrayList<MenuItem> defaultItems = new ArrayList<MenuItem>(
			Arrays.asList(
					new MenuItem(Utils.getUrl("hello.xhtml"), "Start"),
					new MenuItem(Utils.getViewUrl("booking/booking.xhtml"), "Rezerwacja"),
					new MenuItem(Utils.getViewUrl("contact/contact.xhtml"), "Kontakt")));

	private MenuItem logIn = new MenuItem(Utils.getViewUrl("authorization/login.xhtml"), "Zaloguj");
	private MenuItem logOut = new MenuItem(Utils.getViewUrl("client/client-account.xhtml"), "Profil");
	private MenuItem logOutForEmployee = new MenuItem(Utils.getViewUrl("authorization/logout.xhtml"), "Profil");

	private ArrayList<MenuItem> employeeItems = new ArrayList<MenuItem>(
			Arrays.asList(
					new MenuItem(Utils.getViewUrl("tasks/tasks.xhtml"), "Zadania"),
					//new MenuItem(Utils.getViewUrl("timetable/timetable.xhtml"), "Terminarz"),
					new MenuItem(Utils.getViewUrl("visits/visits.xhtml"), "Wizyty"),
					new MenuItem(Utils.getViewUrl("parties/parties.xhtml"), "Przyjecia"),
					new MenuItem(Utils.getViewUrl("room/rooms.xhtml"), "Pokoje"),
					new MenuItem(Utils.getViewUrl("client/clients.xhtml"), "Klienci"),
					new MenuItem(Utils.getViewUrl("card/cards.xhtml"), "Karty"),
					new MenuItem(Utils.getViewUrl("employee/employees.xhtml"), "Pracownicy")));

	private ArrayList<MenuItem> adminItems = new ArrayList<MenuItem>(
			Arrays.asList(
					new MenuItem(Utils.getViewUrl("tasks/tasks.xhtml"), "Zadania"),
					new MenuItem(Utils.getViewUrl("timetable/timetable.xhtml"), "Terminarz"),
					new MenuItem(Utils.getViewUrl("visits/visits.xhtml"), "Wizyty"),
					new MenuItem(Utils.getViewUrl("parties/parties.xhtml"), "Przyjecia"),
					new MenuItem(Utils.getViewUrl("room/rooms.xhtml"), "Pokoje"),
					new MenuItem(Utils.getViewUrl("client/clients.xhtml"), "Klienci"),
					new MenuItem(Utils.getViewUrl("employee/employees.xhtml"), "Pracownicy"),
					new MenuItem(Utils.getViewUrl("config/configuration.xhtml"), "Konfiguracja")));

	private ArrayList<MenuItem> clientItems = new ArrayList<MenuItem>(
			Arrays.asList(
					new MenuItem(Utils.getUrl("hello.xhtml"), "Start"),
					new MenuItem(Utils.getViewUrl("booking/booking.xhtml"), "Rezerwacja"),
					new MenuItem(Utils.getViewUrl("contact/contact.xhtml"), "Kontakt")));

	public ArrayList<MenuItem> getDefaultItems() {
		return defaultItems;
	}

	public ArrayList<MenuItem> getMenu() {
		User user = AuthorizationController.getUser();
		if (user == null)
			return getDefaultItems();
		switch (user.getRole()) {
		case ADMIN:
			return adminItems;
		case CLIENT:
			return clientItems;
		case EMPLOYEE:
			return employeeItems;

		default:
			return getDefaultItems();
		}

	}

	public MenuItem getLogInOut() {
		if (AuthorizationController.isLogIn()) {
			UserRole userRole = AuthorizationController.getUser().getRole();
			if(userRole != null && (userRole.equals(UserRole.ADMIN) || userRole.equals(UserRole.EMPLOYEE)))
				return logOutForEmployee;
			
			return logOut;
		}
		else
			return logIn;
	}

	public String getLogoUrl() {
		return Utils.getUrl("img/logo.png");
	}

	public String getHelloPageUrl() {
		return Utils.getUrl("hello.xhtml");
	}
	
	public String getHeaderImgUrl() {
		return Utils.getUrl("img/header.jpg");
	}
	

	@Data
	public static class MenuItem {
		public MenuItem(String redirect, String name) {
			this.name = name;
			this.redirectPath = redirect;
		}

		private String redirectPath;
		private String name;
	}
}
