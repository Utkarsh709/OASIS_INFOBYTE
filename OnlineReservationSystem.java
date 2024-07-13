import javax.swing.*;
import java.awt.event.*;

public class OnlineReservationSystem extends JFrame implements ActionListener {
    // Components for Login Form
    JLabel userLabel, passLabel;
    JTextField userField;
    JPasswordField passField;
    JButton loginButton, cancelButton;
    int userId;

    // Components for Reservation Form
    JLabel trainLabel, classLabel, dateLabel, fromLabel, toLabel;
    JTextField trainField, classField, dateField, fromField, toField;
    JButton reserveButton;

    // Components for Cancellation Form
    JLabel pnrLabel;
    JTextField pnrField;
    JButton confirmCancelButton;

    // Constructor
    public OnlineReservationSystem() {
        // Initialize Login Form
        setTitle("Login Form");
        setSize(400, 150);

        userLabel = new JLabel("Username:");
        passLabel = new JLabel("Password:");
        userField = new JTextField();
        passField = new JPasswordField();
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        // Layout for Login Form
        setLayout(new java.awt.GridLayout(3, 2));
        add(userLabel); add(userField);
        add(passLabel); add(passField);
        add(new JLabel()); add(loginButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Handle Login Form submission
    public void actionPerformed(ActionEvent e) {
        String username = userField.getText();
        String password = String.valueOf(passField.getPassword());

        // Replace this part with your database logic
        if (username.equals("testuser") && password.equals("password123")) {
            userId = 1; // Example user ID
            JOptionPane.showMessageDialog(this, "Login Successful");
            displayReservationForm();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password");
        }
    }

    // Display Reservation Form
    private void displayReservationForm() {
        // Clear the current JFrame
        getContentPane().removeAll();
        repaint();

        setTitle("Reservation Form");
        setSize(400, 300);

        trainLabel = new JLabel("Train Number:");
        classLabel = new JLabel("Class Type:");
        dateLabel = new JLabel("Date of Journey:");
        fromLabel = new JLabel("From:");
        toLabel = new JLabel("To:");

        trainField = new JTextField();
        classField = new JTextField();
        dateField = new JTextField();
        fromField = new JTextField();
        toField = new JTextField();

        reserveButton = new JButton("Reserve");
        reserveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeReservation();
            }
        });

        // Layout for Reservation Form
        setLayout(new java.awt.GridLayout(6, 2));
        add(trainLabel); add(trainField);
        add(classLabel); add(classField);
        add(dateLabel); add(dateField);
        add(fromLabel); add(fromField);
        add(toLabel); add(toField);
        add(new JLabel()); add(reserveButton);

        setVisible(true);
    }

    // Make Reservation
    private void makeReservation() {
        String trainNumber = trainField.getText();
        String classType = classField.getText();
        String dateOfJourney = dateField.getText();
        String from = fromField.getText();
        String to = toField.getText();
        String pnr = generatePNR();

        // Replace this part with your database logic
        JOptionPane.showMessageDialog(this, "Reservation Successful. PNR: " + pnr);
        displayCancellationForm();
    }

    // Generate unique PNR
    private String generatePNR() {
        return "PNR" + System.currentTimeMillis();
    }

    // Display Cancellation Form
    private void displayCancellationForm() {
        // Clear the current JFrame
        getContentPane().removeAll();
        repaint();

        setTitle("Cancellation Form");
        setSize(400, 150);

        pnrLabel = new JLabel("PNR Number:");
        pnrField = new JTextField();
        confirmCancelButton = new JButton("Cancel");
        confirmCancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelReservation();
            }
        });

        // Layout for Cancellation Form
        setLayout(new java.awt.GridLayout(2, 2));
        add(pnrLabel); add(pnrField);
        add(new JLabel()); add(confirmCancelButton);

        setVisible(true);
    }

    // Cancel Reservation
    private void cancelReservation() {
        String pnr = pnrField.getText();

        // Replace this part with your database logic
        if (pnr.startsWith("PNR")) {
            JOptionPane.showMessageDialog(this, "Cancellation Successful");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid PNR Number");
        }
    }

    // Main Method
    public static void main(String[] args) {
        new OnlineReservationSystem();
    }
}

