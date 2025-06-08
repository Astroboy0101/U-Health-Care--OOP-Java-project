package panels;

import java.awt.*;
import javax.swing.*;

public class HealthRecordPanel extends BasePanel {

    public HealthRecordPanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        // Add title
        add(createTitlePanel("My Health Record"), BorderLayout.NORTH);

        // Create main content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Personal Information Section
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel sectionLabel = createLabel("Personal Information");
        sectionLabel.setFont(new Font("Poppins", Font.BOLD, 18));
        contentPanel.add(sectionLabel, gbc);

        // Name
        gbc.gridy++;
        gbc.gridwidth = 1;
        contentPanel.add(createLabel("Name:"), gbc);

        gbc.gridx = 1;
        JTextField nameField = createTextField();
        nameField.setPreferredSize(new Dimension(200, 30));
        nameField.setEditable(false);
        nameField.setText("Beka Taka");
        contentPanel.add(nameField, gbc);

        // Age
        gbc.gridy++;
        gbc.gridx = 0;
        contentPanel.add(createLabel("Age:"), gbc);

        gbc.gridx = 1;
        JTextField ageField = createTextField();
        ageField.setPreferredSize(new Dimension(200, 30));
        ageField.setEditable(false);
        ageField.setText("25");
        contentPanel.add(ageField, gbc);

        // Blood Type
        gbc.gridy++;
        gbc.gridx = 0;
        contentPanel.add(createLabel("Blood Type:"), gbc);

        gbc.gridx = 1;
        JTextField bloodTypeField = createTextField();
        bloodTypeField.setPreferredSize(new Dimension(200, 30));
        bloodTypeField.setEditable(false);
        bloodTypeField.setText("O+");
        contentPanel.add(bloodTypeField, gbc);

        // Medical Information Section
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        JLabel medicalLabel = createLabel("Medical Information");
        medicalLabel.setFont(new Font("Poppins", Font.BOLD, 18));
        contentPanel.add(medicalLabel, gbc);

        // Allergies
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        contentPanel.add(createLabel("Allergies:"), gbc);

        gbc.gridx = 1;
        JTextArea allergiesArea = new JTextArea();
        allergiesArea.setFont(normalFont);
        allergiesArea.setLineWrap(true);
        allergiesArea.setWrapStyleWord(true);
        allergiesArea.setText("None");
        JScrollPane allergiesScroll = new JScrollPane(allergiesArea);
        allergiesScroll.setPreferredSize(new Dimension(200, 60));
        contentPanel.add(allergiesScroll, gbc);

        // Current Medications
        gbc.gridy++;
        gbc.gridx = 0;
        contentPanel.add(createLabel("Current Medications:"), gbc);

        gbc.gridx = 1;
        JTextArea medicationsArea = new JTextArea();
        medicationsArea.setFont(normalFont);
        medicationsArea.setLineWrap(true);
        medicationsArea.setWrapStyleWord(true);
        medicationsArea.setText("None");
        JScrollPane medicationsScroll = new JScrollPane(medicationsArea);
        medicationsScroll.setPreferredSize(new Dimension(200, 60));
        contentPanel.add(medicationsScroll, gbc);

        // Add update button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        JButton updateButton = createButton("Update Information");
        contentPanel.add(updateButton, gbc);

        // Add content panel to center
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setBackground(backgroundColor);
        wrapper.add(contentPanel);
        add(wrapper, BorderLayout.CENTER);
    }
}
