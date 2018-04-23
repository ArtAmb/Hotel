package hotel.ejb.controller;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

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

	private MenuItem logIn = new MenuItem("client-account.xhtml", "Zaloguj"); // commandButtonie action=#{authorizationController.logIn()}
	private MenuItem logOut = new MenuItem("client-account.xhtml", "Profil"); // w profulu musi byc button wyloguj ktory wywola  authorizationController.logOut()

	public ArrayList<MenuItem> getDefaultItems() {
		return defaultItems;
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
