package hotel.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import hotel.dao.Domain;
import lombok.Data;
/**
 * @author Karolina B¹tkowska
 */
@Data
@Entity
public class Employee implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String surname;

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "user")
	private User user;
	
	@ManyToOne
	private Hotel hotel; 

}
