package panels;

import java.awt.*;
import javax.swing.*;

public class PrescriptionsPanel extends BasePanel {

    public PrescriptionsPanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        // Add title
        add(createTitlePanel("My Prescriptions"), BorderLayout.NORTH);

        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(backgroundColor);

        // Create filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(backgroundColor);

        // Status filter
        filterPanel.add(createLabel("Status:"));
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{
            "All", "Active", "Completed", "Expired"
        });
        statusCombo.setPreferredSize(new Dimension(150, 30));
        filterPanel.add(statusCombo);

        // Date range
        filterPanel.add(createLabel("From:"));
        JTextField fromDate = createTextField();
        fromDate.setPreferredSize(new Dimension(120, 30));
        filterPanel.add(fromDate);

        filterPanel.add(createLabel("To:"));
        JTextField toDate = createTextField();
        toDate.setPreferredSize(new Dimension(120, 30));
        filterPanel.add(toDate);

        JButton filterButton = createButton("Apply Filter");
        filterPanel.add(filterButton);

        // Create table for prescriptions
        String[] columnNames = {"Date", "Medication", "Dosage", "Duration", "Doctor", "Status"};
        Object[][] data = {
            {"2024-05-15", "Amoxicillin", "500mg 3x daily", "7 days", "Dr. Kena Fayera", "Active"},
            {"2024-05-10", "Ibuprofen", "400mg as needed", "5 days", "Dr. Leulekal Nahusenay", "Active"},
            {"2024-05-01", "Vitamin D", "1000 IU daily", "30 days", "Dr. Sewyishal Netsanet", "Active"},
            {"2024-04-25", "Antibiotic", "250mg 2x daily", "10 days", "Dr. Yisakor Tamirat", "Completed"},
            {"2024-04-20", "Pain Relief", "500mg as needed", "3 days", "Dr. Semere Hailu", "Completed"}
        };

        JTable prescriptionsTable = new JTable(data, columnNames);
        prescriptionsTable.setFont(normalFont);
        prescriptionsTable.setRowHeight(30);
        prescriptionsTable.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 14));
        prescriptionsTable.getTableHeader().setBackground(primaryColor);
        prescriptionsTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(prescriptionsTable);

        // Create details panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBackground(backgroundColor);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Prescription Details",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Poppins", Font.BOLD, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Medication details
        gbc.gridx = 0;
        gbc.gridy = 0;
        detailsPanel.add(createLabel("Medication:"), gbc);

        gbc.gridx = 1;
        JTextField medicationField = createTextField();
        medicationField.setEditable(false);
        medicationField.setPreferredSize(new Dimension(200, 30));
        detailsPanel.add(medicationField, gbc);

        gbc.gridx = 2;
        detailsPanel.add(createLabel("Dosage:"), gbc);

        gbc.gridx = 3;
        JTextField dosageField = createTextField();
        dosageField.setEditable(false);
        dosageField.setPreferredSize(new Dimension(200, 30));
        detailsPanel.add(dosageField, gbc);

        // Instructions
        gbc.gridx = 0;
        gbc.gridy = 1;
        detailsPanel.add(createLabel("Instructions:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        JTextArea instructionsArea = new JTextArea(3, 40);
        instructionsArea.setEditable(false);
        instructionsArea.setFont(normalFont);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        JScrollPane instructionsScroll = new JScrollPane(instructionsArea);
        detailsPanel.add(instructionsScroll, gbc);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(backgroundColor);

        JButton viewButton = createButton("View Full Details");
        JButton printButton = createButton("Print");
        JButton refillButton = createButton("Request Refill");

        buttonPanel.add(viewButton);
        buttonPanel.add(printButton);
        buttonPanel.add(refillButton);

        // Add components to content panel
        contentPanel.add(filterPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(detailsPanel, BorderLayout.SOUTH);

        // Add button panel to the bottom
        add(buttonPanel, BorderLayout.SOUTH);

        // Add content panel to center
        add(contentPanel, BorderLayout.CENTER);

        // Add action listeners
        filterButton.addActionListener(e -> applyFilter());
        viewButton.addActionListener(e -> viewFullDetails());
        printButton.addActionListener(e -> printPrescription());
        refillButton.addActionListener(e -> requestRefill());

        // Add table selection listener
        prescriptionsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDetails(prescriptionsTable.getSelectedRow());
            }
        });
    }

    private void applyFilter() {
        JOptionPane.showMessageDialog(this,
                "Applying filters...",
                "Filter",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewFullDetails() {
        JOptionPane.showMessageDialog(this,
                "Opening full prescription details...",
                "View Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void printPrescription() {
        JOptionPane.showMessageDialog(this,
                "Preparing prescription for printing...",
                "Print",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void requestRefill() {
        JOptionPane.showMessageDialog(this,
                "Sending refill request...",
                "Refill Request",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateDetails(int selectedRow) {
        if (selectedRow >= 0) {
            // Implementation for updating details panel
        }
    }
}
