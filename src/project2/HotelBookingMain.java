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
            while (availableRooms.next()) {
                System.out.println("Room ID: " + availableRooms.getString("ROOMID"));
                System.out.println("Type: " + availableRooms.getString("TYPE"));
                System.out.println("Price: " + availableRooms.getDouble("PRICE"));
                System.out.println("Available: " + availableRooms.getBoolean("AVAILABLE"));
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManager.getInstance().closeConnections();
    }
}
