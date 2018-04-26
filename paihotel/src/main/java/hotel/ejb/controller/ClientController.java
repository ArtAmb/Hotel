package hotel.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.ClientDAO;
import hotel.domain.Client;
import hotel.ejb.controller.DatatableService.TableData;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Stateless
@Named
@Data
@LocalBean
public class ClientController {

	@EJB
	private DatatableService datatableService;
	@EJB
	private ClientDAO clientDAO;
	
	private Integer id;
	private String name;
	private String surname;

	private String phone;
	private String email;

	private String street;
	private String homeNr;
	private String flatNr;
	private String zipCode;
	private String city;

	private Client query = new Client();

	private TableData clientData;

	

	public String saveClient() {

		Client client = new Client();
		client.setName(name);
		client.setSurname(surname);

		client.setPhone(phone);
		client.setEmail(email);

		client.setStreet(street);
		client.setHomeNr(homeNr);
		client.setFlatNr(flatNr);
		client.setZipCode(zipCode);
		client.setCity(city);

		clientDAO.save(client);

		return "client";
	}

	public List<Client> findAllClients() {
		return clientDAO.findAll();
	}

	public Client findOne(Long id) {
		return clientDAO.findOne(id);
	}

	

	public List<Client> findByQuery() {
		return clientDAO.findByQuery(query);
	}

	public TableData findAndFillDatatable() {
		clientData = datatableService.prepareDataForClient(clientDAO.findByQuery(query));
		return clientData;
	}

	public String findClient() {
		return "clients-results";
	}

}
