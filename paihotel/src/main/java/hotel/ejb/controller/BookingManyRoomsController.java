package hotel.ejb.controller;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import hotel.dao.BookingDAO;
import hotel.dao.RoomDAO;
import hotel.domain.Booking;
import hotel.domain.Room;
import lombok.Getter;
import lombok.Setter;

@Named
@SessionScoped
@ManagedBean
public class BookingManyRoomsController implements Serializable {

}
