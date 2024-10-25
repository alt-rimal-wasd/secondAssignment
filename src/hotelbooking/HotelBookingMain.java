/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbooking;

/**
 *
 * @author emort
 */
public class HotelBookingMain {

    public static void main(String[] args) {
        HotelBooking hotelBooking = new HotelBooking();
        hotelBooking.connectHotelDB();
        hotelBooking.createCustomerTable();

        // Optionally, retrieve available rooms
        ResultSet availableRooms = hotelBooking.getAvailableRooms();
        // Process availableRooms as needed

        hotelBooking.closeConnection();
    }
}
