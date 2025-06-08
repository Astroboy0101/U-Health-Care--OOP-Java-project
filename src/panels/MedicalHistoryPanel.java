package panels;

import java.awt.*;
import javax.swing.*;

public class MedicalHistoryPanel extends BasePanel {

    public MedicalHistoryPanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        // Add title
        add(createTitlePanel("Medical History"), BorderLayout.NORTH);

        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(backgroundColor);

        // Create filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(backgroundColor);

        // Date range selection
        filterPanel.add(createLabel("From:"));
        JTextField fromDate = createTextField();
        fromDate.setPreferredSize(new Dimension(120, 30));
        filterPanel.add(fromDate);

        filterPanel.add(createLabel("To:"));
        JTextField toDate = createTextField();
        toDate.setPreferredSize(new Dimension(120, 30));
        filterPanel.add(toDate);

        // Category filter
        filterPanel.add(createLabel("Category:"));
        JComboBox<String> categoryCombo = new JComboBox<>(new String[]{
            "All", "Consultations", "Tests", "Procedures", "Medications"
        });
        categoryCombo.setPreferredSize(new Dimension(150, 30));
        filterPanel.add(categoryCombo);

        JButton filterButton = createButton("Apply Filter");
        filterPanel.add(filterButton);

        // Create table for medical history
        String[] columnNames = {"Date", "Category", "Description", "Doctor", "Notes"};
        Object[][] data = {
            {"2024-05-15", "Consultation", "Regular Checkup", "Dr. Semere Hailu", "Normal health status"},
            {"2024-05-10", "Test", "Blood Test", "Dr. Kena Fayera", "Results pending"},
            {"2024-05-01", "Medication", "Antibiotics", "Dr. Sewyishal Netsanet", "7 days course"},
            {"2024-04-25", "Procedure", "X-Ray", " Dr. Yisakor Tamirat", "Chest X-ray"},
            {"2024-04-20", "Consultation", "Follow-up", "Dr. Leulekal Nahusena", "Improving condition"}
        };

        JTable historyTable = new JTable(data, columnNames);
        historyTable.setFont(normalFont);
        historyTable.setRowHeight(30);
        historyTable.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 14));
        historyTable.getTableHeader().setBackground(primaryColor);
        historyTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(historyTable);

        // Create details panel
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(backgroundColor);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Details",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Poppins", Font.BOLD, 14)
        ));

        JTextArea detailsArea = new JTextArea(5, 40);
        detailsArea.setEditable(false);
        detailsArea.setFont(normalFont);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setText("Select a record to view details.");

        detailsPanel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(backgroundColor);

        JButton viewButton = createButton("View Full Record");
        JButton printButton = createButton("Print");
        JButton exportButton = createButton("Export");

        buttonPanel.add(viewButton);
        buttonPanel.add(printButton);
        buttonPanel.add(exportButton);

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
        viewButton.addActionListener(e -> viewFullRecord());
        printButton.addActionListener(e -> printRecord());
        exportButton.addActionListener(e -> exportRecord());

        // Add table selection listener
        historyTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDetails(historyTable.getSelectedRow());
            }
        });
    }

    private void applyFilter() {
        JOptionPane.showMessageDialog(this,
                "Applying filters...",
                "Filter",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewFullRecord() {
        JOptionPane.showMessageDialog(this,
                "Opening full medical record...",
                "View Record",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void printRecord() {
        JOptionPane.showMessageDialog(this,
                "Preparing document for printing...",
                "Print",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportRecord() {
        JOptionPane.showMessageDialog(this,
                "Exporting medical history...",
                "Export",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateDetails(int selectedRow) {
        if (selectedRow >= 0) {
            // Implementation for updating details panel
        }
    }
}
