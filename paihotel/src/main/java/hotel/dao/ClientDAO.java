package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Client;
import hotel.domain.User;

@Named
@Stateless
@LocalBean
public class ClientDAO extends CrudDAO<Long, Client> {
	
	public Client findByUser(User user) {
		return findByQuery(Client
				.builder()
				.user(user)
				.build()).stream()
				.findFirst()
				.orElse(null);
	}
}
