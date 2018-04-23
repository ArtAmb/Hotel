package hotel.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import hotel.dao.Domain;
import lombok.Data;

@Entity
@Data
public class KeyCardUsage implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private KeyCard keyCard;
	
	@ManyToOne
	private Booking booking;
	
	@ManyToOne
	private Room room;
	
	private String description;
}