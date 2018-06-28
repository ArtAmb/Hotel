package hotel.ejb.services.email;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import hotel.ejb.controller.EmailController;
import lombok.Getter;

@Named
@ManagedBean
@ApplicationScoped
public class EmailService {
	@Getter
	private String hotelEmailAddress = "paihotele@gmail.com";
	
	public void sendEmail(EmailDTO dto) { 

		try {
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		
		Session mailSession = Session.getDefaultInstance(props, null);
    	mailSession.setDebug(true);
    	
    	Message message = new MimeMessage(mailSession);
		
		message.setFrom(new InternetAddress(hotelEmailAddress));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(dto.getTo()));
		message.setContent(message, "text/html");
		message.setSubject(dto.getSubject());
		message.setText(dto.getContent());
	
		Transport transport = mailSession.getTransport("smtps");
		transport.connect("smtp.gmail.com", "paihotele", "pai_hotele_123");

		transport.sendMessage(message, message.getAllRecipients());
		
		
    }catch (Exception ex) {
			Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
