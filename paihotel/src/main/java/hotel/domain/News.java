package hotel.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hotel.dao.Domain;
import lombok.Data;

@Entity
@Data
public class News implements Domain {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;	 
	
	private String title;
	private String description;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

}
