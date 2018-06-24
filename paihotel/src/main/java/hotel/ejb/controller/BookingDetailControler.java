package hotel.ejb.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.mysql.jdbc.StringUtils;

import hotel.Utils;
import hotel.dao.BillDAO;
import hotel.dao.BookingDAO;
import hotel.domain.Bill;
import hotel.domain.Booking;
import hotel.domain.BookingStatus;
import lombok.Getter;
import lombok.Setter;

@SessionScoped
@ManagedBean
@Named
public class BookingDetailControler implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private String errorMessage = "";

	@Getter
	@Setter
	private Booking choosenOne = new Booking();
	
	@EJB
	private BillDAO billDAO;
	

	@Getter
	@Setter
	private Bill newBill = new Bill();
	
	@EJB
	private BookingDAO bookingDAO;
	
	
	public String visitDetail(Long bookingId) {
		choosenOne = bookingDAO.findOne(bookingId);
		return Utils.getTemplateUriRedirect("visits/visit-detail");
	}
	
	public String acceptBooking() {
		choosenOne.setStatus(BookingStatus.IN_PROGRESS);
		choosenOne = bookingDAO.save(choosenOne);
		return null;
	}
	
	public String addNewBill() {
		if(StringUtils.isNullOrEmpty(newBill.getDescription()) || newBill.getPrice() == null) {
			errorMessage = "Cena i opis sa wymagane";
			return null;
		}
		
		newBill.setBooking(bookingDAO.findOne(choosenOne.getId()));
		billDAO.save(newBill);
		newBill = new Bill();
		return null;
	}
	
	public List<Bill> findAllBills() {
		return billDAO.findByQuery(Bill.builder().booking(choosenOne).build());
	}
}
