package hotel.ejb.controller;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.User;
import lombok.Data;

@Named
@Stateless
@LocalBean
public class MainMenuController {

	@EJB
	private AuthorizationController authorizationController; // TODO do zastnowienia czy to napewno trzyma sesje i stan

	private ArrayList<MenuItem> defaultItems = new ArrayList<MenuItem>(
			Arrays.asList(new MenuItem("hello.xhtml", "Start"), new MenuItem("booking.xhtml", "Rezerwacja"),
					new MenuItem("contact.xhtml", "Kontakt")));

	private MenuItem logIn = new MenuItem("login.xhtml", "Zaloguj"); // commandButtonie
																		// action=#{authorizationController.logIn()}
	private MenuItem logOut = new MenuItem("client-account.xhtml", "Profil"); // w profulu musi byc button wyloguj ktory
																				// wywola
																		// authorizationController.logOut()

	
	private ArrayList<MenuItem> employeeItems = new ArrayList<MenuItem>(
			Arrays.asList(new MenuItem("tasks.xhtml", "Zadania"), 
					new MenuItem("timetable.xhtml", "Terminarz"),
					new MenuItem("visits.xhtml", "Wizyty"), 
					new MenuItem("parties.xhtml", "Przyjecia"),
					new MenuItem("rooms.xhtml", "Pokoje"),
					new MenuItem("clients.xhtml", "Klienci"),
					new MenuItem("employees.xhtml", "Pracownicy")));
	
	private ArrayList<MenuItem> adminItems = new ArrayList<MenuItem>(
			Arrays.asList(new MenuItem("tasks.xhtml", "Zadania"), 
					new MenuItem("timetable.xhtml", "Terminarz"),
					new MenuItem("visits.xhtml", "Wizyty"), 
					new MenuItem("parties.xhtml", "Przyjecia"),
					new MenuItem("rooms.xhtml", "Pokoje"),
					new MenuItem("clients.xhtml", "Klienci"),
					new MenuItem("employees.xhtml", "Pracownicy"),
					new MenuItem("configuration.xhtml", "Konfiguracja")));
	
	private ArrayList<MenuItem> clientItems = new ArrayList<MenuItem>(
			Arrays.asList(new MenuItem("hello.xhtml", "Start"), new MenuItem("booking.xhtml", "Rezerwacja"),
					new MenuItem("contact.xhtml", "Kontakt")));
	
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

	private boolean isLogged = false;

	public MenuItem getLogInOut() {
		if (authorizationController.isLogIn())
			return logOut;
		else
			return logIn;
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
