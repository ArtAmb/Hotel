package servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hotel.ejb.EjbTest;


@WebServlet("/HelloWorldsama")
public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = -2186320550492241204L;
	
	@EJB
	EjbTest ejbTest;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   response.getWriter().write("I'm working!!!" + ejbTest.doSth());
	}

}
