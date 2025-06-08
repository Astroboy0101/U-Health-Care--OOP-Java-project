package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class PatientSearchPanel extends BasePanel {

    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private final Object[][] initialData = {
        {"P001", "Beka Taka", "25", "2024-05-15", "Active"},
        {"P002", "Sena Ulfesa", "30", "2024-05-14", "Active"},
        {"P003", "Roba Tedros", "45", "2024-05-10", "Inactive"},
        {"P004", "Ola Tesena", "28", "2024-05-13", "Active"},
        {"P005", "Taye Dandana", "35", "2024-05-12", "Active"}
    };

    public PatientSearchPanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        // Add title
        add(createTitlePanel("Patient Search"), BorderLayout.NORTH);

        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(backgroundColor);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(backgroundColor);

        JTextField searchField = createTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setToolTipText("Enter patient name or ID");

        JButton searchButton = createButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 30));

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Results panel
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBackground(backgroundColor);

        // Column headers
        String[] columnNames = {"ID", "Name", "Age", "Last Visit", "Status"};
        tableModel = new DefaultTableModel(initialData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        resultsTable = new JTable(tableModel);
        resultsTable.setFont(normalFont);
        resultsTable.setRowHeight(30);
        resultsTable.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 14));
        resultsTable.getTableHeader().setBackground(primaryColor);
        resultsTable.getTableHeader().setForeground(Color.WHITE);
        resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));

        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setBackground(backgroundColor);

        JButton viewButton = createButton("View Details");
        JButton editButton = createButton("Edit Record");
        JButton historyButton = createButton("Medical History");

        actionPanel.add(viewButton);
        actionPanel.add(editButton);
        actionPanel.add(historyButton);

        // Add components to results panel
        resultsPanel.add(scrollPane, BorderLayout.CENTER);
        resultsPanel.add(actionPanel, BorderLayout.SOUTH);

        // Add panels to content panel
        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.add(resultsPanel, BorderLayout.CENTER);

        // Add content panel to center
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setBackground(backgroundColor);
        wrapper.add(contentPanel);
        add(wrapper, BorderLayout.CENTER);

        // Add action listeners
        searchButton.addActionListener(e -> performSearch(searchField.getText()));
        viewButton.addActionListener(e -> viewPatientDetails());
        editButton.addActionListener(e -> editPatientRecord());
        historyButton.addActionListener(e -> viewMedicalHistory());

        // Add enter key listener to search field
        searchField.addActionListener(e -> performSearch(searchField.getText()));
    }

    private void performSearch(String query) {
        if (query.trim().isEmpty()) {
            tableModel.setDataVector(initialData, new String[]{"ID", "Name", "Age", "Last Visit", "Status"});
            return;
        }

        List<Object[]> filteredData = new ArrayList<>();
        String lowercaseQuery = query.toLowerCase().trim();

        for (Object[] row : initialData) {
            String id = ((String) row[0]).toLowerCase();
            String name = ((String) row[1]).toLowerCase();

            if (id.contains(lowercaseQuery) || name.contains(lowercaseQuery)) {
                filteredData.add(row);
            }
        }

        if (filteredData.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No patients found matching: " + query,
                    "Search Results",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            Object[][] newData = filteredData.toArray(new Object[0][]);
            tableModel.setDataVector(newData, new String[]{"ID", "Name", "Age", "Last Visit", "Status"});
        }
    }

    private void viewPatientDetails() {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a patient to view details",
                    "View Details",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String patientId = (String) tableModel.getValueAt(selectedRow, 0);
        String patientName = (String) tableModel.getValueAt(selectedRow, 1);

        JOptionPane.showMessageDialog(this,
                "Viewing details for patient: " + patientName + " (ID: " + patientId + ")",
                "Patient Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void editPatientRecord() {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a patient to edit record",
                    "Edit Record",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String patientId = (String) tableModel.getValueAt(selectedRow, 0);
        String patientName = (String) tableModel.getValueAt(selectedRow, 1);

        JOptionPane.showMessageDialog(this,
                "Editing record for patient: " + patientName + " (ID: " + patientId + ")",
                "Edit Patient Record",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewMedicalHistory() {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a patient to view medical history",
                    "Medical History",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String patientId = (String) tableModel.getValueAt(selectedRow, 0);
        String patientName = (String) tableModel.getValueAt(selectedRow, 1);

        JOptionPane.showMessageDialog(this,
                "Viewing medical history for patient: " + patientName + " (ID: " + patientId + ")",
                "Medical History",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
