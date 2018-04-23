package hotel.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import hotel.dao.Domain;
import lombok.Data;

@Entity
@Data
public class Bill implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private BigDecimal price;
	private String state;
	
	@ManyToOne
	private Booking booking;
	
	@ManyToOne
	private Party party;
	
}