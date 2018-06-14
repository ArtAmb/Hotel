package hotel.ejb.services.authorization.dto;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Named;

import lombok.Data;
/**
 * @author Karolina B¹tkowska
 */
@Data
@Named
@LocalBean
@Stateful
public class UserDTO {
	private Long id;

	private String login;
	private String password; 
	private boolean active;
	private String role;
}
