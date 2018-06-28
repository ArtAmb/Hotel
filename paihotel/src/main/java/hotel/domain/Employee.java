package hotel.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import hotel.dao.Domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author Karolina B¹tkowska
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String surname;
	private String pesel;
	
	private String phone;
	private String email;
	
	private String street;
	private String homeNr;
	private String flatNr;
	private String zipCode;
	private String city; 

	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	@ManyToOne
	private Hotel hotel; 

}
