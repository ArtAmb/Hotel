package hotel;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.BookingDAO;
import hotel.domain.Booking;
import hotel.domain.BookingStatus;
import lombok.Getter;
import lombok.Setter;

@Named
@LocalBean
@Stateless
public class HotelConfigController {

	@EJB
	private BookingDAO bookingDAO;

	@Getter
	@Setter
	private Long hotelDayInMS = 10000l;
	
	@Getter
	@Setter
	private Long timeUntilBookingCancelInMS = 10000l;

	public String startHotelDayThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(hotelDayInMS);
					hotelDayAction();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		return null;
	}

	public String hotelDayAction() {

		System.out.println("Zaczynam dobre hotelowa");

		Date currDate = new Date(System.currentTimeMillis());

		Booking query = Booking.builder().startDate(currDate).build();
		List<Booking> bookings = bookingDAO.findByQuery(query);

		for(Booking booking: bookings) {
			if(booking.getStatus() == null || booking.getStatus().equals(BookingStatus.BOOKED))
				booking.setStatus(BookingStatus.WAITING_FOR_ACCEPT);
			
			bookingDAO.save(booking);
		}
		
		return null;
	}
}
