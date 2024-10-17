package hotelbooking;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class HotelBookingGUI {
    private fileManager fileManager;
    private Map<String, Customer> customerMap;
    private HashMap<String, Room> roomsMap;
    private BufferedImage backgroundImage; // Declare the backgroundImage variable
    private JFrame frame; // Main frame
    private JButton startBookingButton; // Start Booking button

    public HotelBookingGUI() {
        fileManager = new fileManager(); // Initialize file manager to read/write customer info
        customerMap = fileManager.readCustomerInfo();
        roomsMap = fileManager.readRoomsFromFile();
        loadBackgroundImage();

        // Set up the main frame
        frame = new JFrame("Hotel Booking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new BackgroundPanel()); // Use the custom BackgroundPanel
        frame.setLayout(new BorderLayout()); // Use BorderLayout for the frame
        frame.setSize(800, 600); // Set the size of your frame
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create and configure the Start Booking button
        startBookingButton = new JButton("Start Booking");
        startBookingButton.addActionListener(e -> startBookingProcess());
        startBookingButton.setPreferredSize(new Dimension(200, 50)); // Set preferred size for the button
        startBookingButton.setFocusable(false); // Prevent button focus ring

        // Create a panel to center the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make the panel transparent
        buttonPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering
        buttonPanel.add(startBookingButton); // Add button to the panel

        // Add button panel to the center of the frame
        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setVisible(true); // Make the frame visible
    }

    private void loadBackgroundImage() {
        File imageFile = new File("./resources/hotel_image.jpg");
        if (!imageFile.exists()) {
            System.err.println("Background image file not found: " + imageFile.getAbsolutePath());
            return; // Exit the method if the file doesn't exist
        }

        try {
            // Load the background image from a file
            backgroundImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }
    }

    // Custom JPanel to display the background image
    private class BackgroundPanel extends JPanel {
        public BackgroundPanel() {
            setPreferredSize(new Dimension(800, 600)); // Set preferred size
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null); // Draw background image
            }
        }
    }

    private void startBookingProcess() {
    // Ask if the customer has stayed before
    int response = JOptionPane.showConfirmDialog(frame,
            "Have you stayed with us before?", "Hotel Booking",
            JOptionPane.YES_NO_OPTION);

    // Check if the dialog was closed (user clicked the close button)
    if (response == JOptionPane.CLOSED_OPTION) {
        // Do nothing if the dialog was closed
        return;
    }

    if (response == JOptionPane.YES_OPTION) {
        handleReturningCustomer();
    } else {
        handleNewCustomer();
    }
}

    private void handleReturningCustomer() {
        String phoneNumber = JOptionPane.showInputDialog(frame, "Please enter your phone number:");

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Phone number cannot be empty.");
            return;
        }

        Customer customer = customerMap.get(phoneNumber);

        if (customer != null) {
            // Welcome returning customer
            JOptionPane.showMessageDialog(frame, "Welcome back, " + customer.getName() + "!");
            bookRoom(customer);
        } else {
            // Prompt to continue as a new customer
            int response = JOptionPane.showConfirmDialog(frame,
                    "Phone number not found. Would you like to continue as a new customer?", "Customer Not Found",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                handleNewCustomer();
            } else {
                JOptionPane.showMessageDialog(frame, "Thank you for visiting.");
            }
        }
    }

    private void handleNewCustomer() {
        String name = JOptionPane.showInputDialog(frame, "Enter your name:");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name cannot be empty.");
            return;
        }

        String phoneNumber = JOptionPane.showInputDialog(frame, "Enter your phone number:");
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Phone number cannot be empty.");
            return;
        }

        if (customerMap.containsKey(phoneNumber)) {
            JOptionPane.showMessageDialog(frame, "Phone number already exists in the system.");
            return;
        }

        String email = JOptionPane.showInputDialog(frame, "Enter your email:");
        if (email == null || email.trim().isEmpty() || !Customer.isValidEmail(email)) {
            JOptionPane.showMessageDialog(frame, "Invalid email format.");
            return;
        }

        Customer customer = new Customer(name, phoneNumber, email);
        customerMap.put(phoneNumber, customer);
        fileManager.writeCustomerInfo(new HashMap<>(customerMap));
        JOptionPane.showMessageDialog(frame, "New customer registered.");
        bookRoom(customer);
    }

    private void bookRoom(Customer customer) {
        String[] options = {"Single", "Double", "Suite"};
        int roomTypeChoice = JOptionPane.showOptionDialog(frame,
                "Which type of room would you like to book?",
                "Room Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (roomTypeChoice == JOptionPane.CLOSED_OPTION) {
            JOptionPane.showMessageDialog(frame, "Booking cancelled.");
            return;
        }

        Room selectedRoom = selectRoomByType(roomTypeChoice);
        if (selectedRoom != null) {
            String nightsStr = JOptionPane.showInputDialog(frame, "How many nights would you like to book?");
            int nights;

            try {
                nights = Integer.parseInt(nightsStr);
                Booking booking = new Booking(selectedRoom, customer, nights);
                JOptionPane.showMessageDialog(frame, booking.confirmBooking());
                JOptionPane.showMessageDialog(frame, "Thank you for your booking!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid number of nights.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No available rooms of the selected type.");
        }
    }

    private Room selectRoomByType(int roomTypeChoice) {
        // Logic to select a room based on the user's choice
        for (Room room : roomsMap.values()) {
            if ((roomTypeChoice == 0 && room instanceof SingleRoom) ||
                (roomTypeChoice == 1 && room instanceof DoubleRoom) ||
                (roomTypeChoice == 2 && room instanceof Suite)) {
                return room; // Return the first available room of the selected type
            }
        }
        return null; // No available rooms of that type
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelBookingGUI::new);
    }
}
