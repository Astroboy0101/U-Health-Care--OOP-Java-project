package panels;

import java.awt.*;
import javax.swing.*;

public class DepartmentPanel extends BasePanel {

    public DepartmentPanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        // Add title
        add(createTitlePanel("Department Management"), BorderLayout.NORTH);

        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(backgroundColor);

        // Create departments list panel
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBackground(backgroundColor);
        listPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Departments",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Poppins", Font.BOLD, 14)
        ));

        // Departments table
        String[] columnNames = {"ID", "Name", "Head", "Staff Count", "Status"};
        Object[][] data = {
            {"D001", "General Medicine", "Dr. Kena Fayera", "15", "Active"},
            {"D002", "Cardiology", "Dr. Leulekal Nahusenay", "10", "Active"},
            {"D003", "Orthopedics", "Dr. Sewyishal Netsanet", "8", "Active"},
            {"D004", "Neurology", "Dr. Semere Hailu", "12", "Active"},
            {"D005", "Pediatrics", "Dr. Yisakor Tamirat", "14", "Active"}
        };

        JTable departmentsTable = new JTable(data, columnNames);
        departmentsTable.setFont(normalFont);
        departmentsTable.setRowHeight(30);
        departmentsTable.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 14));
        departmentsTable.getTableHeader().setBackground(primaryColor);
        departmentsTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane tableScroll = new JScrollPane(departmentsTable);
        listPanel.add(tableScroll, BorderLayout.CENTER);

        // Department details panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBackground(backgroundColor);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Department Details",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Poppins", Font.BOLD, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Department info
        gbc.gridx = 0;
        gbc.gridy = 0;
        detailsPanel.add(createLabel("Department Name:"), gbc);

        gbc.gridx = 1;
        JTextField nameField = createTextField();
        nameField.setPreferredSize(new Dimension(200, 30));
        detailsPanel.add(nameField, gbc);

        gbc.gridx = 2;
        detailsPanel.add(createLabel("Department Head:"), gbc);

        gbc.gridx = 3;
        JComboBox<String> headCombo = new JComboBox<>(new String[]{
            "Dr. Kena Fayera", "Dr. Yisakor Tamirat", "Dr. Sewyishal Netsanet",
            "Dr. Leulekal Nahusenay", "Dr. Semere Hailu"
        });
        headCombo.setPreferredSize(new Dimension(200, 30));
        detailsPanel.add(headCombo, gbc);

        // Location
        gbc.gridx = 0;
        gbc.gridy = 1;
        detailsPanel.add(createLabel("Location:"), gbc);

        gbc.gridx = 1;
        JTextField locationField = createTextField();
        locationField.setPreferredSize(new Dimension(200, 30));
        detailsPanel.add(locationField, gbc);

        gbc.gridx = 2;
        detailsPanel.add(createLabel("Contact:"), gbc);

        gbc.gridx = 3;
        JTextField contactField = createTextField();
        contactField.setPreferredSize(new Dimension(200, 30));
        detailsPanel.add(contactField, gbc);

        // Description
        gbc.gridx = 0;
        gbc.gridy = 2;
        detailsPanel.add(createLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        JTextArea descriptionArea = new JTextArea(3, 40);
        descriptionArea.setFont(normalFont);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        detailsPanel.add(descriptionScroll, gbc);

        // Staff list
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        JPanel staffPanel = new JPanel(new BorderLayout());
        staffPanel.setBackground(backgroundColor);
        staffPanel.setBorder(BorderFactory.createTitledBorder("Staff Members"));

        String[] staffColumns = {"ID", "Name", "Role", "Status"};
        Object[][] staffData = {
            {"S001", "Dr. Kena Fayera", "Department Head", "Active"},
            {"S002", "Dr. Semere Hailu", "Senior Doctor", "Active"},
            {"S003", "Dr. Sewyishal Netsanet", "Doctor", "Active"},
            {"S004", "Nurse Merion", "Head Nurse", "Active"}
        };

        JTable staffTable = new JTable(staffData, staffColumns);
        staffTable.setFont(normalFont);
        staffTable.setRowHeight(30);
        staffTable.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 14));
        staffTable.getTableHeader().setBackground(primaryColor);
        staffTable.getTableHeader().setForeground(Color.WHITE);

        staffPanel.add(new JScrollPane(staffTable), BorderLayout.CENTER);
        detailsPanel.add(staffPanel, gbc);

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(backgroundColor);

        JButton addButton = createButton("Add Department");
        JButton editButton = createButton("Edit");
        JButton saveButton = createButton("Save Changes");
        JButton deleteButton = createButton("Delete");
        deleteButton.setBackground(new Color(220, 53, 69));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);

        // Add components to content panel
        contentPanel.add(listPanel, BorderLayout.CENTER);
        contentPanel.add(detailsPanel, BorderLayout.SOUTH);

        // Add panels to main frame
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(e -> addDepartment());
        editButton.addActionListener(e -> editDepartment());
        saveButton.addActionListener(e -> saveDepartment());
        deleteButton.addActionListener(e -> deleteDepartment());

        // Add table selection listener
        departmentsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDetails(departmentsTable.getSelectedRow());
            }
        });
    }

    private void addDepartment() {
        JOptionPane.showMessageDialog(this,
                "Adding new department...",
                "Add Department",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void editDepartment() {
        JOptionPane.showMessageDialog(this,
                "Editing department...",
                "Edit Department",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveDepartment() {
        JOptionPane.showMessageDialog(this,
                "Saving department changes...",
                "Save Changes",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteDepartment() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this department?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                    "Deleting department...",
                    "Delete Department",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateDetails(int selectedRow) {
        if (selectedRow >= 0) {
            // Implementation for updating details panel
        }
    }
}
