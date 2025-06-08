package panels;

import java.awt.*;
import javax.swing.*;

public class MedicalRecordsPanel extends BasePanel {

    public MedicalRecordsPanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        // Add title
        add(createTitlePanel("Medical Records"), BorderLayout.NORTH);

        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(backgroundColor);

        // Create search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(backgroundColor);

        searchPanel.add(createLabel("Patient Search:"));
        JTextField searchField = createTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setToolTipText("Search by name, ID, or condition");

        JButton searchButton = createButton("Search");

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Create records table
        String[] columnNames = {"ID", "Name", "Age", "Last Visit", "Condition", "Status"};
        Object[][] data = {
            {"P001", "Beka Taka", "45", "2024-05-15", "Hypertension", "Active"},
            {"P002", "Sena Ulfesa", "32", "2024-05-14", "Diabetes", "Active"},
            {"P003", "Roba Tedros", "28", "2024-05-10", "Asthma", "Active"},
            {"P004", "Ola Tesena", "55", "2024-05-13", "Arthritis", "Active"},
            {"P005", "Taye Dandana", "41", "2024-05-12", "Anxiety", "Active"}
        };

        JTable recordsTable = new JTable(data, columnNames);
        recordsTable.setFont(normalFont);
        recordsTable.setRowHeight(30);
        recordsTable.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 14));
        recordsTable.getTableHeader().setBackground(primaryColor);
        recordsTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane tableScroll = new JScrollPane(recordsTable);

        // Create details panel
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(backgroundColor);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Patient Details",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Poppins", Font.BOLD, 14)
        ));

        // Patient info panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Basic info
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(createLabel("Name:"), gbc);

        gbc.gridx = 1;
        JTextField nameField = createTextField();
        nameField.setEditable(false);
        nameField.setPreferredSize(new Dimension(200, 30));
        infoPanel.add(nameField, gbc);

        gbc.gridx = 2;
        infoPanel.add(createLabel("Age:"), gbc);

        gbc.gridx = 3;
        JTextField ageField = createTextField();
        ageField.setEditable(false);
        ageField.setPreferredSize(new Dimension(100, 30));
        infoPanel.add(ageField, gbc);

        // Medical info
        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(createLabel("Blood Type:"), gbc);

        gbc.gridx = 1;
        JTextField bloodTypeField = createTextField();
        bloodTypeField.setEditable(false);
        bloodTypeField.setPreferredSize(new Dimension(100, 30));
        infoPanel.add(bloodTypeField, gbc);

        gbc.gridx = 2;
        infoPanel.add(createLabel("Height:"), gbc);

        gbc.gridx = 3;
        JTextField heightField = createTextField();
        heightField.setEditable(false);
        heightField.setPreferredSize(new Dimension(100, 30));
        infoPanel.add(heightField, gbc);

        gbc.gridx = 4;
        infoPanel.add(createLabel("Weight:"), gbc);

        gbc.gridx = 5;
        JTextField weightField = createTextField();
        weightField.setEditable(false);
        weightField.setPreferredSize(new Dimension(100, 30));
        infoPanel.add(weightField, gbc);

        // Add info panel to details panel
        detailsPanel.add(infoPanel, BorderLayout.NORTH);

        // Create tabs for different sections
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(normalFont);

        // Medical History tab
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBackground(backgroundColor);
        JTextArea historyArea = new JTextArea(5, 40);
        historyArea.setEditable(false);
        historyArea.setFont(normalFont);
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);
        historyPanel.add(new JScrollPane(historyArea), BorderLayout.CENTER);
        tabbedPane.addTab("Medical History", historyPanel);

        // Allergies tab
        JPanel allergiesPanel = new JPanel(new BorderLayout());
        allergiesPanel.setBackground(backgroundColor);
        JTextArea allergiesArea = new JTextArea(5, 40);
        allergiesArea.setEditable(false);
        allergiesArea.setFont(normalFont);
        allergiesArea.setLineWrap(true);
        allergiesArea.setWrapStyleWord(true);
        allergiesPanel.add(new JScrollPane(allergiesArea), BorderLayout.CENTER);
        tabbedPane.addTab("Allergies", allergiesPanel);

        // Medications tab
        JPanel medicationsPanel = new JPanel(new BorderLayout());
        medicationsPanel.setBackground(backgroundColor);
        JTextArea medicationsArea = new JTextArea(5, 40);
        medicationsArea.setEditable(false);
        medicationsArea.setFont(normalFont);
        medicationsArea.setLineWrap(true);
        medicationsArea.setWrapStyleWord(true);
        medicationsPanel.add(new JScrollPane(medicationsArea), BorderLayout.CENTER);
        tabbedPane.addTab("Medications", medicationsPanel);

        // Add tabbed pane to details panel
        detailsPanel.add(tabbedPane, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(backgroundColor);

        JButton editButton = createButton("Edit Record");
        JButton addNoteButton = createButton("Add Note");
        JButton printButton = createButton("Print Record");
        JButton exportButton = createButton("Export");

        buttonPanel.add(editButton);
        buttonPanel.add(addNoteButton);
        buttonPanel.add(printButton);
        buttonPanel.add(exportButton);

        // Add components to content panel
        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.add(tableScroll, BorderLayout.CENTER);
        contentPanel.add(detailsPanel, BorderLayout.SOUTH);

        // Add panels to main frame
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        searchButton.addActionListener(e -> searchRecords(searchField.getText()));
        editButton.addActionListener(e -> editRecord());
        addNoteButton.addActionListener(e -> addNote());
        printButton.addActionListener(e -> printRecord());
        exportButton.addActionListener(e -> exportRecord());

        // Add table selection listener
        recordsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDetails(recordsTable.getSelectedRow());
            }
        });
    }

    private void searchRecords(String query) {
        JOptionPane.showMessageDialog(this,
                "Searching records: " + query,
                "Search",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void editRecord() {
        JOptionPane.showMessageDialog(this,
                "Opening record editor...",
                "Edit Record",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void addNote() {
        JOptionPane.showMessageDialog(this,
                "Opening note editor...",
                "Add Note",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void printRecord() {
        JOptionPane.showMessageDialog(this,
                "Preparing record for printing...",
                "Print Record",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportRecord() {
        JOptionPane.showMessageDialog(this,
                "Exporting medical record...",
                "Export",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateDetails(int selectedRow) {
        if (selectedRow >= 0) {
            // Implementation for updating details panel
        }
    }
}
