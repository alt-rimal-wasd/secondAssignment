package hotelbooking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public final class HotelBookingGUI extends JFrame implements ActionListener {
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

    private void initComponents() {
        this.startButton = new JButton("START");
        this.textLabel = new JLabel("Hotel Booking System", JLabel.CENTER);
        this.textLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.textLabel.setForeground(Color.WHITE);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private void initPanels() {
        // North Panel
        JPanel northPanel = new JPanel();
        northPanel.setOpaque(false);
        northPanel.add(this.textLabel);
        this.add(northPanel, BorderLayout.NORTH);

        // Center Panel with background image
        centerPanel = new BGPanel();
        centerPanel.setLayout(new BorderLayout());
        this.add(centerPanel, BorderLayout.CENTER);

        // South Panel with START button
        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.add(startButton);
        this.add(southPanel, BorderLayout.SOUTH);

        // Form Panel for customer details, initially hidden
        this.formPanel = new JPanel();
        this.formPanel.setLayout(new BoxLayout(this.formPanel, BoxLayout.Y_AXIS));
        this.formPanel.setOpaque(false);
        this.formPanel.setPreferredSize(new Dimension(400, 300));
        this.formPanel.setAlignmentX(CENTER_ALIGNMENT);
        
        JLabel phoneLabel = new JLabel("Phone:");
        this.phoneField = new JTextField(20);
        this.returningCustomerButton = new JButton("Returning Customer");
        this.newCustomerButton = new JButton("New Customer");

        setupComponent(phoneLabel, new Dimension(200, 30), CENTER_ALIGNMENT);
        setupComponent(phoneField, new Dimension(200, 30), CENTER_ALIGNMENT);
        setupComponent(returningCustomerButton, new Dimension(200, 30), CENTER_ALIGNMENT);
        setupComponent(newCustomerButton, new Dimension(200, 30), CENTER_ALIGNMENT);

        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(returningCustomerButton);
        formPanel.add(newCustomerButton);
        formPanel.setVisible(false);

        centerPanel.add(formPanel, BorderLayout.CENTER);

        returningCustomerButton.addActionListener(e -> handleReturningCustomer());
        newCustomerButton.addActionListener(e -> handleNewCustomer());
    }

    private void setupComponent(JComponent component, Dimension size, float alignment) {
        component.setMaximumSize(size);
        component.setAlignmentX(alignment);
        component.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private void initActionListener() {
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

        setupComponent(nameLabel, new Dimension(200, 30), CENTER_ALIGNMENT);
        setupComponent(nameField, new Dimension(200, 30), CENTER_ALIGNMENT);
        setupComponent(emailLabel, new Dimension(200, 30), CENTER_ALIGNMENT);
        setupComponent(emailField, new Dimension(200, 30), CENTER_ALIGNMENT);

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);

        JButton proceedButton = new JButton("Proceed");
        setupComponent(proceedButton, new Dimension(200, 30), CENTER_ALIGNMENT);

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

        formPanel.add(proceedButton);
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

        setupComponent(roomTypeLabel, new Dimension(200, 30), CENTER_ALIGNMENT);
        setupComponent(roomTypeComboBox, new Dimension(200, 30), CENTER_ALIGNMENT);
        setupComponent(nightsLabel, new Dimension(200, 30), CENTER_ALIGNMENT);
        setupComponent(nightsField, new Dimension(100, 30), CENTER_ALIGNMENT);
        setupComponent(bookButton, new Dimension(200, 30), CENTER_ALIGNMENT);

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
