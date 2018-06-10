package hotel.ejb.controller;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.BookingDAO;
import hotel.domain.Booking;
import lombok.Data;

@Stateless
@Named
@Data
@LocalBean
public class BookingController {

	private Integer id;
	private Date startDate;
	private Date endDate;
	

	private Booking query = new Booking();
	
	@EJB
	private BookingDAO bookingDAO;

	public String saveBooking() {

		Booking booking = new Booking();
		booking.setStartDate(startDate);
		booking.setEndDate(endDate);

		bookingDAO.save(booking);
		
		return "booking";
	}
	
	public List<Booking> findAllBookings(){
		return bookingDAO.findAll();
	}
	
	public Booking findOne(Long id){
		return bookingDAO.findOne(id);
	}
	
	public List<Booking> findByQuery(){
		return bookingDAO.findByQuery(query);
	}
	public String findBooking() { 
		return "/templates/visits/visits-results";
	}
	
	

}
