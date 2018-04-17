package hotel.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.ejb.services.EmailService;
import lombok.Data;

@Named
@Stateless
@Data
public class EmailController {	

	@EJB
	private EmailService emailService;
	
	private String topic;
	private String message;
	private String email;
	
	public String sendEmail() {
		emailService.sendEmail();
		return "hello";
	}
	
	
}
