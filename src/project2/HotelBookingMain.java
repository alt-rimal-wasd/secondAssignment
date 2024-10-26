package project2;

import java.sql.ResultSet;

public class HotelBookingMain {
    public static void main(String[] args) {
        Info info = new Info();
        info.connectHotelDB(); // Set up database tables
        System.out.println("Database setup complete.");

        // Retrieve available rooms
        try {
            ResultSet availableRooms = info.getAvailableRooms();
           
        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManager.getInstance().closeConnections();
    }
}
