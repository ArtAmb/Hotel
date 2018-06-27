package hotel.ejb.services;

import hotel.domain.Client;
import java.sql.Date;

public enum SessionObject {
	ROOM_ID("roomId", Long.class), CONTROL_BOOKING_PARAM("controlBookingParam", String.class), BOOKING_CLIENT("bookingClient", Client.class),
	BOOKING_START_DATE("startDate", Date.class), BOOKING_END_DATE("endDate", Date.class);
	
	private String name;
	private Class<?> objectClass;
	
	private SessionObject(String name, Class<?> objectClass) {
		this.name = name;
		this.objectClass = objectClass;
	}
	
	public String getName() {
		return name; 
	}
	
	public Class<?> getObjectClass(){
		return objectClass;
	}
}
