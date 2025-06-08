package login;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSliderUI;

public class RegisterFrame extends JFrame {

    private JTextField firstnameField;
    private JTextField lastnameField;
    private JTextField studentIdField;
    private JTextField emailField;
    private JComboBox<String> departmentCombo;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private JButton registerButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private JPanel contentPanel;
    private boolean maximized = false;
    private Rectangle normalBounds;
    private JButton togglePasswordButton;
    private JButton toggleConfirmPasswordButton;
    private boolean passwordVisible = false;
    private boolean confirmPasswordVisible = false;

    public RegisterFrame() {
        setTitle("Register - U HEALTH CARE");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(true);
        initComponents();
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Custom Title Bar
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(240, 240, 240));
        titleBar.setPreferredSize(new Dimension(0, 36));
        JLabel titleLabel = new JLabel("  Register - U HEALTH CARE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(new Color(40, 40, 40));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleBar.add(titleLabel, BorderLayout.WEST);

        // Desktop buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);
        JButton minBtn = createTitleButton("-", "Minimize");
        JButton maxBtn = createTitleButton("☐", "Maximize/Restore");
        JButton closeBtn = createTitleButton("x", "Close");
        buttonPanel.add(minBtn);
        buttonPanel.add(maxBtn);
        buttonPanel.add(closeBtn);
        titleBar.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(titleBar, BorderLayout.NORTH);

        // Title bar actions
        minBtn.addActionListener(e -> setState(Frame.ICONIFIED));
        maxBtn.addActionListener(e -> toggleMaximize());
        closeBtn.addActionListener(e -> dispose());

        // Drag window
        MouseAdapter dragListener = new MouseAdapter() {
            private Point mouseDownCompCoords = null;

            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }

            public void mouseDragged(MouseEvent e) {
                if (!maximized) {
                    Point currCoords = e.getLocationOnScreen();
                    setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
                }
            }
        };
        titleBar.addMouseListener(dragListener);
        titleBar.addMouseMotionListener(dragListener);

        // Content panel (everything except title bar and slider)
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // Left panel (branding)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 102, 68));
        leftPanel.setPreferredSize(new Dimension(350, 0));
        leftPanel.setLayout(new BorderLayout());

        // Logo panel
        JPanel logoPanel = new JPanel(new GridBagLayout());
        logoPanel.setBackground(new Color(0, 102, 68));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);

        // Logo at the top
        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            java.net.URL logoUrl = getClass().getResource("/images/logo.png");
            if (logoUrl != null) {
                ImageIcon logo = new ImageIcon(logoUrl);
                if (logo.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    Image scaledImage = logo.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    logoLabel.setIcon(new ImageIcon(scaledImage));
                }
            }
        } catch (Exception e) {
            // fallback: no icon
        }
        logoPanel.add(logoLabel, gbc);

        // 'U HEALTH CARE' text immediately below logo
        JLabel leftTitleLabel = new JLabel("U HEALTH CARE");
        leftTitleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        leftTitleLabel.setForeground(new Color(238, 238, 228));
        leftTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        logoPanel.add(leftTitleLabel, gbc);

        // Tagline below 'U HEALTH CARE'
        JLabel taglineLabel1 = new JLabel("Addis Ababa University");
        taglineLabel1.setFont(new Font("Arial", Font.PLAIN, 18));
        taglineLabel1.setForeground(new Color(238, 238, 228));
        taglineLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 2;
        logoPanel.add(taglineLabel1, gbc);

        JLabel taglineLabel2 = new JLabel("Health management system");
        taglineLabel2.setFont(new Font("Arial", Font.PLAIN, 18));
        taglineLabel2.setForeground(new Color(238, 238, 228));
        taglineLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 3;
        logoPanel.add(taglineLabel2, gbc);

        // Add logoPanel to leftPanel (centered)
        leftPanel.add(logoPanel, BorderLayout.CENTER);

        // Right panel (form)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        rightPanel.setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Student Registration");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        rightPanel.add(title, BorderLayout.NORTH);

        // Form fields
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        // First Name
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(new JLabel("First Name *"), c);
        c.gridy = 1;
        firstnameField = new JTextField();
        formPanel.add(firstnameField, c);

