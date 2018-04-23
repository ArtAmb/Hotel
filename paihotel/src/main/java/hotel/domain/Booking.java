package hotel.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import hotel.dao.Domain;
import lombok.Data;

@Entity
@Data 
public class Booking implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date startDate;
	private Date endDate;
	
	@ManyToOne
	private Room room;
	@ManyToOne
	private Client client;
	
	@OneToMany
	@JoinColumn(name = "booking")
	private List<KeyCard> cards;
}