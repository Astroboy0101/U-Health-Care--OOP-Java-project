package panels;

import java.awt.*;
import javax.swing.*;

public class BasePanel extends JPanel {

    protected Color primaryColor = new Color(0, 102, 68);
    protected Color backgroundColor = Color.WHITE;
    protected Font titleFont = new Font("Poppins", Font.BOLD, 24);
    protected Font normalFont = new Font("Poppins", Font.PLAIN, 14);

    public BasePanel() {
        setBackground(backgroundColor);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout());
    }

    protected JPanel createTitlePanel(String title) {
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(backgroundColor);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(primaryColor);

        titlePanel.add(titleLabel);
        return titlePanel;
    }

    protected JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        button.setFont(normalFont);
        button.setFocusPainted(false);
        return button;
    }

    protected JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(normalFont);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }

    protected JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(normalFont);
        return label;
    }
}
