package hotelbooking;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author emort
 */
public class BookingHandler {
    private Map<String, Customer> customerMap;
    private HashMap<String, Room> roomsMap;

    public BookingHandler() {
        this.customerMap = fileManager.readCustomerInfo();
        this.roomsMap = fileManager.readRoomsFromFile();
    }

    public Map<String, Customer> getCustomerMap() {
        return customerMap;
    }

    public HashMap<String, Room> getRoomsMap() {
        return roomsMap;
    }

    public void addCustomer(Customer customer) {
        customerMap.put(customer.getPhoneNumber(), customer);
        fileManager.writeCustomerInfo((HashMap<String, Customer>) customerMap);
    }

    public Booking createBooking(String phoneNumber, String roomType, int nights) {
        Customer customer = customerMap.get(phoneNumber);
        Room selectedRoom = selectRoomByType(roomType);

        if (selectedRoom != null) {
            return new Booking(selectedRoom, customer, nights);
        }
        return null;
    }

    private Room selectRoomByType(String roomType) {
        return roomsMap.values().stream()
                .filter(room -> {
                    switch (roomType.toLowerCase()) {
                        case "single":
                            return room instanceof SingleRoom;
                        case "double":
                            return room instanceof DoubleRoom;
                        case "suite":
                            return room instanceof Suite;
                        default:
                            return false;
                    }
                })
                .filter(Room::isAvailable)
                .findFirst()
                .orElse(null);
    }
}
