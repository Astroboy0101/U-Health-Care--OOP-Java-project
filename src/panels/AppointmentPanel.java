package panels;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentPanel extends BasePanel {
    private User currentUser;
    private JComboBox<String> departmentCombo;
    private JComboBox<String> doctorCombo;
    private JFormattedTextField dateField;
    private JComboBox<String> timeSlotCombo;
    private JComboBox<String> typeCombo;
    private JTextArea notesArea;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat displayTimeFormat = new SimpleDateFormat("hh:mm a");

    public AppointmentPanel(User user) {
        super();
        this.currentUser = user;
        initComponents();
    }

    private void initComponents() {
        // Add title
        add(createTitlePanel("Book Appointment"), BorderLayout.NORTH);

        // Create main content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Department selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(createLabel("Department:"), gbc);

        gbc.gridx = 1;
        departmentCombo = new JComboBox<>(loadDepartments());
        departmentCombo.setPreferredSize(new Dimension(200, 30));
        contentPanel.add(departmentCombo, gbc);

        // Doctor selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(createLabel("Doctor:"), gbc);

        gbc.gridx = 1;
        doctorCombo = new JComboBox<>();
        doctorCombo.setPreferredSize(new Dimension(200, 30));
        contentPanel.add(doctorCombo, gbc);

        // Date selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(createLabel("Date:"), gbc);

        gbc.gridx = 1;
        dateField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        dateField.setPreferredSize(new Dimension(200, 30));
        dateField.setValue(new Date());
        contentPanel.add(dateField, gbc);

        // Time slot selection
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPanel.add(createLabel("Time:"), gbc);

        gbc.gridx = 1;
        timeSlotCombo = new JComboBox<>(new String[]{"09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM"});
        timeSlotCombo.setPreferredSize(new Dimension(200, 30));
        contentPanel.add(timeSlotCombo, gbc);

        // Appointment type selection
        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPanel.add(createLabel("Type:"), gbc);

        gbc.gridx = 1;
        typeCombo = new JComboBox<>(new String[]{"Regular Checkup", "Follow-up", "Emergency", "Consultation"});
        typeCombo.setPreferredSize(new Dimension(200, 30));
        contentPanel.add(typeCombo, gbc);

        // Notes field
        gbc.gridx = 0;
        gbc.gridy = 5;
        contentPanel.add(createLabel("Notes:"), gbc);

        gbc.gridx = 1;
        notesArea = new JTextArea(3, 20);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        notesScrollPane.setPreferredSize(new Dimension(200, 60));
        contentPanel.add(notesScrollPane, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(backgroundColor);

        JButton bookButton = createButton("Book Appointment");
        JButton clearButton = createButton("Clear");

        buttonPanel.add(clearButton);
        buttonPanel.add(bookButton);

        // Add components to main panel
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        bookButton.addActionListener(e -> bookAppointment());
        clearButton.addActionListener(e -> clearForm());
        departmentCombo.addActionListener(e -> updateDoctorsList());
        doctorCombo.addActionListener(e -> updateTimeSlots());
        dateField.addPropertyChangeListener("value", e -> updateTimeSlots());
    }

    private String[] loadDepartments() {
        List<String> departments = new ArrayList<>();
        Connection conn = null;
        try {
            conn = models.DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT name FROM departments");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                departments.add(rs.getString("name"));
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to load departments: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                models.DatabaseManager.releaseConnection(conn);
            }
        }
        return departments.toArray(new String[0]);
    }

    private void updateDoctorsList() {
        String selectedDepartment = (String) departmentCombo.getSelectedItem();
        if (selectedDepartment == null) return;

        doctorCombo.removeAllItems();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = models.DatabaseManager.getConnection();
            pstmt = conn.prepareStatement("SELECT id, full_name FROM users WHERE user_type = 'Doctor' AND department = ?");
            pstmt.setString(1, selectedDepartment);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String doctorName = rs.getString("full_name");
                doctorCombo.addItem(doctorName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to load doctors: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) models.DatabaseManager.releaseConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void updateTimeSlots() {
        String selectedDoctor = (String) doctorCombo.getSelectedItem();
        String selectedDate = dateField.getText();
        if (selectedDoctor == null || selectedDate == null) return;

        // Get booked time slots for the selected doctor and date
        List<String> bookedSlots = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = models.DatabaseManager.getConnection();
            pstmt = conn.prepareStatement(
                "SELECT appointment_time FROM appointments " +
                "WHERE doctor_id = (SELECT id FROM users WHERE full_name = ?) " +
                "AND DATE(appointment_time) = ?");
            
            pstmt.setString(1, selectedDoctor);
            pstmt.setString(2, selectedDate);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                bookedSlots.add(displayTimeFormat.format(rs.getTimestamp("appointment_time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to check available time slots: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) models.DatabaseManager.releaseConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Update time slot combo box
        timeSlotCombo.removeAllItems();
        String[] allSlots = {"09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM"};
        for (String slot : allSlots) {
            if (!bookedSlots.contains(slot)) {
                timeSlotCombo.addItem(slot);
            }
        }
    }

    private void bookAppointment() {
        String selectedDoctor = (String) doctorCombo.getSelectedItem();
        String selectedDate = dateField.getText();
        String selectedTime = (String) timeSlotCombo.getSelectedItem();
        String selectedType = (String) typeCombo.getSelectedItem();
        String notes = notesArea.getText().trim();
        
        if (selectedDoctor == null || selectedDate == null || selectedTime == null) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            System.out.println("Booking appointment with parameters:");
            System.out.println("Patient ID: " + currentUser.getId());
            System.out.println("Doctor Name: " + selectedDoctor);
            System.out.println("Date: " + selectedDate);
            System.out.println("Time: " + selectedTime);
            System.out.println("Type: " + selectedType);
            
            conn = models.DatabaseManager.getConnection();
            
            // First, get the doctor's ID
            String doctorQuery = "SELECT id FROM users WHERE full_name = ? AND user_type = 'Doctor'";
            pstmt = conn.prepareStatement(doctorQuery);
            pstmt.setString(1, selectedDoctor);
            ResultSet rs = pstmt.executeQuery();
            
            if (!rs.next()) {
                throw new SQLException("Doctor not found: " + selectedDoctor);
            }
            
            int doctorId = rs.getInt("id");
            System.out.println("Found doctor ID: " + doctorId);
            
            // Now insert the appointment
            String appointmentQuery = """
                INSERT INTO appointments (patient_id, doctor_id, appointment_time, appointment_type, status, notes)
                VALUES (?, ?, ?, ?, 'Scheduled', ?)
            """;
            
            pstmt = conn.prepareStatement(appointmentQuery);
            pstmt.setInt(1, currentUser.getId());
            pstmt.setInt(2, doctorId);
            pstmt.setString(3, selectedDate + " " + timeFormat.format(displayTimeFormat.parse(selectedTime)));
            pstmt.setString(4, selectedType);
            pstmt.setString(5, notes);
            
            int result = pstmt.executeUpdate();
            System.out.println("Appointment inserted successfully. Rows affected: " + result);
            
            JOptionPane.showMessageDialog(this,
                    "Appointment booked successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form
            departmentCombo.setSelectedIndex(0);
            doctorCombo.removeAllItems();
            dateField.setValue(new Date());
            timeSlotCombo.removeAllItems();
            typeCombo.setSelectedIndex(0);
            notesArea.setText("");
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Failed to book appointment: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Invalid time format: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) models.DatabaseManager.releaseConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void clearForm() {
        departmentCombo.setSelectedIndex(0);
        doctorCombo.removeAllItems();
        dateField.setValue(new Date());
        timeSlotCombo.removeAllItems();
        typeCombo.setSelectedIndex(0);
        notesArea.setText("");
    }
}
