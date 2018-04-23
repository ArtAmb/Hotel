package hotel.ejb.controller;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import lombok.Data;

@Named
@Stateless
@LocalBean
public class MainMenuController {

	private ArrayList<MenuItem> defaultItems = new ArrayList<MenuItem>(
			Arrays.asList(new MenuItem("hello.xhtml", "Start"), new MenuItem("booking.xhtml", "Rezerwacja"),
					new MenuItem("contact.xhtml", "Kontakt")));

	private MenuItem logIn = new MenuItem("client-account.xhtml", "Zaloguj");
	private MenuItem logOut = new MenuItem("client-account.xhtml", "Profil");

	public ArrayList<MenuItem> getDefaultItems() {
		return defaultItems;
	}

	private boolean isLogged = false;

	public MenuItem getLogInOut() {
		if (isLogged)
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
