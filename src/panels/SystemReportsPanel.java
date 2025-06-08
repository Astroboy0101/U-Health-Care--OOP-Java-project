package panels;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SystemReportsPanel extends BasePanel {

    public SystemReportsPanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        // Add title
        add(createTitlePanel("System Reports"), BorderLayout.NORTH);

        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(backgroundColor);

        // Create report selection panel
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionPanel.setBackground(backgroundColor);

        // Report type selection
        selectionPanel.add(createLabel("Report Type:"));
        JComboBox<String> reportTypeCombo = new JComboBox<>(new String[]{
            "Patient Statistics", "Department Performance", "Doctor Workload",
            "Appointment Analytics", "Resource Utilization"
        });
        reportTypeCombo.setPreferredSize(new Dimension(200, 30));
        selectionPanel.add(reportTypeCombo);

        // Date range selection
        selectionPanel.add(createLabel("From:"));
        JFormattedTextField fromDate = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        fromDate.setValue(new Date());
        fromDate.setPreferredSize(new Dimension(120, 30));
        selectionPanel.add(fromDate);

        selectionPanel.add(createLabel("To:"));
        JFormattedTextField toDate = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        toDate.setValue(new Date());
        toDate.setPreferredSize(new Dimension(120, 30));
        selectionPanel.add(toDate);

        JButton generateButton = createButton("Generate Report");
        selectionPanel.add(generateButton);

        // Create tabbed pane for different report views
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(normalFont);

        // Summary panel
        JPanel summaryPanel = createSummaryPanel();
        tabbedPane.addTab("Summary", summaryPanel);

        // Detailed view panel
        JPanel detailedPanel = createDetailedPanel();
        tabbedPane.addTab("Detailed View", detailedPanel);

        // Charts panel
        JPanel chartsPanel = createChartsPanel();
        tabbedPane.addTab("Charts", chartsPanel);

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(backgroundColor);

        JButton exportButton = createButton("Export Report");
        JButton printButton = createButton("Print Report");
        JButton scheduleButton = createButton("Schedule Report");

        buttonPanel.add(exportButton);
        buttonPanel.add(printButton);
        buttonPanel.add(scheduleButton);

        // Add components to content panel
        contentPanel.add(selectionPanel, BorderLayout.NORTH);
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        // Add panels to main frame
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        generateButton.addActionListener(e -> generateReport());
        exportButton.addActionListener(e -> exportReport());
        printButton.addActionListener(e -> printReport());
        scheduleButton.addActionListener(e -> scheduleReport());
        reportTypeCombo.addActionListener(e -> updateReportType());
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);

        // Key metrics panel
        JPanel metricsPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        metricsPanel.setBackground(backgroundColor);
        metricsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add metric cards
        addMetricCard(metricsPanel, "Total Patients", "1,234");
        addMetricCard(metricsPanel, "Active Appointments", "156");
        addMetricCard(metricsPanel, "Doctors Available", "5");
        addMetricCard(metricsPanel, "Departments", "12");
        addMetricCard(metricsPanel, "Average Wait Time", "15 mins");
        addMetricCard(metricsPanel, "Patient Satisfaction", "4.5/5");

        panel.add(metricsPanel, BorderLayout.NORTH);

        // Summary table
        String[] columnNames = {"Metric", "Value", "Change"};
        Object[][] data = {
            {"New Patients", "125", "+5%"},
            {"Completed Appointments", "450", "+2%"},
            {"Cancelled Appointments", "23", "-1%"},
            {"Average Treatment Time", "25 mins", "0%"},
            {"Resource Utilization", "85%", "+3%"}
        };

        JTable summaryTable = new JTable(data, columnNames);
        summaryTable.setFont(normalFont);
        summaryTable.setRowHeight(30);
        summaryTable.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 14));
        summaryTable.getTableHeader().setBackground(primaryColor);
        summaryTable.getTableHeader().setForeground(Color.WHITE);

        panel.add(new JScrollPane(summaryTable), BorderLayout.CENTER);

        return panel;
    }

    private void addMetricCard(JPanel panel, String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 14));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        valueLabel.setForeground(primaryColor);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        panel.add(card);
    }

    private JPanel createDetailedPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);

        // Create detailed table
        String[] columnNames = {"Date", "Department", "Patients", "Appointments", "Revenue", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable detailedTable = new JTable(model);
        detailedTable.setFont(normalFont);
        detailedTable.setRowHeight(30);
        detailedTable.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 14));
        detailedTable.getTableHeader().setBackground(primaryColor);
        detailedTable.getTableHeader().setForeground(Color.WHITE);

        panel.add(new JScrollPane(detailedTable), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createChartsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Placeholder for charts
        for (int i = 0; i < 4; i++) {
            JPanel chartPanel = new JPanel();
            chartPanel.setBackground(Color.WHITE);
            chartPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            panel.add(chartPanel);
        }

        return panel;
    }

    private void generateReport() {
        JOptionPane.showMessageDialog(this,
                "Generating report...",
                "Generate Report",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportReport() {
        JOptionPane.showMessageDialog(this,
                "Exporting report...",
                "Export",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void printReport() {
        JOptionPane.showMessageDialog(this,
                "Printing report...",
                "Print",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void scheduleReport() {
        JOptionPane.showMessageDialog(this,
                "Opening schedule report dialog...",
                "Schedule Report",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateReportType() {
        JOptionPane.showMessageDialog(this,
                "Updating report type...",
                "Update",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
