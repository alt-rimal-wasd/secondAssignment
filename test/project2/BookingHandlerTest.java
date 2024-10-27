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
}
