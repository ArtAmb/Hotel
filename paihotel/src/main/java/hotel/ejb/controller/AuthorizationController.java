package hotel.ejb.controller;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.StringUtils;

import hotel.Utils;
import hotel.dao.UserDAO;
import hotel.domain.User;
import hotel.ejb.services.authorization.dto.Authorization;
import hotel.ejb.services.authorization.dto.UserDTO;
import hotel.ejb.services.authorization.mapper.UserMapper;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@RequestScoped
@Named
public class AuthorizationController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Authorization authorization = new Authorization();

	@Getter
	@Setter
	private UserDTO newUser = new UserDTO();
	
	@Getter
	@Setter
	private String errorMessage;


	@EJB
	private UserDAO userDAO;

	static public boolean isLogIn() {
		return getUser() != null;
	}

	static public User getUser() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		return (User) session.getAttribute("user");
	}

	public String addNewUser() {
		newUser.setActive(true);
		userDAO.save(UserMapper.map(newUser));
		return "hello";
	}
	
	public String addNewClient() {
		newUser.setActive(true);
		newUser.setRole("CLIENT");
		userDAO.save(UserMapper.map(newUser));
		return "hello";
	}

	public String tryToLogIn() {
		if(StringUtils.isNullOrEmpty(authorization.getLogin()) 
				|| StringUtils.isNullOrEmpty(authorization.getPassword())) {
			errorMessage = "Podaj login i has³o";
			return null;
			
		}
		
		Optional<User> optUser = userDAO.findByQuery(
				User.builder()
				.login(authorization.getLogin())
				.password(authorization.getPassword())
				.active(true)
				.build()).stream().findFirst();
		
		if (!optUser.isPresent()) {
			errorMessage = "Bledny login lub haslo";
			return null;
		}

		User user = optUser.get();
		
		if (!user.isActive()) {
			errorMessage = "User o login " + user.getLogin() + " jest zablokowany";
			return null; 
		}

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		session.setAttribute("user", user);

		return "/hello?faces-redirect=true"; 
	}

	public String logOutTheUser() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.removeAttribute("user");
		return Utils.getTemplateUriRedirect("authorization/login.xhtml");
	}

}
