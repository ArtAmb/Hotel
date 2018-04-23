package hotel.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Hotel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer hotelId;
	
	 
	private String nameHotel;
}
