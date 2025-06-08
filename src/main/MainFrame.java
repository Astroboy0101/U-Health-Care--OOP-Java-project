package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import models.User;
import panels.AboutUsPanel;
import panels.AppointmentPanel;
import panels.DepartmentPanel;
import panels.DoctorAppointmentsPanel;
import panels.DoctorSchedulePanel;
import panels.MedicalHistoryPanel;
import panels.MedicalRecordsPanel;
import panels.PatientSearchPanel;
import panels.PersonalInfoPanel;
import panels.PrescriptionWriterPanel;
import panels.PrescriptionsPanel;
import panels.SystemReportsPanel;
import panels.UserManagementPanel;

public class MainFrame extends javax.swing.JFrame {

    private String userType;
    private JPanel headerPanel;
    private JPanel sidePanel;
    private JPanel mainPanel;
    private JLabel userLabel;
    private User currentUser;

    public MainFrame(User user) {
        this.currentUser = user;
        this.userType = user.getUserType();
        initComponents();
        setupUserInterface();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("U HEALTH CARE - AAU");
        setMinimumSize(new Dimension(1200, 700));
        setLocationRelativeTo(null);

        // Create main container
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        // Header Panel
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 102, 68));
        headerPanel.setPreferredSize(new Dimension(0, 60));

        JLabel titleLabel = new JLabel("U HEALTH CARE");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        userLabel = new JLabel(userType + " Dashboard");
        userLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        userLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);

        // About Us button
        JButton aboutButton = new JButton("About Us");
        aboutButton.setForeground(Color.WHITE);
        aboutButton.setBackground(new Color(0, 102, 68));
        aboutButton.setBorderPainted(false);
        aboutButton.setFocusPainted(false);
        aboutButton.addActionListener(e -> showPanel(new AboutUsPanel()));

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(0, 102, 68));
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> handleLogout());

        rightPanel.add(aboutButton);
        rightPanel.add(logoutButton);

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(userLabel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        // Side Panel
        sidePanel = new JPanel();
        sidePanel.setBackground(new Color(51, 51, 51));
        sidePanel.setPreferredSize(new Dimension(250, 0));
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

        // Main Content Panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Add panels to frame
        contentPane.add(headerPanel, BorderLayout.NORTH);
        contentPane.add(sidePanel, BorderLayout.WEST);
        contentPane.add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    private void setupUserInterface() {
        // Clear existing menu items
        sidePanel.removeAll();

        // Add menu items based on user type
        if (userType.equals("Patient")) {
            addMenuItem("Personal Information", () -> showPanel(new PersonalInfoPanel(currentUser)));
            addMenuItem("Book Appointment", () -> showPanel(new AppointmentPanel(currentUser)));
            addMenuItem("Medical History", () -> showPanel(new MedicalHistoryPanel()));
            addMenuItem("Prescriptions", () -> showPanel(new PrescriptionsPanel()));
        } else if (userType.equals("Doctor")) {
            addMenuItem("Personal Information", () -> showPanel(new PersonalInfoPanel(currentUser)));
            addMenuItem("Patient Search", () -> showPanel(new PatientSearchPanel()));
            addMenuItem("Appointments", () -> showPanel(new DoctorAppointmentsPanel(currentUser)));
            addMenuItem("Medical Records", () -> showPanel(new MedicalRecordsPanel()));
            addMenuItem("Write Prescription", () -> showPanel(new PrescriptionWriterPanel()));
        } else if (userType.equals("Admin")) {
            addMenuItem("Personal Information", () -> showPanel(new PersonalInfoPanel(currentUser)));
            addMenuItem("User Management", () -> showPanel(new UserManagementPanel()));
            addMenuItem("Doctor Schedule", () -> showPanel(new DoctorSchedulePanel()));
            addMenuItem("Department Management", () -> showPanel(new DepartmentPanel()));
            addMenuItem("System Reports", () -> showPanel(new SystemReportsPanel()));
        }

        // Show default panel
        showDefaultPanel();
    }

    private void addMenuItem(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 40));
        button.setBackground(new Color(51, 51, 51));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addActionListener(e -> action.run());

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 102, 68));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(51, 51, 51));
            }
        });

        sidePanel.add(button);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 1)));
    }

    private void showPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showDefaultPanel() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Welcome to the Health Management System");
        welcomeLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(0, 102, 68));

        welcomePanel.add(welcomeLabel);
        showPanel(welcomePanel);
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new login.LoginFrame().setVisible(true);
        }
    }
}
