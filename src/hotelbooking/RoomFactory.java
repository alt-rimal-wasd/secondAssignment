/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbooking;

/**
 *
 * @author emort
 */
public class RoomFactory {
    public static Room createRoom(String type, String roomNumber, double price) {
        switch (type.toLowerCase()) {
            case "single":
                return new SingleRoom(roomNumber, price);
            case "double":
                return new DoubleRoom(roomNumber, price);
            case "suite":
                return new Suite(roomNumber, price);
            default:
                throw new IllegalArgumentException("Invalid room type: " + type);
        }
    }
}

