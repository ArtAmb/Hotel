package hotel.ejb.controller;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

import hotel.Utils;
import hotel.ejb.services.email.EmailDTO;
import hotel.ejb.services.email.EmailService;
import lombok.Data;

@Named
@RequestScoped
@Data
public class EmailController {

	private EmailService emailService = EmailService.getInstance();

	@Inject
	private MainMenuController mainMenuController;

	private String topic;
	private String message;
	private String email;

	private String errorMessage;

	void validate() {
		if (StringUtils.isNullOrEmpty(topic)) {
			throw new IllegalStateException("Prosze podac temat wiadomosci");
		}
		if (StringUtils.isNullOrEmpty(message)) {
			throw new IllegalStateException("Prosze podac tresc wiadomosci");
		}
		if (StringUtils.isNullOrEmpty(email)) {
			throw new IllegalStateException("Prosze podac email do korespondencji");
		}
		if (mainMenuController.getChosenHotel() == null) {
			throw new IllegalStateException("Prosze wybrac hotel");
		}
	}

	public String sendEmail() {
		try {
			validate();

			EmailDTO dto = EmailDTO.builder().to(emailService.getHotelEmailAddress())
					.content("Wiadomosc od: " + email + "\n\n\n" + message)
					.subject(mainMenuController.getChosenHotel().getName() + ": " + topic).build();

			emailService.sendEmail(dto);
		} catch (Exception e) {
			errorMessage = e.getMessage();
			return null;
		}

		return Utils.getTemplateUri("contact/contact-success");
	}
	
	public String sendEmailViaServlet() throws ClientProtocolException, IOException {
		try {
		validate();
		
		EmailDTO dto = EmailDTO.builder().to(emailService.getHotelEmailAddress())
				.content("Wiadomosc od: " + email + "\n\n\n" + message)
				.subject(mainMenuController.getChosenHotel().getName() + ": " + topic).build();

		CloseableHttpClient restClient = HttpClients.createDefault();
		
		
		HttpPost request = new HttpPost("http://localhost:8080/paihotel/emailServlet/sendEmail");
		request.addHeader("Content-Type", "application/json");
		String body = new Gson().toJson(dto);
		StringEntity entity = new StringEntity(body, "UTF-8");
		request.setEntity(entity);
		
		restClient.execute(request);
        
		} catch (Exception e) {
			errorMessage = e.getMessage();
			return null;
		}
		return Utils.getTemplateUri("contact/contact-success");
	}

}
