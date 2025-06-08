package panels;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class DoctorSchedulePanel extends BasePanel {

    public DoctorSchedulePanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        // Add title
        add(createTitlePanel("Doctor Schedule Management"), BorderLayout.NORTH);

        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(backgroundColor);

        // Create filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(backgroundColor);

        // Department filter
        filterPanel.add(createLabel("Department:"));
        JComboBox<String> departmentCombo = new JComboBox<>(new String[]{
            "All Departments", "General Medicine", "Cardiology", "Orthopedics", "Neurology"
        });
        departmentCombo.setPreferredSize(new Dimension(200, 30));
        filterPanel.add(departmentCombo);

        // Doctor filter
        filterPanel.add(createLabel("Doctor:"));
        JComboBox<String> doctorCombo = new JComboBox<>(new String[]{
            "All Doctors", "Dr. Kena Fayera", "Dr. Sewyishal Netsanet", "Dr. Yisakor Tamirat", "Dr. Leulekal Nahusenay", "Dr. Semere Hailu"
        });
        doctorCombo.setPreferredSize(new Dimension(200, 30));
        filterPanel.add(doctorCombo);

        // Date selection
        filterPanel.add(createLabel("Date:"));
        JFormattedTextField dateField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        dateField.setValue(new Date());
        dateField.setPreferredSize(new Dimension(120, 30));
        filterPanel.add(dateField);

        JButton filterButton = createButton("Apply Filter");
        filterPanel.add(filterButton);

        // Create schedule table
        String[] columnNames = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        Object[][] data = {
            {"09:00 AM", "Available", "Clinic", "Surgery", "Available", "Clinic"},
            {"10:00 AM", "Clinic", "Available", "Surgery", "Clinic", "Available"},
            {"11:00 AM", "Clinic", "Clinic", "Available", "Clinic", "Clinic"},
            {"12:00 PM", "Break", "Break", "Break", "Break", "Break"},
            {"02:00 PM", "Surgery", "Clinic", "Clinic", "Surgery", "Clinic"},
            {"03:00 PM", "Surgery", "Available", "Clinic", "Surgery", "Available"},
            {"04:00 PM", "Clinic", "Clinic", "Available", "Clinic", "Clinic"}
        };

        JTable scheduleTable = new JTable(data, columnNames);
        scheduleTable.setFont(normalFont);
        scheduleTable.setRowHeight(30);
        scheduleTable.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 14));
        scheduleTable.getTableHeader().setBackground(primaryColor);
        scheduleTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane tableScroll = new JScrollPane(scheduleTable);

        // Create edit panel
        JPanel editPanel = new JPanel(new GridBagLayout());
        editPanel.setBackground(backgroundColor);
        editPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Edit Schedule",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Poppins", Font.BOLD, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Time slot selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        editPanel.add(createLabel("Time Slot:"), gbc);

        gbc.gridx = 1;
        JComboBox<String> timeSlotCombo = new JComboBox<>(new String[]{
            "09:00 AM", "10:00 AM", "11:00 AM", "02:00 PM", "03:00 PM", "04:00 PM"
        });
        timeSlotCombo.setPreferredSize(new Dimension(150, 30));
        editPanel.add(timeSlotCombo, gbc);

        // Day selection
        gbc.gridx = 2;
        editPanel.add(createLabel("Day:"), gbc);

        gbc.gridx = 3;
        JComboBox<String> dayCombo = new JComboBox<>(new String[]{
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
        });
        dayCombo.setPreferredSize(new Dimension(150, 30));
        editPanel.add(dayCombo, gbc);

        // Status selection
        gbc.gridx = 4;
        editPanel.add(createLabel("Status:"), gbc);

        gbc.gridx = 5;
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{
            "Available", "Clinic", "Surgery", "Break", "Off"
        });
        statusCombo.setPreferredSize(new Dimension(150, 30));
        editPanel.add(statusCombo, gbc);

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(backgroundColor);

        JButton updateButton = createButton("Update Schedule");
        JButton resetButton = createButton("Reset");
        JButton printButton = createButton("Print Schedule");

        buttonPanel.add(updateButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(printButton);

        // Add components to content panel
        contentPanel.add(filterPanel, BorderLayout.NORTH);
        contentPanel.add(tableScroll, BorderLayout.CENTER);
        contentPanel.add(editPanel, BorderLayout.SOUTH);

        // Add panels to main frame
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        filterButton.addActionListener(e -> applyFilter());
        updateButton.addActionListener(e -> updateSchedule());
        resetButton.addActionListener(e -> resetSchedule());
        printButton.addActionListener(e -> printSchedule());
    }

    private void applyFilter() {
        JOptionPane.showMessageDialog(this,
                "Applying schedule filters...",
                "Filter",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateSchedule() {
        JOptionPane.showMessageDialog(this,
                "Updating schedule...",
                "Update",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetSchedule() {
        JOptionPane.showMessageDialog(this,
                "Resetting schedule...",
                "Reset",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void printSchedule() {
        JOptionPane.showMessageDialog(this,
                "Printing schedule...",
                "Print",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