// Last Name
        c.gridx = 1;
        c.gridy = 0;
        formPanel.add(new JLabel("Last Name *"), c);
        c.gridy = 1;
        lastnameField = new JTextField();
        formPanel.add(lastnameField, c);

        // Student ID
        c.gridx = 0;
        c.gridy = 2;
        formPanel.add(new JLabel("Student ID (UGR/XXXX/XX) *"), c);
        c.gridy = 3;
        studentIdField = new JTextField();
        formPanel.add(studentIdField, c);

        // Email
        c.gridx = 1;
        c.gridy = 2;
        formPanel.add(new JLabel("AAU Email (name-ugr-XXXX-XX@aau.edu.et) *"), c);
        c.gridy = 3;
        emailField = new JTextField();
        formPanel.add(emailField, c);

        // Department
        c.gridx = 0;
        c.gridy = 4;
        formPanel.add(new JLabel("Department *"), c);
        c.gridy = 5;
        departmentCombo = new JComboBox<>(new String[]{
            "Select Department",
            "Biomedical Engineering",
            "Electrical Engineering",
            "Mechanical Engineering",
            "Civil Engineering",
            "Chemical Engineering",
            "Software Engineering",
            "Computer Science",
            "Mathematics",
            "Physics",
            "Chemistry",
            "Biology",
            "Other"
        });
        formPanel.add(departmentCombo, c);

        // Gender
        c.gridx = 1;
        c.gridy = 4;
        formPanel.add(new JLabel("Gender *"), c);
        c.gridy = 5;
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.setBackground(Color.WHITE);
        ButtonGroup genderGroup = new ButtonGroup();
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        formPanel.add(genderPanel, c);

        // Password
        c.gridx = 0;
        c.gridy = 6;
        formPanel.add(new JLabel("Password *"), c);
        c.gridy = 7;

        // Password field with toggle button
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(Color.WHITE);
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(270, 35));
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        togglePasswordButton = new JButton("👁");
        togglePasswordButton.setPreferredSize(new Dimension(30, 35));
        togglePasswordButton.setFocusPainted(false);
        togglePasswordButton.setBorderPainted(false);
        togglePasswordButton.setContentAreaFilled(false);
        togglePasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        togglePasswordButton.addActionListener(e -> togglePasswordVisibility());
        passwordPanel.add(togglePasswordButton, BorderLayout.EAST);

        formPanel.add(passwordPanel, c);

        // Confirm Password
        c.gridx = 1;
        c.gridy = 6;
        formPanel.add(new JLabel("Confirm Password *"), c);
        c.gridy = 7;

        // Confirm password field with toggle button
        JPanel confirmPasswordPanel = new JPanel(new BorderLayout());
        confirmPasswordPanel.setBackground(Color.WHITE);
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(270, 35));
        confirmPasswordPanel.add(confirmPasswordField, BorderLayout.CENTER);

        toggleConfirmPasswordButton = new JButton("👁");
        toggleConfirmPasswordButton.setPreferredSize(new Dimension(30, 35));
        toggleConfirmPasswordButton.setFocusPainted(false);
        toggleConfirmPasswordButton.setBorderPainted(false);
        toggleConfirmPasswordButton.setContentAreaFilled(false);
        toggleConfirmPasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleConfirmPasswordButton.addActionListener(e -> toggleConfirmPasswordVisibility());
        confirmPasswordPanel.add(toggleConfirmPasswordButton, BorderLayout.EAST);

        formPanel.add(confirmPasswordPanel, c);

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;
        c.insets = new Insets(20, 10, 10, 10);
        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel2.setBackground(Color.WHITE);
        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(0, 102, 68));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setPreferredSize(new Dimension(120, 35));
        registerButton.addActionListener(e -> handleRegister());

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(new Color(0, 102, 68));
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(120, 35));
        cancelButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        buttonPanel2.add(registerButton);
        buttonPanel2.add(cancelButton);
        formPanel.add(buttonPanel2, c);

        rightPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(leftPanel, BorderLayout.WEST);
        contentPanel.add(rightPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JButton createTitleButton(String text, String tooltip) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Dialog", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setPreferredSize(new Dimension(40, 36));
        btn.setToolTipText(tooltip);
        return btn;
    }

    private void toggleMaximize() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        if (!maximized) {
            normalBounds = getBounds();
            setBounds(bounds);
            maximized = true;
        } else {
            if (normalBounds != null) {
                setBounds(normalBounds);
            }
            maximized = false;
        }
        revalidate();
    }

    private void handleRegister() {
        // Validate all required fields
        if (firstnameField.getText().trim().isEmpty()) {
            showError("Please enter your first name");
            return;
        }
        if (lastnameField.getText().trim().isEmpty()) {
            showError("Please enter your last name");
            return;
        }

        // Validate Student ID format (UGR-XXXX-XX)
        String studentId = studentIdField.getText().trim();
        if (studentId.isEmpty()) {
            showError("Please enter your student ID");
            return;
        }
        if (!studentId.matches("UGR/\\d{4}/\\d{2}")) {
            showError("Student ID must be in the format UGR/XXXX/XX (e.g., UGR/5643/16)");
            return;
        }

        // Validate AAU Email format (name-ugr-XXXX-XX@aau.edu.et)
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            showError("Please enter your AAU email");
            return;
        }
        if (!email.matches("[a-zA-Z.]+-ugr-\\d{4}-\\d{2}@aau\\.edu\\.et")) {
            showError("Email must be in the format name-ugr-XXXX-XX@aau.edu.et (e.g., kena.fayera-ugr-5643-16@aau.edu.et)");
            return;
        }

        // Extract ID numbers from email and student ID for comparison
        String emailId = email.substring(email.indexOf("-ugr-") + 5, email.indexOf("@"));
        String studentIdNumbers = studentId.substring(4);
        if (!emailId.equals(studentIdNumbers)) {
            showError("The ID numbers in your email must match your student ID");
            return;
        }

        if (departmentCombo.getSelectedIndex() == 0) {
            showError("Please select your department");
            return;
        }
        if (!maleRadio.isSelected() && !femaleRadio.isSelected()) {
            showError("Please select your gender");
            return;
        }
        if (passwordField.getPassword().length == 0) {
            showError("Please enter a password");
            return;
        }
        if (confirmPasswordField.getPassword().length == 0) {
            showError("Please confirm your password");
            return;
        }
        if (!new String(passwordField.getPassword()).equals(new String(confirmPasswordField.getPassword()))) {
            showError("Passwords do not match");
            return;
        }

        // If all validation passes, proceed with registration
        // TODO: Add actual registration logic here
        JOptionPane.showMessageDialog(this,
                "Registration successful!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            passwordField.setEchoChar((char) 0);
            togglePasswordButton.setText("👁‍🗨");
        } else {
            passwordField.setEchoChar('•');
            togglePasswordButton.setText("👁");
        }
    }

    private void toggleConfirmPasswordVisibility() {
        confirmPasswordVisible = !confirmPasswordVisible;
        if (confirmPasswordVisible) {
            confirmPasswordField.setEchoChar((char) 0);
            toggleConfirmPasswordButton.setText("👁‍🗨");
        } else {
            confirmPasswordField.setEchoChar('•');
            toggleConfirmPasswordButton.setText("👁");
        }
    }

    // Custom modern slider UI
    static class ModernSliderUI extends BasicSliderUI {

        public ModernSliderUI(JSlider s) {
            super(s);
        }

        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = 28, h = 28;
            int x = thumbRect.x + (thumbRect.width - w) / 2;
            int y = thumbRect.y + (thumbRect.height - h) / 2;
            g2.setColor(new Color(0, 102, 68));
            g2.fillRoundRect(x, y, w, h, 16, 16);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(x, y, w, h, 16, 16);
            g2.dispose();
        }

        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int x = trackRect.x;
            int y = trackRect.y + trackRect.height / 2 - 4;
            int w = trackRect.width;
            int h = 8;
            g2.setColor(new Color(220, 220, 220));
            g2.fillRoundRect(x, y, w, h, 8, 8);
            g2.dispose();
        }
    }
}
