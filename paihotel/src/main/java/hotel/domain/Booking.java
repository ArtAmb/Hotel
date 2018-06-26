package hotel.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import hotel.dao.Domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date startDate;
	private Date endDate;
	
	@Enumerated(EnumType.STRING)
	private BookingStatus status;
	
	@ManyToOne
	private Client client;
	@ManyToMany
	private List<Room> rooms;
	@ManyToMany
	private List<Client> guests;
	@OneToMany
	@JoinColumn(name = "booking")
	private List<KeyCard> cards;
}