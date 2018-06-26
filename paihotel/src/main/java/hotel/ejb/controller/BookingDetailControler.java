package hotel.ejb.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.mysql.jdbc.StringUtils;

import hotel.Utils;
import hotel.dao.BillDAO;
import hotel.dao.BookingDAO;
import hotel.dao.TaskDAO;
import hotel.domain.Bill;
import hotel.domain.BillState;
import hotel.domain.Booking;
import hotel.domain.BookingStatus;
import hotel.domain.Room;
import hotel.domain.Task;
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
	private BigDecimal totalCost = new BigDecimal(0);
	
	@Getter
	@Setter
	private Bill newBill = new Bill();
	
	@EJB
	private BookingDAO bookingDAO;
	
	@EJB
	private TaskDAO taskDAO;
	
	
	public String visitDetail(Long bookingId) {
		choosenOne = bookingDAO.findOne(bookingId);
		return Utils.getTemplateUriRedirect("visits/visit-detail");
	}
	
	public String acceptBooking() {
		if(choosenOne.getGuests() == null || choosenOne.getGuests().isEmpty()) {
			errorMessage = "Musisz zameldowaæ gosci";
			return null;
		}
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
	

	public String finishVisit() {
		BigDecimal priceForRooms = choosenOne.getRooms().stream().map(Room::getPrice).reduce((r1, r2)->{r1= r1.add(r2); return r1;}).get();
		BigDecimal priceForBills = findAllBills().stream().map(Bill::getPrice).reduce((b1, b2)->{b1= b1.add(b2); return b1;}).get();
		
		totalCost = priceForRooms.add(priceForBills);
		
		Bill finalBill = billDAO.save(Bill.builder().booking(choosenOne).price(totalCost).state(BillState.FINAL_TO_PAY).build());
		
		
		for(Room room : choosenOne.getRooms()) {
			taskDAO.save(Task.builder().description("Sprzatanie pokoju").booking(choosenOne).hotel(room.getHotel()).room(room).date(new Date(System.currentTimeMillis())).build());
		}
		return null;
	}
}
