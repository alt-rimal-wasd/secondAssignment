/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

import Project1.Booking;
import Project1.Customer;
import Project1.DoubleRoom;
import Project1.Room;
import Project1.SingleRoom;
import Project1.Suite;
import Project1.fileManager;
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
        if (customer == null) {
            return null; // Customer does not exist
        }

        Room selectedRoom = selectRoomByType(roomType);
        if (selectedRoom != null && selectedRoom.isAvailable()) {
            System.out.println("Selected room instance: " + selectedRoom);
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
