package hotel;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import hotel.dao.BookingDAO;
import hotel.dao.KeyCardDAO;
import hotel.domain.Booking;
import hotel.domain.BookingStatus;
import hotel.domain.KeyCard;
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
	private Integer timeUntilBookingCancelInDay = 1;

	@EJB
	private KeyCardDAO keyCardDAO;
	
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
		
		
		List<Booking> bookingsInProgress = bookingDAO.findByStatus(BookingStatus.IN_PROGRESS);

		for(Booking booking: bookingsInProgress) {
			for(KeyCard card : booking.getCards()) {
				card.setActive(false);
				keyCardDAO.save(card);
			}
		}
		
		Date expiredBookingDate = Utils.addDays(currDate, -timeUntilBookingCancelInDay);
		
		List<Booking> expiredBookings = bookingDAO.findByStatus(BookingStatus.BOOKED);
		expiredBookings = expiredBookings.stream().filter(b->b.getStartDate()!=null).filter(b->b.getStartDate().toString().equals(expiredBookingDate.toString())).collect(Collectors.toList());
		
		for(Booking booking: expiredBookings) {
			booking.setStatus(BookingStatus.CANCELLED);
			bookingDAO.save(booking);
		}

		return null;
	}
}
