package hotel.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.UserDAO;
import hotel.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Stateless
@LocalBean
@Named
public class AuthorizationController {

	@Getter
	@Setter
	private Authorization authorization;
	
	@Getter
	@Setter
	private String errorMessage; 
	
	private User user = null;
	
	@EJB
	private UserDAO userDAO;
	
	public boolean isLogIn() {
		return user == null;
	}
	
	@Data
	public class Authorization {
		private String login;
		private String password;
	}
	
	public String logIn() {
		user = userDAO.findByQuery(User.builder()
				.login(authorization.getLogin())
				.password(authorization.getPassword()).build()).iterator().next();
		if(user == null) {
			errorMessage = "Bledny login lub haslo";
			return null; // TODO
		}
		
		if(!user.isActive()) {
			errorMessage = "User o login " + user.getLogin() + " jest zablokowany";
			return null; // TODO
		}
		
		return null; // TODO udalo sie
	}
	
	public String logOut() {
		user = null;
		
		return null; // TODO strona logowania
	}
	
}
