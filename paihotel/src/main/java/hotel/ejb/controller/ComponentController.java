package hotel.ejb.controller;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
@LocalBean
public class ComponentController {

	public String getMainMenu() {
		return "mainMenu.xhtml";
	}
	
	public String getHead() {
		return "head.xhtml";
	}
	
}
