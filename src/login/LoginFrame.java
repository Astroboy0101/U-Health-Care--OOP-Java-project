package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import main.MainFrame;
import models.User;

public class LoginFrame extends javax.swing.JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeCombo;

    public LoginFrame() {
        // Initialize database first
        models.DatabaseManager.initializeDatabase();

        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("U HEALTH CARE - AAU");
        setResizable(false);
        setSize(800, 500);
        setLocationRelativeTo(null);

        // Main panel with grid layout
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        // Left panel with logo
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(0, 102, 68)); // Dark green background

        // Logo panel
        JPanel logoPanel = new JPanel(new GridBagLayout());
        logoPanel.setBackground(new Color(0, 102, 68));

        // Create circular logo
        JLabel logoLabel = new JLabel("U HEALTH CARE", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 36));
        logoLabel.setForeground(new Color(238, 238, 228)); // Off-white color

        try {
            ImageIcon logo = new ImageIcon(getClass().getResource("/images/logo.png"));
            if (logo.getImageLoadStatus() == MediaTracker.COMPLETE) {
                logoLabel.setIcon(logo);
                logoLabel.setText("");
            }
        } catch (Exception e) {
            // Keep the text-based logo if image loading fails
        }

        JLabel subtitleLabel = new JLabel("Addis Ababa University");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitleLabel.setForeground(new Color(238, 238, 228));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel2 = new JLabel("Health Management System");
        subtitleLabel2.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitleLabel2.setForeground(new Color(238, 238, 228));
        subtitleLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        // Add components to logo panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        logoPanel.add(logoLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 0);
        logoPanel.add(subtitleLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        logoPanel.add(subtitleLabel2, gbc);

        leftPanel.add(logoPanel, BorderLayout.CENTER);

        // Right panel
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);

        // Login form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 30, 5, 30);

        // User Type
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel userTypeLabel = new JLabel("User Type");
        userTypeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(userTypeLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 30, 20, 30);
        userTypeCombo = new JComboBox<>(new String[]{"Patient", "Doctor", "Admin"});
        userTypeCombo.setPreferredSize(new Dimension(300, 35));
        formPanel.add(userTypeCombo, gbc);

        // Username
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 30, 5, 30);
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(usernameLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 30, 20, 30);
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(300, 35));
        formPanel.add(usernameField, gbc);

        // Password
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 30, 5, 30);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(passwordLabel, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 30, 20, 30);
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 35));
        formPanel.add(passwordField, gbc);

        // Login button
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 30, 20, 30);
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 102, 68));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(300, 40));
        formPanel.add(loginButton, gbc);

        // Registration link
        gbc.gridy = 7;
        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerPanel.setBackground(Color.WHITE);
        JLabel notRegisteredLabel = new JLabel("Not registered yet?");
        notRegisteredLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        JButton registerLink = new JButton("Create an Account");
        registerLink.setFont(new Font("Arial", Font.PLAIN, 12));
        registerLink.setBorderPainted(false);
        registerLink.setContentAreaFilled(false);
        registerLink.setForeground(new Color(0, 102, 68));
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        registerPanel.add(notRegisteredLabel);
        registerPanel.add(registerLink);
        formPanel.add(registerPanel, gbc);

        rightPanel.add(formPanel);

        // Add panels to main panel
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        // Add main panel to frame
        add(mainPanel);

        // Add action listeners
        loginButton.addActionListener(e -> handleLogin());
        registerLink.addActionListener(e -> handleRegister());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String selectedUserType = (String) userTypeCombo.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password");
            return;
        }

        try {
            Connection conn = models.DatabaseManager.getConnection();
            String query = "SELECT * FROM users WHERE username = ? AND password = ? AND user_type = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, selectedUserType);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_type"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getString("student_id")
                );
                // Set age and blood type from the database
                user.setAge(rs.getInt("age"));
                user.setBloodType(rs.getString("blood_type"));
                System.out.println("Login successful. User: " + user);
                new MainFrame(user).setVisible(true);
                this.dispose();
            } else {
                showError("Invalid username or password for selected user type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database error: " + e.getMessage());
        }
    }

    private void handleRegister() {
        new RegisterFrame().setVisible(true);
        this.dispose();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
