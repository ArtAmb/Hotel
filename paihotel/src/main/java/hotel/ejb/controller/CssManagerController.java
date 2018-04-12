package hotel.ejb.controller;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
@LocalBean
public class CssManagerController {
	private final String basePath = "css/";

	private String getCssPath(String cssPath) {
		return basePath + cssPath;
	}
	
	
	public String getMainCss() {
		return getCssPath("main.css");
	}
	
	public String getBootstrapCss() {
		return getCssPath("bootstrap.min.css");
	}
	
}
