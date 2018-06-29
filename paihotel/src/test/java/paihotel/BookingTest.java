package paihotel;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import hotel.HotelConfigController;
import hotel.dao.BookingDAO;
import hotel.dao.ClientDAO;
import hotel.dao.KeyCardDAO;
import hotel.domain.Booking;
import hotel.domain.BookingStatus;
import hotel.domain.KeyCard;
import hotel.ejb.controller.CreateNewClientAcountController;

public class BookingTest {

	private HotelConfigController controller = new HotelConfigController();
	
	
	@Test
	public void tmp() {
		BookingDAO mockBookingDAO = mock(BookingDAO.class);
		when(mockBookingDAO.save(any())).thenReturn(null);
		
		KeyCardDAO keyCardDAO = mock(KeyCardDAO.class);
		when(keyCardDAO.save(any())).thenReturn(null);
		
		
		Booking booking = Booking.builder()
				.startDate(new Date(System.currentTimeMillis()))
				.status(BookingStatus.BOOKED)
				.build();
		
		Booking booking2 = Booking.builder()
				.startDate(new Date(System.currentTimeMillis()))
				.status(BookingStatus.IN_PROGRESS)
				.cards(Arrays.asList(KeyCard.builder().active(true).build(), KeyCard.builder().active(true).build()))
				.build();
		
		when(mockBookingDAO.findByQuery(any())).thenReturn(Arrays.asList(booking));
		when(mockBookingDAO.findByStatus(any())).thenReturn(Arrays.asList(booking2));
		
		controller.setBookingDAO(mockBookingDAO);
		controller.setKeyCardDAO(keyCardDAO);
		
		controller.hotelDayAction();
		
		verify(mockBookingDAO, times(1)).save(any());
		verify(keyCardDAO, times(2)).save(any());
	}
	
}
