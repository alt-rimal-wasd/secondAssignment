package project2;

import Project1.Booking;
import Project1.Customer;
import Project1.Room;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookingHandlerTest {

    private BookingHandler bookingHandler;

    @Before
    public void setUp() {
        bookingHandler = new BookingHandler();
    }

    @Test
    public void testGetCustomerMap() {
        assertNotNull("Customer map should be initialized", bookingHandler.getCustomerMap());
    }

    @Test
    public void testGetRoomsMap() {
        assertNotNull("Rooms map should be initialized", bookingHandler.getRoomsMap());
    }

    @Test
    public void testAddCustomer() {
        Customer customer = new Customer("James Smith", "0123456789", "james.smith@example.com");
        bookingHandler.addCustomer(customer);
        assertEquals("Customer should be added", customer, bookingHandler.getCustomerMap().get("0123456789"));
    }

    @Test
    public void testCreateBooking() {
        Customer customer = new Customer("John Golden", "0987654321", "john.golden@example.com");
        bookingHandler.addCustomer(customer);
        Room room = CreateRoom.createRoom("single", "s001", 100.0);
        bookingHandler.getRoomsMap().put("s001", room);

        System.out.println("Created room instance: " + room);
        Booking booking = bookingHandler.createBooking("0987654321", "single", 3);
        System.out.println("Booking room instance: " + booking.getRoom());

        assertNotNull("Booking should be created", booking);
        assertEquals("Booking should have the correct room", room, booking.getRoom());
        assertEquals("Booking should have the correct customer", customer, booking.getCustomer());
    }

    @Test
    public void testCreateBookingWithUnavailableRoom() {
        Customer customer = new Customer("John Golden", "0987654321", "john.golden@example.com");
        bookingHandler.addCustomer(customer);
        Room room = CreateRoom.createRoom("single", "s001", 100.0);
        room.setAvailable(false); // Set room to unavailable
        bookingHandler.getRoomsMap().put("s001", room);

        Booking booking = bookingHandler.createBooking("0987654321", "single", 3);
        assertNull("Booking should not be created for unavailable room", booking);
    }

    @Test
    public void testCreateBookingForNonexistentCustomer() {
        Booking booking = bookingHandler.createBooking("9999999999", "single", 3);
        assertNull("Booking should not be created for non-existent customer", booking);
    }
}
