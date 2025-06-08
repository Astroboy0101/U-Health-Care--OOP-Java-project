package panels;

import java.awt.*;
import javax.swing.*;
import models.User;

public class ProfilePanel extends BasePanel {

    private User currentUser;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField departmentField;
    private JTextField idField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    public ProfilePanel(User user) {
        super();
        this.currentUser = user;
        initComponents();
    }

    private void initComponents() {
        // Add title
        add(createTitlePanel("My Profile"), BorderLayout.NORTH);

        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(backgroundColor);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createLabel("Full Name:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        fullNameField = new JTextField(currentUser.getFullName());
        formPanel.add(fullNameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(createLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        emailField = new JTextField(currentUser.getEmail());
        formPanel.add(emailField, gbc);

        // Department
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(createLabel("Department:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        departmentField = new JTextField(currentUser.getDepartment());
        formPanel.add(departmentField, gbc);

        // ID (Student ID or Doctor ID)
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        String idLabel = currentUser.getUserType().equals("Patient") ? "Student ID:" : "Staff ID:";
        formPanel.add(createLabel(idLabel), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        idField = new JTextField(currentUser.getStudentId());
        idField.setEditable(false); // ID should not be editable
        formPanel.add(idField, gbc);

        // Change Password Section
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        JLabel passwordTitle = new JLabel("Change Password");
        passwordTitle.setFont(new Font("Poppins", Font.BOLD, 14));
        formPanel.add(passwordTitle, gbc);

        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        formPanel.add(createLabel("New Password:"), gbc);

        gbc.gridx = 1;
        newPasswordField = new JPasswordField();
        formPanel.add(newPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(createLabel("Confirm Password:"), gbc);

        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordField, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(backgroundColor);

        JButton saveButton = createButton("Save Changes");
        saveButton.addActionListener(e -> handleSave());

        JButton resetButton = createButton("Reset");
        resetButton.addActionListener(e -> handleReset());

        buttonPanel.add(resetButton);
        buttonPanel.add(saveButton);

        // Add components to content panel
        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add to main panel
        add(contentPanel, BorderLayout.CENTER);
    }

    private void handleSave() {
        // Validate email
        if (!emailField.getText().toLowerCase().endsWith("@aau.edu.et")) {
            showError("Please enter a valid AAU email address");
            return;
        }

        // Validate password if being changed
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (!newPassword.isEmpty() || !confirmPassword.isEmpty()) {
            if (newPassword.length() < 6) {
                showError("Password must be at least 6 characters long");
                return;
            }
            if (!newPassword.equals(confirmPassword)) {
                showError("Passwords do not match");
                return;
            }
        }

        // Update user information
        // In a real application, this would update the database
        JOptionPane.showMessageDialog(this,
                "Profile updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleReset() {
        fullNameField.setText(currentUser.getFullName());
        emailField.setText(currentUser.getEmail());
        departmentField.setText(currentUser.getDepartment());
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    @Override
    protected JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(normalFont);
        return label;
    }
}
