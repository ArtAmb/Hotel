package hotel.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.domain.Client;

@Named
@Stateless
@LocalBean
public class ClientDAO extends CrudDAO<Long, Client> {
	
	
}
