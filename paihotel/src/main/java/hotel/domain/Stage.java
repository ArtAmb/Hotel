package hotel.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import hotel.dao.Domain;
import lombok.Data;

@Data
@Entity
public class Stage implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Party party;
	
	private String type;
	private String state;
	private String description;
	@ManyToOne
	private Stage nextStage;
	@ManyToOne
	private Stage headStage;
}
