package panels;

import java.awt.*;
import javax.swing.*;
import models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonalInfoPanel extends BasePanel {

    private User currentUser;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField departmentField;
    private JTextField idField;
    private JSpinner ageSpinner;
    private JComboBox<String> bloodTypeCombo;

    public PersonalInfoPanel(User user) {
        super();
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.currentUser = user;
        System.out.println("PersonalInfoPanel: User passed: " + user);
        System.out.println("PersonalInfoPanel: User full name: " + user.getFullName());
        System.out.println("PersonalInfoPanel: User email: " + user.getEmail());
        System.out.println("PersonalInfoPanel: User department: " + user.getDepartment());
        System.out.println("PersonalInfoPanel: User student ID: " + user.getStudentId());
        System.out.println("PersonalInfoPanel: User age: " + user.getAge());
        System.out.println("PersonalInfoPanel: User blood type: " + user.getBloodType());
        initComponents();
    }

    private void initComponents() {
        // Add title
        add(createTitlePanel("Personal Information"), BorderLayout.NORTH);

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

        // Age
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        formPanel.add(createLabel("Age:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        SpinnerNumberModel ageModel = new SpinnerNumberModel(currentUser.getAge(), 0, 150, 1);
        ageSpinner = new JSpinner(ageModel);
        formPanel.add(ageSpinner, gbc);

        // Blood Type
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        formPanel.add(createLabel("Blood Type:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        bloodTypeCombo = new JComboBox<>(new String[]{
            "Select Blood Type", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"
        });
        if (currentUser.getBloodType() != null && !currentUser.getBloodType().isEmpty()) {
            bloodTypeCombo.setSelectedItem(currentUser.getBloodType());
        }
        formPanel.add(bloodTypeCombo, gbc);

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

        // Set initial values
        fullNameField.setText(currentUser.getFullName());
        emailField.setText(currentUser.getEmail());
        departmentField.setText(currentUser.getDepartment());
        idField.setText(currentUser.getStudentId());
        ageSpinner.setValue(currentUser.getAge());
        bloodTypeCombo.setSelectedItem(currentUser.getBloodType() != null ? currentUser.getBloodType() : "Select Blood Type");
    }

    private void handleSave() {
        // Validate email
        if (!emailField.getText().toLowerCase().endsWith("@aau.edu.et")) {
            showError("Please enter a valid AAU email address");
            return;
        }

        // Validate blood type
        if (bloodTypeCombo.getSelectedIndex() == 0) {
            showError("Please select your blood type");
            return;
        }

        // Update user information
        currentUser.setFullName(fullNameField.getText());
        currentUser.setEmail(emailField.getText());
        currentUser.setDepartment(departmentField.getText());
        currentUser.setAge((Integer) ageSpinner.getValue());
        currentUser.setBloodType((String) bloodTypeCombo.getSelectedItem());

        // Save to database
        try {
            Connection conn = models.DatabaseManager.getConnection();
            String query = "UPDATE users SET full_name = ?, email = ?, department = ?, age = ?, blood_type = ? WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, currentUser.getFullName());
            pstmt.setString(2, currentUser.getEmail());
            pstmt.setString(3, currentUser.getDepartment());
            pstmt.setInt(4, currentUser.getAge());
            pstmt.setString(5, currentUser.getBloodType());
            pstmt.setString(6, currentUser.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Failed to save changes: " + e.getMessage());
            return;
        }

        // Show success message
        JOptionPane.showMessageDialog(this,
                "Personal information updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleReset() {
        fullNameField.setText(currentUser.getFullName());
        emailField.setText(currentUser.getEmail());
        departmentField.setText(currentUser.getDepartment());
        idField.setText(currentUser.getStudentId());
        ageSpinner.setValue(currentUser.getAge());
        bloodTypeCombo.setSelectedItem(currentUser.getBloodType() != null ? currentUser.getBloodType() : "Select Blood Type");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
