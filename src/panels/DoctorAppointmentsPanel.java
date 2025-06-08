package panels;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.DatabaseManager;
import models.User;

public class DoctorAppointmentsPanel extends JPanel {

    private User currentUser;
    private Date currentDate;
    private JTable appointmentsTable;
    private DefaultTableModel tableModel;
    private JLabel dateLabel;
    private JTextArea patientDetailsArea;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");

    public DoctorAppointmentsPanel(User user) {
        this.currentUser = user;
        this.currentDate = new Date();
        initComponents();
        loadAppointments();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(0, 120, 212));
        titlePanel.setPreferredSize(new Dimension(0, 60));

        JLabel titleLabel = new JLabel("My Appointments");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        // Calendar Navigation
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navPanel.setBackground(new Color(0, 120, 212));

        JButton prevButton = new JButton("←");
        JButton nextButton = new JButton("→");
        dateLabel = new JLabel(dateFormat.format(currentDate));

        prevButton.addActionListener(e -> {
            currentDate = new Date(currentDate.getTime() - 24 * 60 * 60 * 1000);
            dateLabel.setText(dateFormat.format(currentDate));
            loadAppointments();
        });

        nextButton.addActionListener(e -> {
            currentDate = new Date(currentDate.getTime() + 24 * 60 * 60 * 1000);
            dateLabel.setText(dateFormat.format(currentDate));
            loadAppointments();
        });

        navPanel.add(prevButton);
        navPanel.add(dateLabel);
        navPanel.add(nextButton);
        titlePanel.add(navPanel, BorderLayout.EAST);

        add(titlePanel, BorderLayout.NORTH);

        // Appointments Table
        String[] columns = {"Time", "Patient", "Type", "Status", "Notes"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        appointmentsTable = new JTable(tableModel);
        appointmentsTable.setRowHeight(30);
        appointmentsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane tableScrollPane = new JScrollPane(appointmentsTable);
        tableScrollPane.setPreferredSize(new Dimension(600, 300));

        // Patient Details Panel
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Patient Details"));

        patientDetailsArea = new JTextArea();
        patientDetailsArea.setEditable(false);
        patientDetailsArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane detailsScrollPane = new JScrollPane(patientDetailsArea);

        detailsPanel.add(detailsScrollPane, BorderLayout.CENTER);

        // Split the main panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScrollPane, detailsPanel);
        splitPane.setDividerLocation(300);

        add(splitPane, BorderLayout.CENTER);

        // Add selection listener
        appointmentsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = appointmentsTable.getSelectedRow();
                if (selectedRow != -1) {
                    updatePatientDetails(selectedRow);
                }
            }
        });
    }

    private void loadAppointments() {
        tableModel.setRowCount(0);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            System.out.println("\n=== Debug Information ===");
            System.out.println("Current Doctor: " + currentUser.getFullName());
            System.out.println("Current Doctor ID: " + currentUser.getId());
            System.out.println("Current Date: " + new SimpleDateFormat("yyyy-MM-dd").format(currentDate));

            conn = DatabaseManager.getConnection();

            // First, verify the doctor exists
            String doctorQuery = "SELECT id, full_name, user_type FROM users WHERE full_name = ?";
            pstmt = conn.prepareStatement(doctorQuery);
            pstmt.setString(1, currentUser.getFullName());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("\nDoctor Information:");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("full_name"));
                System.out.println("Type: " + rs.getString("user_type"));
            } else {
                System.out.println("WARNING: Doctor not found in database!");
            }

            // Check all appointments in the database
            System.out.println("\nAll Appointments in Database:");
            String allAppointmentsQuery = """
                SELECT a.*, d.full_name as doctor_name, p.full_name as patient_name
                FROM appointments a
                JOIN users d ON a.doctor_id = d.id
                JOIN users p ON a.patient_id = p.id
                ORDER BY a.appointment_time
            """;
            pstmt = conn.prepareStatement(allAppointmentsQuery);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(String.format(
                        "Appointment: ID=%d, Doctor=%s, Patient=%s, Time=%s, Status=%s",
                        rs.getInt("id"),
                        rs.getString("doctor_name"),
                        rs.getString("patient_name"),
                        rs.getTimestamp("appointment_time"),
                        rs.getString("status")
                ));
            }

            // Now load appointments for the current doctor
            System.out.println("\nLoading appointments for current doctor...");
            String query = """
                SELECT a.appointment_time, u.full_name, a.appointment_type, a.status, a.notes
                FROM appointments a
                JOIN users u ON a.patient_id = u.id
                WHERE a.doctor_id = ?
                AND DATE(a.appointment_time) = DATE(?)
                ORDER BY a.appointment_time
            """;

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, currentUser.getId());
            pstmt.setTimestamp(2, new Timestamp(currentDate.getTime()));

            rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
                Object[] row = {
                    new SimpleDateFormat("HH:mm").format(rs.getTimestamp("appointment_time")),
                    rs.getString("full_name"),
                    rs.getString("appointment_type"),
                    rs.getString("status"),
                    rs.getString("notes")
                };
                tableModel.addRow(row);
            }
            System.out.println("Found " + count + " appointments for current date");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error loading appointments: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    DatabaseManager.releaseConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void updatePatientDetails(int selectedRow) {
        String patientName = (String) tableModel.getValueAt(selectedRow, 1);
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = """
                SELECT u.*, m.specialization, m.license_number
                FROM users u
                LEFT JOIN medical_staff m ON u.id = m.user_id
                WHERE u.full_name = ? AND u.user_type = 'Patient'
            """;

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, patientName);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                StringBuilder details = new StringBuilder();
                details.append("Name: ").append(rs.getString("full_name")).append("\n");
                details.append("Email: ").append(rs.getString("email")).append("\n");
                details.append("Department: ").append(rs.getString("department")).append("\n");
                details.append("Student ID: ").append(rs.getString("student_id")).append("\n");
                details.append("Age: ").append(rs.getInt("age")).append("\n");
                details.append("Blood Type: ").append(rs.getString("blood_type")).append("\n");

                patientDetailsArea.setText(details.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading patient details: " + e.getMessage());
        }
    }
}
