/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project1;

/**
 *
 * @author emort
 */
public abstract class Room implements RoomType {

    private String roomNumber;
    private double price;
    private boolean available;

    public Room(String roomNumber, double price) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.available = true;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String bookingConfirmation(int nights) {

        return null;

    }
}
