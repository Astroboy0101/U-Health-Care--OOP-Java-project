package panels;

import java.awt.*;
import java.net.URI;
import javax.swing.*;

public class AboutUsPanel extends BasePanel {

    public AboutUsPanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        // Add title
        add(createTitlePanel("About Us"), BorderLayout.NORTH);

        // Create main content panel with scroll
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(backgroundColor);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Project Information
        addSection(contentPanel, "Project Information", new String[]{
            "University Health Management System",
            "Department of Biomedical Engineering",
            "Object-Oriented Programming (Java) Project",
            "Submitted to: Mr. Bemnet",
            "By 2nd Year Biomedical Students"
        });

        // Team Members
        addSection(contentPanel, "Team Members", new String[]{
            "• Kena Fayera          UGR/5643/16",
            "• Sewyishal Netsanet   UGR/6276/16",
            "• Yisakor Tamirat      UGR/0563/16",
            "• Semere Hailu         UGR/4915/16",
            "• Leulekal Nahusenay  UGR/8263/16"
        });

        // Features
        addSection(contentPanel, "Key Features", new String[]{
            "• Multi-user system (Admin, Doctor, Patient)",
            "• Appointment Management",
            "• Health Records Management",
            "• Department Management",
            "• Prescription Management",
            "• System Reports"
        });

        // Support Section
        JPanel supportPanel = new JPanel();
        supportPanel.setBackground(backgroundColor);
        supportPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton coffeeButton = createButton("Buy Me a Coffee ☕");
        coffeeButton.setPreferredSize(new Dimension(200, 40));
        coffeeButton.addActionListener(e -> openBuyMeCoffee());

        supportPanel.add(coffeeButton);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(supportPanel);

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addSection(JPanel panel, String title, String[] items) {
        panel.add(Box.createVerticalStrut(20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        titleLabel.setForeground(primaryColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createVerticalStrut(10));

        for (String item : items) {
            JLabel itemLabel = new JLabel(item);
            itemLabel.setFont(normalFont);
            itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(itemLabel);
            panel.add(Box.createVerticalStrut(5));
        }
    }

    private void openBuyMeCoffee() {
        try {
            Desktop.getDesktop().browse(new URI("https://buymeacoffee.com/fayerakenaf"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not open the link. Please visit https://buymeacoffee.com/fayerakenaf manually.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
