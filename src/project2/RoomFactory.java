package project2;

import Project1.DoubleRoom;
import Project1.Room;
import Project1.SingleRoom;
import Project1.Suite;

public class RoomFactory {
    
    // method to create a room of the specified type
    public static Room createRoom(String type, String roomNumber, double price) {
        switch (type.toLowerCase()) {
            case "single":
                return new SingleRoom(roomNumber, price);
            case "double":
                return new DoubleRoom(roomNumber, price);
            case "suite":
                return new Suite(roomNumber, price);
            default:
                throw new IllegalArgumentException("Invalid room type: " + type); // Handle invalid room type
        }
    }
}
