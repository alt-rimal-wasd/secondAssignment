package project2;

public class HotelBookingMain {
    public static void main(String[] args) {
        Info info = new Info();
        info.connectHotelDB(); // Set up database tables
        System.out.println("Database setup complete.");

        DBManager.getInstance().closeConnections();
    }
}
