package panels;

import java.awt.*;
import javax.swing.*;

public class UserManagementPanel extends BasePanel {

    public UserManagementPanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        // Add title
        add(createTitlePanel("User Management"), BorderLayout.NORTH);

        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(backgroundColor);

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(backgroundColor);

        JComboBox<String> userTypeFilter = new JComboBox<>(new String[]{"All Users", "Patients", "Doctors", "Administrators"});
        userTypeFilter.setPreferredSize(new Dimension(150, 30));

        JTextField searchField = createTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Search users");

        JButton searchButton = createButton("Search");
        JButton addButton = createButton("Add New User");

        filterPanel.add(userTypeFilter);
        filterPanel.add(searchField);
        filterPanel.add(searchButton);
        filterPanel.add(addButton);

        // Users table
        String[] columnNames = {"ID", "Name", "Role", "Email", "Status", "Last Login"};
        Object[][] data = {
            {"U001", "Kena Fayera", "Doctor", "kena.doc@email.com", "Active", "2024-05-15 10:30"},
            {"U002", "Sena Ulfesa", "Patient", "sena.ulfesa@email.com", "Active", "2024-05-14 15:45"},
            {"U003", "Bethelot Dawit", "patient", "bethelot.dawit@email.com", "Active", "2024-05-15 09:00"},
            {"U004", "Leulekal Nahusenay", "Doctor", "leulekal.doc@email.com", "Inactive", "2024-05-10 11:20"},
            {"U005", "Ola Tesena", "Patient", "ola.tesena@email.com", "Active", "2024-05-13 14:15"}
        };

        JTable usersTable = new JTable(data, columnNames);
        usersTable.setFont(normalFont);
        usersTable.setRowHeight(30);
        usersTable.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 14));
        usersTable.getTableHeader().setBackground(primaryColor);
        usersTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(usersTable);

        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setBackground(backgroundColor);

        JButton editButton = createButton("Edit User");
        JButton deactivateButton = createButton("Deactivate");
        JButton resetPasswordButton = createButton("Reset Password");

        actionPanel.add(editButton);
        actionPanel.add(deactivateButton);
        actionPanel.add(resetPasswordButton);

        // Add components to content panel
        contentPanel.add(filterPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(actionPanel, BorderLayout.SOUTH);

        // Add content panel to center
        add(contentPanel, BorderLayout.CENTER);

        // Add action listeners
        addButton.addActionListener(e -> showAddUserDialog());
        editButton.addActionListener(e -> showEditUserDialog());
        deactivateButton.addActionListener(e -> deactivateUser());
        resetPasswordButton.addActionListener(e -> resetUserPassword());
        searchButton.addActionListener(e -> searchUsers(searchField.getText()));
        userTypeFilter.addActionListener(e -> filterUsers((String) userTypeFilter.getSelectedItem()));
    }

    private void showAddUserDialog() {
        JOptionPane.showMessageDialog(this,
                "Add new user dialog will be shown here",
                "Add User",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showEditUserDialog() {
        JOptionPane.showMessageDialog(this,
                "Edit user dialog will be shown here",
                "Edit User",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void deactivateUser() {
        JOptionPane.showMessageDialog(this,
                "User will be deactivated here",
                "Deactivate User",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetUserPassword() {
        JOptionPane.showMessageDialog(this,
                "User password will be reset here",
                "Reset Password",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void searchUsers(String query) {
        JOptionPane.showMessageDialog(this,
                "Searching users: " + query,
                "Search Users",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void filterUsers(String userType) {
        JOptionPane.showMessageDialog(this,
                "Filtering users by type: " + userType,
                "Filter Users",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
