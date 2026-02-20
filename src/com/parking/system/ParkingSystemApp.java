package com.parking.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ParkingSystemApp extends JFrame {
    private ParkingLot parkingLot;
    private JTextArea displayArea;
    
    public ParkingSystemApp() {
        // Initialize parking lot with 10 slots
        parkingLot = new ParkingLot(10);
        
        // Set up the main window
        setTitle("Smart Parking System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(41, 128, 185));
        JLabel titleLabel = new JLabel("SMART PARKING SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Display Area
        displayArea = new JTextArea();
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton parkButton = createButton("Park Vehicle", new Color(46, 204, 113));
        JButton removeButton = createButton("Remove Vehicle", new Color(231, 76, 60));
        JButton viewAllButton = createButton("View All Slots", new Color(52, 152, 219));
        JButton viewAvailableButton = createButton("View Available", new Color(155, 89, 182));
        JButton calculateFeeButton = createButton("Calculate Fee", new Color(241, 196, 15));
        JButton exitButton = createButton("Exit", new Color(149, 165, 166));
        
        buttonPanel.add(parkButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(viewAllButton);
        buttonPanel.add(viewAvailableButton);
        buttonPanel.add(calculateFeeButton);
        buttonPanel.add(exitButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Button Actions
        parkButton.addActionListener(e -> parkVehicle());
        removeButton.addActionListener(e -> removeVehicle());
        viewAllButton.addActionListener(e -> viewAllSlots());
        viewAvailableButton.addActionListener(e -> viewAvailableSlots());
        calculateFeeButton.addActionListener(e -> calculateFee());
        exitButton.addActionListener(e -> System.exit(0));
        
        setLocationRelativeTo(null);
        setVisible(true);
        
        // Welcome message
        displayArea.setText("Welcome to Smart Parking System!\n\n" +
                          "Total Parking Slots: 10\n" +
                          "Select an option from below.\n");
    }
    
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return button;
    }
    
    private void parkVehicle() {
        JTextField vehicleNumField = new JTextField();
        JTextField ownerNameField = new JTextField();
        String[] vehicleTypes = {"Car", "Bike", "Bus"};
        JComboBox<String> typeCombo = new JComboBox<>(vehicleTypes);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Vehicle Number:"));
        panel.add(vehicleNumField);
        panel.add(new JLabel("Owner Name:"));
        panel.add(ownerNameField);
        panel.add(new JLabel("Vehicle Type:"));
        panel.add(typeCombo);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
                     "Park Vehicle", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            String vehicleNum = vehicleNumField.getText().trim();
            String ownerName = ownerNameField.getText().trim();
            String vehicleType = (String) typeCombo.getSelectedItem();
            
            if (vehicleNum.isEmpty() || ownerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }
            
            String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
            Vehicle vehicle = new Vehicle(vehicleNum, vehicleType, ownerName, currentTime);
            
            // Try database insertion, but continue even if it fails
            try (Connection conn = DatabaseManager.getConnection()) {
                String sql = "INSERT INTO vehicles (vehicleNumber, vehicleType, ownerName, entryTime) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, vehicleNum);
                stmt.setString(2, vehicleType);
                stmt.setString(3, ownerName);
                stmt.setString(4, currentTime);
                stmt.executeUpdate();
            } catch (SQLException e) {
                // Database might not be available, continue with in-memory parking
                System.err.println("Database error: " + e.getMessage());
            }

            String message = parkingLot.parkVehicle(vehicle);
            
            displayArea.setText("===== PARK VEHICLE =====\n\n" + message + "\n\n" +
                              "Vehicle Details:\n" + vehicle.toString());
            JOptionPane.showMessageDialog(this, message);
        }
    }
    
    private void removeVehicle() {
        String vehicleNum = JOptionPane.showInputDialog(this, 
                           "Enter Vehicle Number to Remove:");
        
        if (vehicleNum != null && !vehicleNum.trim().isEmpty()) {
            // Try database removal, but continue even if it fails
            try (Connection conn = DatabaseManager.getConnection()) {
                String sql = "DELETE FROM vehicles WHERE vehicleNumber = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, vehicleNum.trim());
                stmt.executeUpdate();
            } catch (SQLException e) {
                // Database might not be available, continue with in-memory removal
                System.err.println("Database error: " + e.getMessage());
            }

            String message = parkingLot.removeVehicle(vehicleNum.trim());
            displayArea.setText("===== REMOVE VEHICLE =====\n\n" + message);
            JOptionPane.showMessageDialog(this, message);
        }
    }
    
    private void viewAllSlots() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== ALL PARKING SLOTS =====\n\n");
        
        // Display all slots from in-memory data
        for (ParkingSlot slot : parkingLot.getSlots()) {
            sb.append(slot.getStatus()).append("\n");
            sb.append("---------------------------\n");
        }
        
        displayArea.setText(sb.toString());
        
        // Also try to get data from database
        try (Connection conn = DatabaseManager.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM vehicles");
            if (rs.next()) {
                sb.append("\n===== DATABASE RECORDS =====\n");
                do {
                    sb.append("Vehicle: ").append(rs.getString("vehicleNumber"))
                      .append(", Type: ").append(rs.getString("vehicleType"))
                      .append(", Owner: ").append(rs.getString("ownerName"))
                      .append(", Entry: ").append(rs.getString("entryTime"))
                      .append("\n");
                } while (rs.next());
                displayArea.setText(sb.toString());
            }
        } catch (SQLException e) {
            // Database might not be available, showing in-memory data only
            System.err.println("Database error: " + e.getMessage());
        }
    }
    
    private void viewAvailableSlots() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== AVAILABLE SLOTS =====\n\n");
        
        int count = 0;
        for (ParkingSlot slot : parkingLot.getSlots()) {
            if (!slot.isOccupied()) {
                sb.append("Slot ").append(slot.getSlotNumber()).append(" - AVAILABLE\n");
                count++;
            }
        }
        
        sb.append("\nTotal Available: ").append(count).append("/10");
        displayArea.setText(sb.toString());
    }
    
    private void calculateFee() {
        String[] vehicleTypes = {"Car", "Bike", "Bus"};
        JComboBox<String> typeCombo = new JComboBox<>(vehicleTypes);
        JTextField hoursField = new JTextField();
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(new JLabel("Vehicle Type:"));
        panel.add(typeCombo);
        panel.add(new JLabel("Parking Hours:"));
        panel.add(hoursField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
                     "Calculate Parking Fee", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String vehicleType = (String) typeCombo.getSelectedItem();
                int hours = Integer.parseInt(hoursField.getText().trim());
                double fee = parkingLot.calculateFee(vehicleType, hours);
                
                String feeDetails = "===== PARKING FEE =====\n\n" +
                                  "Vehicle Type: " + vehicleType + "\n" +
                                  "Hours Parked: " + hours + "\n" +
                                  "Total Fee: ₹" + fee;
                
                displayArea.setText(feeDetails);
                JOptionPane.showMessageDialog(this, feeDetails);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid hours!");
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ParkingSystemApp());
    }
}
