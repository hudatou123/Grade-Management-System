package ui;

import models.User;
import javax.swing.*;
import java.awt.*;

// Teacher Panel
public class TeacherPanel extends JFrame {

    private User currentUser;

    public TeacherPanel(User user) {
        this.currentUser = user;

        setTitle("Teacher Panel - " + user.getFullName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(34, 139, 34));
        headerPanel.setPreferredSize(new Dimension(800, 80));

        JLabel headerLabel = new JLabel("Teacher Dashboard");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center - Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JButton btn1 = new JButton("View Students");
        btn1.setFont(new Font("Arial", Font.BOLD, 18));
        btn1.setBackground(new Color(200, 230, 255));
        btn1.setForeground(Color.BLACK);
        btn1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        btn1.addActionListener(e -> new StudentManagementPanel().setVisible(true));

        JButton btn2 = new JButton("Manage Grades");
        btn2.setFont(new Font("Arial", Font.BOLD, 18));
        btn2.setBackground(new Color(200, 255, 220));
        btn2.setForeground(Color.BLACK);
        btn2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        btn2.addActionListener(e -> new GradeManagementPanel().setVisible(true));

        JButton btn3 = new JButton("Generate Reports");
        btn3.setFont(new Font("Arial", Font.BOLD, 18));
        btn3.setBackground(new Color(255, 230, 200));
        btn3.setForeground(Color.BLACK);
        btn3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        btn3.addActionListener(e -> new ReportPanel().setVisible(true));

        JButton btn4 = new JButton("Logout");
        btn4.setFont(new Font("Arial", Font.BOLD, 18));
        btn4.setBackground(new Color(255, 200, 200));
        btn4.setForeground(Color.BLACK);
        btn4.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        btn4.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Logout?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginPanel().setVisible(true);
            }
        });

        buttonPanel.add(btn1);
        buttonPanel.add(btn2);
        buttonPanel.add(btn3);
        buttonPanel.add(btn4);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 240, 240));
        footerPanel.setPreferredSize(new Dimension(800, 40));

        JLabel statusLabel = new JLabel("Logged in as: " + currentUser.getFullName() + " (Teacher)");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setForeground(Color.BLACK);
        footerPanel.add(statusLabel);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
