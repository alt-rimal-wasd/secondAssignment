package hotelbooking;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class HotelBookingGUI extends JFrame implements ActionListener {
    private JButton startButton;
    private JTextField phoneField;
    private JButton returningCustomerButton;
    private JButton newCustomerButton;
    private JTextField nameField;
    private JTextField emailField;
    private JComboBox<String> roomTypeComboBox;
    private JTextField nightsField;
    private JLabel textLabel;
    private BookingHandler bookingHandler;
    private JPanel formPanel;
    private BGPanel centerPanel;
    private Customer currentCustomer;

    public HotelBookingGUI() {
        this.bookingHandler = new BookingHandler();
        initComponents();
        initPanels();
        initActionListener();
    }

    public void initComponents() {
        this.startButton = new JButton("START");
        this.textLabel = new JLabel("Hotel Booking System");
        this.textLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    public void initPanels() {
        // Create and configure the north panel for the title
        JPanel northPanel = new JPanel();
        northPanel.add(this.textLabel);
        this.add(northPanel, BorderLayout.NORTH);

        // Center panel using BGPanel
        centerPanel = new BGPanel();
        centerPanel.setLayout(new BorderLayout());
        this.add(centerPanel, BorderLayout.CENTER);

        // Add the start button in the south panel
        JPanel southPanel = new JPanel();
        southPanel.add(startButton);
        this.add(southPanel, BorderLayout.SOUTH);

        // Form Panel for customer details, initially hidden
        this.formPanel = new JPanel();
        this.formPanel.setLayout(new BoxLayout(this.formPanel, BoxLayout.Y_AXIS));
        this.formPanel.setOpaque(false); // Make it transparent
        this.formPanel.setPreferredSize(new Dimension(400, 300)); // Add preferred size

        // Ensure uniform alignment and size for form components
        formPanel.setAlignmentX(CENTER_ALIGNMENT);

        JLabel phoneLabel = new JLabel("Phone:");
        this.phoneField = new JTextField(20);
        this.returningCustomerButton = new JButton("Returning Customer");
        this.newCustomerButton = new JButton("New Customer");

        // Align components to center
        phoneLabel.setAlignmentX(CENTER_ALIGNMENT);
        phoneField.setAlignmentX(CENTER_ALIGNMENT);
        returningCustomerButton.setAlignmentX(CENTER_ALIGNMENT);
        newCustomerButton.setAlignmentX(CENTER_ALIGNMENT);

        // Set uniform size for text fields and buttons
        phoneField.setMaximumSize(new Dimension(200, 30));
        returningCustomerButton.setMaximumSize(new Dimension(200, 30));
        newCustomerButton.setMaximumSize(new Dimension(200, 30));

        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(returningCustomerButton);
        formPanel.add(newCustomerButton);

        formPanel.setVisible(false);
        centerPanel.add(formPanel, BorderLayout.CENTER);

        // Event listeners for customer buttons
        returningCustomerButton.addActionListener(e -> handleReturningCustomer());
        newCustomerButton.addActionListener(e -> handleNewCustomer());
    }

    public void initActionListener() {
        this.startButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.startButton) {
            this.formPanel.setVisible(true);
            this.startButton.setEnabled(false);
        }
    }

    private void handleReturningCustomer() {
        String phone = this.phoneField.getText();
        currentCustomer = bookingHandler.getCustomerMap().get(phone);

        if (currentCustomer != null) {
            JOptionPane.showMessageDialog(this, "Welcome back, " + currentCustomer.getName() + "! You qualify for a complimentary spa session.", "Returning Customer", JOptionPane.INFORMATION_MESSAGE);
            showBookingForm();
        } else {
            JOptionPane.showMessageDialog(this, "Phone number not found. Please continue as a new customer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleNewCustomer() {
        String phone = this.phoneField.getText();
        if (bookingHandler.getCustomerMap().containsKey(phone)) {
            JOptionPane.showMessageDialog(this, "Phone number already exists. Please check your number or contact support.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JLabel nameLabel = new JLabel("Name:");
        this.nameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        this.emailField = new JTextField(20);

        // Align components to center
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);
        nameField.setAlignmentX(CENTER_ALIGNMENT);
        emailLabel.setAlignmentX(CENTER_ALIGNMENT);
        emailField.setAlignmentX(CENTER_ALIGNMENT);

        // Set uniform size for text fields
        nameField.setMaximumSize(new Dimension(200, 30));
        emailField.setMaximumSize(new Dimension(200, 30));

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);

        JButton proceedButton = new JButton("Proceed");
        formPanel.add(proceedButton);
        proceedButton.setMaximumSize(new Dimension(200, 30));
        proceedButton.setAlignmentX(CENTER_ALIGNMENT);
        proceedButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();

            if (!Customer.isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format. Please enter a valid email.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            currentCustomer = new Customer(name, phone, email);
            bookingHandler.addCustomer(currentCustomer);
            showBookingForm();
        });

        formPanel.revalidate();
        formPanel.repaint();
    }

    private void showBookingForm() {
        formPanel.removeAll();

        JLabel roomTypeLabel = new JLabel("Room Type:");
        this.roomTypeComboBox = new JComboBox<>(new String[]{"Single", "Double", "Suite"});
        JLabel nightsLabel = new JLabel("Nights:");
        this.nightsField = new JTextField(5);
        JButton bookButton = new JButton("Book Now");

        // Align components to center
        roomTypeLabel.setAlignmentX(CENTER_ALIGNMENT);
        roomTypeComboBox.setAlignmentX(CENTER_ALIGNMENT);
        nightsLabel.setAlignmentX(CENTER_ALIGNMENT);
        nightsField.setAlignmentX(CENTER_ALIGNMENT);
        bookButton.setAlignmentX(CENTER_ALIGNMENT);

        // Set uniform sizes
        roomTypeComboBox.setMaximumSize(new Dimension(200, 30));
        nightsField.setMaximumSize(new Dimension(100, 30));

        formPanel.add(roomTypeLabel);
        formPanel.add(roomTypeComboBox);
        formPanel.add(nightsLabel);
        formPanel.add(nightsField);
        formPanel.add(bookButton);

        bookButton.addActionListener(e -> handleBooking());

        formPanel.revalidate();
        formPanel.repaint();
    }

    private void handleBooking() {
        String roomType = this.roomTypeComboBox.getSelectedItem().toString();
        int nights = Integer.parseInt(this.nightsField.getText());

        Booking booking = bookingHandler.createBooking(currentCustomer.getPhoneNumber(), roomType, nights);

        if (booking != null) {
            JOptionPane.showMessageDialog(this, booking.confirmBooking(), "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);
            if (currentCustomer != null) {
                JOptionPane.showMessageDialog(this, "Check the reception to book your free spa session after you have checked in.", "Complimentary Spa", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No available rooms of the selected type.", "Booking Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        HotelBookingGUI cf = new HotelBookingGUI();
        cf.setVisible(true);
    }
}
