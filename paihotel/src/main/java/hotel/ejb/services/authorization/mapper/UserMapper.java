package hotel.ejb.services.authorization.mapper;

import hotel.domain.User;
import hotel.ejb.services.authorization.dto.UserDTO;
/**
 * @author Karolina B¹tkowska
 */
public class UserMapper {

	static public User map(UserDTO userDTO) {
		return User.builder()
				.login(userDTO.getLogin())
				.password(userDTO.getPassword()) 
				.active(userDTO.isActive())
				.role(userDTO.getRole())
				.build();
	}

}
