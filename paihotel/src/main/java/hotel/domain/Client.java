package hotel.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import hotel.dao.Domain;
import lombok.Data;

@Entity
@Data
public class Client implements Domain{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String surname;
	
	private String phone;
	private String email;
	
	private String street;
	private String homeNr;
	private String flatNr;
	private String zipCode;
	private String city;
	
	
}
