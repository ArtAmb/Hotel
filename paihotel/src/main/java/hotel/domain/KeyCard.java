package hotel.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import hotel.dao.Domain;
import lombok.Data;
/**
 * @author Karolina B¹tkowska
 */
@Entity
@Data
public class KeyCard implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private CardStatus state;
	
	private String comments;
	private Integer codeNumber;
}