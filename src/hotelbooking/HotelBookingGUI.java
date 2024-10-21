package hotelbooking;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelBookingGUI extends JFrame {

    private JButton bookBtn;
    private JTextField nameField, phoneField, emailField;
    private JComboBox<String> roomTypeDropdown;
    private JLabel titleLabel;

    public HotelBookingGUI() {
        // Create form components
        nameField = new JTextField(20);
        phoneField = new JTextField(20);
        emailField = new JTextField(20);
        String[] roomTypes = {"Single", "Double", "Suite"};
        roomTypeDropdown = new JComboBox<>(roomTypes);

        // Title label
        titleLabel = new JLabel("Hotel Booking");

        // Booking button
        bookBtn = new JButton("Book Room");
        bookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBooking();
            }
        });

        // Center Panel (Background + Title Label)
        BGPanel centerPanel = new BGPanel();
        centerPanel.add(this.titleLabel);
        this.add(centerPanel, BorderLayout.CENTER);

        // South Panel (Form Fields + Book Button)
        JPanel southPanel = new JPanel();
        southPanel.add(new JLabel("Name:"));
        southPanel.add(this.nameField);
        southPanel.add(new JLabel("Phone:"));
        southPanel.add(this.phoneField);
        southPanel.add(new JLabel("Email:"));
        southPanel.add(this.emailField);
        southPanel.add(new JLabel("Room Type:"));
        southPanel.add(this.roomTypeDropdown);
        southPanel.add(bookBtn);
        this.add(southPanel, BorderLayout.SOUTH);

        // Frame settings
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    // Booking logic
    private void handleBooking() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String roomType = (String) roomTypeDropdown.getSelectedItem();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Booking Confirmed! Room Type: " + roomType);
        }
    }

    public static void main(String[] args) {
        HotelBookingGUI gui = new HotelBookingGUI();
        gui.setVisible(true);
    }
}

// Background panel class (BGPanel)
class BGPanel extends JPanel {
    private Image image;

    public BGPanel() {
        this.image = new ImageIcon("./resources/background.jpg").getImage(); // You can replace with your own background image
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, getWidth(), getHeight(), this);
    }
}
