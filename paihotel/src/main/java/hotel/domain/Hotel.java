package hotel.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import hotel.dao.Domain;
import lombok.Data;
/**
 * @author Karolina B�tkowska
 */
@Entity
@Data
public class Hotel implements Domain {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;	
	 
	private String name;
	private String address; 
}
