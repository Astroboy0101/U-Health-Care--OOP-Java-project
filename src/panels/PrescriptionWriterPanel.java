package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class PrescriptionWriterPanel extends BasePanel {

    private JTextField nameField;
    private JTextField ageField;
    private JDateChooser dateChooser;
    private JTable medicationsTable;
    private JTextArea instructionsArea;
    private DefaultTableModel tableModel;

    public PrescriptionWriterPanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        // Add title
        add(createTitlePanel("Write Prescription"), BorderLayout.NORTH);

        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(backgroundColor);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create patient selection panel
        JPanel patientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        patientPanel.setBackground(backgroundColor);

        patientPanel.add(createLabel("Patient:"));
        JTextField patientSearch = createTextField();
        patientSearch.setPreferredSize(new Dimension(300, 30));
        patientSearch.setToolTipText("Search patient by name or ID");

        JButton searchButton = createButton("Search");
        searchButton.setBackground(new Color(0, 102, 68));
        searchButton.setForeground(Color.WHITE);

        patientPanel.add(patientSearch);
        patientPanel.add(searchButton);

        // Create prescription form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Patient info section
        JPanel patientInfoPanel = new JPanel(new GridBagLayout());
        patientInfoPanel.setBackground(backgroundColor);
        patientInfoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 68)),
                "Patient Information",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Poppins", Font.BOLD, 16),
                new Color(0, 102, 68)
        ));

        gbc.gridx = 0;
        gbc.gridy = 0;
        patientInfoPanel.add(createLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = createTextField();
        nameField.setPreferredSize(new Dimension(300, 30));
        patientInfoPanel.add(nameField, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        patientInfoPanel.add(createLabel("Age:"), gbc);

        gbc.gridx = 1;
        ageField = createTextField();
        ageField.setPreferredSize(new Dimension(100, 30));
        patientInfoPanel.add(ageField, gbc);

        // Prescription details section
        JPanel prescriptionPanel = new JPanel(new GridBagLayout());
        prescriptionPanel.setBackground(backgroundColor);
        prescriptionPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 68)),
                "Prescription Details",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Poppins", Font.BOLD, 16),
                new Color(0, 102, 68)
        ));

        // Date field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        prescriptionPanel.add(createLabel("Date:"), gbc);

        gbc.gridx = 1;
        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        dateChooser.setPreferredSize(new Dimension(200, 35));
        dateChooser.setFont(normalFont);
        dateChooser.setDateFormatString("dd-MM-yyyy");
        prescriptionPanel.add(dateChooser, gbc);

        // Medications table
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        String[] columnNames = {"Medication", "Dosage", "Frequency", "Duration", "Notes"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        tableModel.addRow(new Object[]{"", "", "", "", ""});

        medicationsTable = new JTable(tableModel);
        medicationsTable.setFont(normalFont);
        medicationsTable.setRowHeight(35);
        medicationsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        medicationsTable.getTableHeader().setBackground(new Color(0, 102, 68));
        medicationsTable.getTableHeader().setForeground(Color.WHITE);
        medicationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Set column widths
        int[] columnWidths = {200, 150, 150, 150, 200};
        for (int i = 0; i < columnWidths.length; i++) {
            medicationsTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        JScrollPane tableScroll = new JScrollPane(medicationsTable);
        tableScroll.setPreferredSize(new Dimension(850, 200));
        prescriptionPanel.add(tableScroll, gbc);

        // Table buttons
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 5, 20, 5);
        JPanel tableButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        tableButtonPanel.setBackground(backgroundColor);

        JButton addRowButton = createButton("Add Medication");
        JButton removeRowButton = createButton("Remove Selected");
        addRowButton.setPreferredSize(new Dimension(150, 35));
        removeRowButton.setPreferredSize(new Dimension(150, 35));
        addRowButton.setBackground(new Color(0, 102, 68));
        removeRowButton.setBackground(new Color(0, 102, 68));
        addRowButton.setForeground(Color.WHITE);
        removeRowButton.setForeground(Color.WHITE);

        tableButtonPanel.add(addRowButton);
        tableButtonPanel.add(removeRowButton);
        prescriptionPanel.add(tableButtonPanel, gbc);

        // Special Instructions
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 5, 5, 5);
        JPanel instructionsPanel = new JPanel(new BorderLayout(0, 5));
        instructionsPanel.setBackground(backgroundColor);
        instructionsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 68)),
                "Special Instructions",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(0, 102, 68)
        ));

        instructionsArea = new JTextArea(5, 40);
        instructionsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        instructionsArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JScrollPane instructionsScroll = new JScrollPane(instructionsArea);
        instructionsScroll.setPreferredSize(new Dimension(850, 150));
        instructionsPanel.add(instructionsScroll, BorderLayout.CENTER);
        prescriptionPanel.add(instructionsPanel, gbc);

        // Main form layout
        JPanel mainFormPanel = new JPanel(new GridBagLayout());
        mainFormPanel.setBackground(backgroundColor);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainFormPanel.add(patientInfoPanel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(20, 5, 5, 5);
        mainFormPanel.add(prescriptionPanel, gbc);

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(backgroundColor);

        JButton saveButton = createButton("Save Prescription");
        JButton printButton = createButton("Save & Print");
        JButton clearButton = createButton("Clear Form");

        saveButton.setBackground(new Color(0, 102, 68));
        printButton.setBackground(new Color(0, 102, 68));
        clearButton.setBackground(Color.LIGHT_GRAY);

        saveButton.setForeground(Color.WHITE);
        printButton.setForeground(Color.WHITE);

        buttonPanel.add(saveButton);
        buttonPanel.add(printButton);
        buttonPanel.add(clearButton);

        // Add components to content panel
        contentPanel.add(patientPanel, BorderLayout.NORTH);
        contentPanel.add(mainFormPanel, BorderLayout.CENTER);

        // Add panels to main frame
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        searchButton.addActionListener(e -> searchPatient(patientSearch.getText()));
        addRowButton.addActionListener(e -> addMedicationRow());
        removeRowButton.addActionListener(e -> removeMedicationRow());
        saveButton.addActionListener(e -> savePrescription());
        printButton.addActionListener(e -> saveAndPrint());
        clearButton.addActionListener(e -> clearForm());
    }

    private void searchPatient(String query) {
        // TODO: Implement patient search
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a patient name or ID",
                    "Search",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // For demonstration, just show a message
        JOptionPane.showMessageDialog(this,
                "Searching for patient: " + query,
                "Search",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void addMedicationRow() {
        tableModel.addRow(new Object[]{"", "", "", "", ""});
    }

    private void removeMedicationRow() {
        int selectedRow = medicationsTable.getSelectedRow();
        if (selectedRow != -1) {
            if (tableModel.getRowCount() > 1) {
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Cannot remove the last row. Clear its contents instead.",
                        "Remove Medication",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a medication to remove",
                    "Remove Medication",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void savePrescription() {
        if (validateForm()) {
            JOptionPane.showMessageDialog(this,
                    "Prescription saved successfully",
                    "Save",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        }
    }

    private void saveAndPrint() {
        if (validateForm()) {
            JOptionPane.showMessageDialog(this,
                    "Prescription saved and sent to printer",
                    "Save & Print",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        }
    }

    private void clearForm() {
        nameField.setText("");
        ageField.setText("");
        dateChooser.setDate(new Date());
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
        tableModel.addRow(new Object[]{"", "", "", "", ""});
        instructionsArea.setText("");
    }

    private boolean validateForm() {
        if (nameField.getText().trim().isEmpty()) {
            showError("Please enter patient name");
            return false;
        }
        if (ageField.getText().trim().isEmpty()) {
            showError("Please enter patient age");
            return false;
        }
        try {
            int age = Integer.parseInt(ageField.getText().trim());
            if (age <= 0 || age > 150) {
                showError("Please enter a valid age");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Please enter a valid age number");
            return false;
        }
        if (dateChooser.getDate() == null) {
            showError("Please select a date");
            return false;
        }
        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
