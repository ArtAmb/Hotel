package hotel.ejb.services.authorization.dto;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Named;

import lombok.Data;
/**
 * @author Karolina B�tkowska
 */
@Data
@LocalBean
@Named
@Stateful
public class Authorization {
	private String login;
	private String password;
}