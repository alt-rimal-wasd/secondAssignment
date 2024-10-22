package hotelbooking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class HotelBookingGUI extends JFrame {
    private JTextField nameField, phoneNumberField, emailField, nightsField;
    private JComboBox<String> roomTypeBox;
    private JButton bookButton;
    private JLabel statusLabel;
    
    private Map<String, Customer> customerMap = fileManager.readCustomerInfo();
    private HashMap<String, Room> roomsMap = fileManager.readRoomsFromFile();

    public HotelBookingGUI() {
        setTitle("Hotel Booking System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the frame

        // Create panel for input fields
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create fields for input
        JLabel phoneLabel = new JLabel("Phone Number:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(phoneLabel, gbc);

        phoneNumberField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(phoneNumberField, gbc);

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        JLabel nightsLabel = new JLabel("Nights:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(nightsLabel, gbc);

        nightsField = new JTextField(5);
        gbc.gridx = 1;
        panel.add(nightsField, gbc);

        JLabel roomTypeLabel = new JLabel("Room Type:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(roomTypeLabel, gbc);

        roomTypeBox = new JComboBox<>(new String[] { "Single", "Double", "Suite" });
        gbc.gridx = 1;
        panel.add(roomTypeBox, gbc);

        bookButton = new JButton("Book");
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(bookButton, gbc);

        statusLabel = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(statusLabel, gbc);

        add(panel);

        // Initial prompt to check if the customer is new or returning
        int response = JOptionPane.showConfirmDialog(this, "Are you a returning customer?", "Customer Type", JOptionPane.YES_NO_OPTION);

        // Adjust UI based on response
        if (response == JOptionPane.YES_OPTION) {
            // Returning customer: hide name and email fields
            nameField.setVisible(false);
            emailField.setVisible(false);
            nameLabel.setVisible(false);
            emailLabel.setVisible(false);
        } else {
            // New customer: hide phone number field initially
            phoneNumberField.setVisible(false);
            phoneLabel.setVisible(false);
        }

        // Handle the booking process on button click
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBooking(response == JOptionPane.YES_OPTION);
            }
        });
    }

    private void handleBooking(boolean isReturningCustomer) {
        String phoneNumber = phoneNumberField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        int nights;

        try {
            nights = Integer.parseInt(nightsField.getText().trim());
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid number of nights.");
            return;
        }

        int roomTypeChoice = roomTypeBox.getSelectedIndex() + 1; // 1 for Single, 2 for Double, 3 for Suite
        Room selectedRoom = selectRoomByType(roomsMap, roomTypeChoice);

        if (selectedRoom == null) {
            statusLabel.setText("No available rooms of the selected type.");
            return;
        }

        Customer customer = null;

        // Check if the customer is returning
        if (isReturningCustomer) {
            customer = customerMap.get(phoneNumber);

            if (customer != null) {
                // Welcome back the customer
                statusLabel.setText("Welcome back, " + customer.getName() + "! Complimentary spa session awaits.");
            } else {
                // Phone number not found for returning customer
                statusLabel.setText("Phone number not found. Please enter as a new customer.");
                return;
            }
        } else {
            // Handle new customer
            if (name.isEmpty() || email.isEmpty()) {
                statusLabel.setText("Please fill in all fields.");
                return;
            }
            if (!Customer.isValidEmail(email)) {
                statusLabel.setText("Invalid email format.");
                return;
            }

            // Create new customer
            customer = new Customer(name, phoneNumber, email);
            customerMap.put(phoneNumber, customer);
            fileManager.writeCustomerInfo((HashMap<String, Customer>) customerMap);
        }

        // Create booking and confirm
        Booking booking = new Booking(selectedRoom, customer, nights);
        statusLabel.setText(booking.confirmBooking());

        // Reset fields after booking
        resetFields();
    }

    private void resetFields() {
        nameField.setText("");
        phoneNumberField.setText("");
        emailField.setText("");
        nightsField.setText("");
        roomTypeBox.setSelectedIndex(0);
    }

    // Reuse the existing room selection logic
    private Room selectRoomByType(HashMap<String, Room> roomsMap, int roomTypeChoice) {
        return roomsMap.values().stream()
                .filter(room -> {
                    switch (roomTypeChoice) {
                        case 1: return room instanceof SingleRoom;
                        case 2: return room instanceof DoubleRoom;
                        case 3: return room instanceof Suite;
                        default: return false;
                    }
                })
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HotelBookingGUI gui = new HotelBookingGUI();
            gui.setVisible(true);
        });
    }
}
