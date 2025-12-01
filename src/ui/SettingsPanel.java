package ui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

// Settings Panel
public class SettingsPanel extends JFrame {

    private JTextArea textArea;

    public SettingsPanel() {
        setTitle("Settings - Project Information");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadReadme();
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top
        JLabel titleLabel = new JLabel("Project Information (README.md)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Center
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        closeBtn.setPreferredSize(new Dimension(100, 35));
        closeBtn.addActionListener(e -> dispose());
        buttonPanel.add(closeBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Read README.md from project root
    private void loadReadme() {
        try {
            String readmePath = "README.md";

            FileReader fileReader = new FileReader(readmePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            StringBuilder content = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
                content.append("\n");
            }

            bufferedReader.close();
            fileReader.close();

            textArea.setText(content.toString());
            textArea.setCaretPosition(0);  // Scroll to top

        } catch (Exception e) {
            textArea.setText("ERROR: Could not load README.md file.\n\n");
            textArea.append("Make sure README.md exists in the project root directory.\n\n");
            textArea.append("Error details:\n");
            textArea.append(e.getMessage());
        }
    }
}