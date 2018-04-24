package hotel.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

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
	private Authorization authorization = new Authorization();

	@Getter
	@Setter
	private String errorMessage;

	@Getter
	@Setter
	private User newUser = new User();

	@EJB
	private UserDAO userDAO;

	public boolean isLogIn() {
		return getUser() != null;
	}

	public User getUser() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		return (User) session.getAttribute("user");
	}

	public String addNewUser() {
		newUser.setActive(true);
		userDAO.save(newUser);
		return "hello";
	}

	@Data
	public class Authorization {
		private String login;
		private String password;
	}

	public String logIn() {
		User user = userDAO.findByQuery(User.builder().login(authorization.getLogin())
				.password(authorization.getPassword()).active(true).build()).iterator().next();
		if (user == null) {
			errorMessage = "Bledny login lub haslo";
		}

		if (!user.isActive()) {
			errorMessage = "User o login " + user.getLogin() + " jest zablokowany";
			return null; // TODO
		}

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		session.setAttribute("user", user);

		return null; // TODO udalo sie
	}

	public String logOut() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.removeAttribute("user");
		return null; // TODO strona logowania
	}

}
