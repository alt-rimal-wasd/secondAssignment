/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbooking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author emort
 */
//Main class for handling hotel bookings.
public class HotelBooking {

    public static void main(String[] args) {
        // Load customer and room data from files
        Map<String, Customer> customerMap = fileManager.readCustomerInfo();
        HashMap<String, Room> roomsMap = fileManager.readRoomsFromFile();
        // Use Scanner for user input
        try ( Scanner scanner = new Scanner(System.in)) {
            String response;
            // Loop to ensure valid input for previous stay question
            while (true) {
                System.out.print("Have you stayed with us before (yes/no)? ");
                response = scanner.nextLine().trim();
                if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            }

            Customer customer = null;
            String phoneNumber = null;
            boolean isReturningCustomer = false;
            // Process returning customers
            if (response.equalsIgnoreCase("yes")) {
                System.out.print("Please enter your phone number (or type 'quit' to exit): ");
                phoneNumber = scanner.nextLine();
                if (phoneNumber.equalsIgnoreCase("quit")) {
                    System.out.println("Thank you for visiting. Goodbye!");
                    return;
                }
                customer = customerMap.get(phoneNumber);

                if (customer != null) {
                    // Welcome returning customer and offer a free spa session
                    isReturningCustomer = true;
                    System.out.println("Welcome back, " + customer.getName() + "!!! As a returning customer you qualify for a complimentary spa.");
                } else {
                    // Prompt to continue as a new customer if not found
                    System.out.println("Phone number not found.");
                    while (true) {
                        System.out.print("Would you like to continue as a new customer (yes/no or type 'quit' to exit)? ");
                        String newCustomerResponse = scanner.nextLine().trim();
                        if (newCustomerResponse.equalsIgnoreCase("quit")) {
                            System.out.println("Thank you for visiting. Goodbye!");
                            return;
                        }
                        if (newCustomerResponse.equalsIgnoreCase("yes")) {
                            System.out.print("Enter your name (or type 'quit' to exit): ");
                            String name = scanner.nextLine().toUpperCase();
                            if (name.equalsIgnoreCase("quit")) {
                                System.out.println("Thank you for visiting. Goodbye!");
                                return;
                            }

                            String email;
                            while (true) {
                                System.out.print("Enter your email (or type 'quit' to exit): ");
                                email = scanner.nextLine();
                                if (email.equalsIgnoreCase("quit")) {
                                    System.out.println("Thank you for visiting. Goodbye!");
                                    return;
                                }
                                if (Customer.isValidEmail(email)) {
                                    break; // Valid email, exit loop
                                } else {
                                    System.out.println("Invalid email format. Please enter a valid email.");
                                }
                            }
                            customer = new Customer(name, phoneNumber, email);
                            customerMap.put(phoneNumber, customer);
                            fileManager.writeCustomerInfo((HashMap<String, Customer>) customerMap);
                            break;
                        } else if (newCustomerResponse.equalsIgnoreCase("no")) {
                            System.out.println("Thank you for visiting. Please contact our support if you need further assistance.");
                            return;
                        } else {
                            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                        }
                    }
                }
            }
            // Process new customers
            if (customer == null && phoneNumber == null) {
                System.out.print("Enter your name (or type 'quit' to exit): ");
                String name = scanner.nextLine().toUpperCase();
                if (name.equalsIgnoreCase("quit")) {
                    System.out.println("Thank you for visiting. Goodbye!");
                    return;
                }

                System.out.print("Enter your phone number (or type 'quit' to exit): ");
                phoneNumber = scanner.nextLine();
                if (phoneNumber.equalsIgnoreCase("quit")) {
                    System.out.println("Thank you for visiting. Goodbye!");
                    return;
                }

                if (customerMap.containsKey(phoneNumber)) {
                    System.out.println("Phone number already exists in the system. Please check your number or contact support.");
                } else {
                    System.out.print("Enter your email (or type 'quit' to exit): ");
                    String email = scanner.nextLine().toUpperCase();
                    if (email.equalsIgnoreCase("quit")) {
                        System.out.println("Thank you for visiting. Goodbye!");
                        return;
                    }

                    customer = new Customer(name, phoneNumber, email);
                    customerMap.put(phoneNumber, customer);
                    fileManager.writeCustomerInfo(new HashMap<>(customerMap));
                }
            }

            // Get booking details
            int nights = 0;
            int roomTypeChoice = 0;
            try {
                System.out.print("How many nights would you like to book? ");
                nights = Integer.parseInt(scanner.nextLine());

                System.out.print("Which type of room would you like to book (1 for Single, 2 for Double, 3 for Suite)? ");
                roomTypeChoice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values for nights and room type.");
                return;
            }
            // Select room based on user choice
            Room selectedRoom = selectRoomByType(roomsMap, roomTypeChoice);

            if (selectedRoom != null) {
                // Create and confirm booking
                Booking booking = new Booking(selectedRoom, customer, nights);
                System.out.println(booking.confirmBooking());
                // Inform returning customers about the complimentary spa
                if (isReturningCustomer) {
                    System.out.println("Check the reception to book your free spa session after you have checked in.");
                }
            } else {
                System.out.println("No available rooms of the selected type.");
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred");
        }
    }

    // got help from chat gpt to select a room from a collection of available rooms based on the user's choice of room type.
    private static Room selectRoomByType(HashMap<String, Room> roomsMap, int roomTypeChoice) {
        Map<String, Room> filteredRoomsMap = roomsMap.entrySet().stream()
                .filter(entry -> {
                    Room room = entry.getValue();
                    switch (roomTypeChoice) {
                        case 1:
                            return room instanceof SingleRoom;
                        case 2:
                            return room instanceof DoubleRoom;
                        case 3:
                            return room instanceof Suite;
                        default:
                            System.out.println("Invalid choice.");
                            return false;
                    }
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (!filteredRoomsMap.isEmpty()) {
            return Booking.assignRoom(new ArrayList<>(filteredRoomsMap.values()));
        } else {
            return null;
        }
    }
}
