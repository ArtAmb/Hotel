package hotel.ejb.controller;

import java.util.Optional;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.mysql.jdbc.StringUtils;

import hotel.Utils;
import hotel.dao.ClientDAO;
import hotel.dao.UserDAO;
import hotel.domain.Client;
import hotel.domain.User;
import hotel.domain.UserRole;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
@ManagedBean
public class CreateNewClientAcountController {

	@Getter
	@Setter
	private User user = new User();
	@Getter
	@Setter
	private Client client = new Client();

	@EJB
	private ClientDAO clientDAO;

	@EJB
	private UserDAO userDAO;
	@Getter
	@Setter
	private String repeatedPassword;

	@Getter
	@Setter
	private String errorMessage;

	public void setClientDAO(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}
	
	public void validateClientPart() {
		if (StringUtils.isNullOrEmpty(client.getName()))
			throw new IllegalStateException("Imie jest wymagane");
		if (StringUtils.isNullOrEmpty(client.getSurname()))
			throw new IllegalStateException("Nazwisko jest wymagane");
		if (StringUtils.isNullOrEmpty(client.getEmail()))
			throw new IllegalStateException("Emial jest wymagany");
		if (StringUtils.isNullOrEmpty(client.getPhone()))
			throw new IllegalStateException("Telefon jest wymagany");
		if (StringUtils.isNullOrEmpty(client.getStreet()))
			throw new IllegalStateException("Ulica jest wymagana");
		if (StringUtils.isNullOrEmpty(client.getHomeNr()))
			throw new IllegalStateException("Numer domu jest wymagany");
		if (StringUtils.isNullOrEmpty(client.getPhone()))
			throw new IllegalStateException("Telefon jest wymagany");
		if (StringUtils.isNullOrEmpty(client.getCity()))
			throw new IllegalStateException("Miasto jest wymagany");
	}

	public void validateUserPart() {
		if (StringUtils.isNullOrEmpty(user.getLogin()))
			throw new IllegalStateException("Login jest wymagane");
		if (StringUtils.isNullOrEmpty(user.getPassword()))
			throw new IllegalStateException("Haslo jest wymagane");
		if (!user.getPassword().equals(repeatedPassword)) {
			throw new IllegalStateException("Oba has³a musza byc takie same");
		}
	}

	public String saveNewClient() {
		try {
			validateClientPart();
			validateUserPart();
		} catch (Exception e) {
			errorMessage = e.getMessage();
			return null;
		}
		Optional<Client> optClient = clientDAO.findByQuery(Client.builder().email(client.getEmail()).build()).stream()
				.findFirst();
		if (optClient.isPresent()) {
			errorMessage = "Klient o takim adresie emial istnieje";
			return null;
		}

		Optional<User> optUser = userDAO.findByQuery(User.builder().login(user.getLogin()).active(true).role(UserRole.CLIENT).build()).stream()
				.findFirst();
		if (optUser.isPresent()) {
			errorMessage = "Uzytkownik o takim loginie istnieje";
			return null;
		}

		User newUser = userDAO.save(user);
		client.setUser(newUser);

		clientDAO.save(client);
		return Utils.getViewUrl("authorization/create-account-success");

	}

	public String redirectToFrom() {
		return Utils.getTemplateUriRedirect("authorization/create-account-for-client");
	}

}
