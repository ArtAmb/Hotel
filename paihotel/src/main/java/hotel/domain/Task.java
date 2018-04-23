package hotel.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import hotel.dao.Domain;
import lombok.Data;

@Data
@Entity
public class Task implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date date;
	private String description;
	private String state;
	private String type;

	@ManyToOne
	private Hotel hotel;
	
	@ManyToOne
	private Party party;
	
	@ManyToOne
	private Booking booking;
}