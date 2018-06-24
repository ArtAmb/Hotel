package hotel.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Room implements Domain {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;	 
	
	private String name;
	private String description;
	@Enumerated(EnumType.STRING)
	private RoomType state;
	private String type;
	
	private Integer number;
	private Integer floor;
	private Integer maxNumberOfPeople;
	
	private BigDecimal price;
	
	@ManyToOne
	private Gallery gallery;
	
	@ManyToOne
	private Hotel hotel;
	
	@OneToMany
	private List<Feature> features;
	
	@OneToMany
	private List<Offert> offerts;
}
