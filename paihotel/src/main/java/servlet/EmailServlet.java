package servlet;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hotel.ejb.services.email.EmailDTO;
import hotel.ejb.services.email.EmailService;


@WebServlet("/emailServlet/sendEmail")
public class EmailServlet extends HttpServlet {
	private static final long serialVersionUID = -2186320550492241204L;
	
	private EmailService emailService = EmailService.getInstance();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
//		String to = request.getParameter("to");
//		String content = request.getParameter("content");
//		String subject = request.getParameter("subject");
		EmailDTO dto = new Gson().fromJson(request.getReader(), EmailDTO.class);
		
		//EmailDTO.builder()
//				.to(to)
//				.content(content)
//				.subject(subject).build();

		
			emailService.sendEmail(dto);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	

}
