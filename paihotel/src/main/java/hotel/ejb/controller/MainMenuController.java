package hotel.ejb.controller;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.Utils;
import hotel.domain.User;
import lombok.Data;



@Named
@Stateless
@LocalBean
public class MainMenuController {

	@EJB
	private AuthorizationController authorizationController; // TODO do zastnowienia czy to napewno trzyma sesje i stan

	@EJB
	private ComponentController componentController;

	private ArrayList<MenuItem> defaultItems = new ArrayList<MenuItem>(
			Arrays.asList(
					new MenuItem(Utils.getUrl("hello.xhtml"), "Start"),
					new MenuItem(Utils.getViewUrl("booking/booking.xhtml"), "Rezerwacja"),
					new MenuItem(Utils.getViewUrl("contact/contact.xhtml"), "Kontakt")));

	private MenuItem logIn = new MenuItem(Utils.getViewUrl("authorization/login.xhtml"), "Zaloguj");
	private MenuItem logOut = new MenuItem(Utils.getViewUrl("client/client-account.xhtml"), "Profil");

	private ArrayList<MenuItem> employeeItems = new ArrayList<MenuItem>(
			Arrays.asList(
					new MenuItem(Utils.getViewUrl("tasks/tasks.xhtml"), "Zadania"),
					new MenuItem(Utils.getViewUrl("timetable/timetable.xhtml"), "Terminarz"),
					new MenuItem(Utils.getViewUrl("visits/visits.xhtml"), "Wizyty"),
					new MenuItem(Utils.getViewUrl("parties/parties.xhtml"), "Przyjecia"),
					new MenuItem(Utils.getViewUrl("room/rooms.xhtml"), "Pokoje"),
					new MenuItem(Utils.getViewUrl("client/clients.xhtml"), "Klienci"),
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
		User user = authorizationController.getUser();
		if (user == null)
			return getDefaultItems();
		switch (user.getRole()) {
		case "ADMIN":
			return adminItems;
		case "CLIENT":
			return clientItems;
		case "EMPLOYEE":
			return employeeItems;

		default:
			return getDefaultItems();
		}

	}

	public MenuItem getLogInOut() {
		if (authorizationController.isLogIn())
			return logOut;
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
