package ui;

import dao.UserDAO;
import database.DatabaseConnection;
import models.User;
import javax.swing.*;
import java.awt.*;

// Login panel
public class LoginPanel extends JFrame {

    // Components
    private JRadioButton adminRadio;
    private JRadioButton teacherRadio;
    private ButtonGroup roleGroup;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox;
    private JButton loginButton;
    private JLabel statusLabel;

    public LoginPanel() {
        setTitle("Grade Management System");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createUI();
        checkDatabase();
    }

    private void createUI() {
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(500, 100));

        JLabel titleLabel = new JLabel("Welcome!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(236, 240, 241));

        // Role label
        JLabel roleLabel = new JLabel("Select Role:");
        roleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        roleLabel.setBounds(70, 30, 120, 25);
        formPanel.add(roleLabel);

        // Admin radio
        adminRadio = new JRadioButton("Administrator");
        adminRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        adminRadio.setBackground(new Color(236, 240, 241));
        adminRadio.setBounds(200, 30, 130, 25);
        adminRadio.setSelected(true);
        formPanel.add(adminRadio);

        // Teacher radio
        teacherRadio = new JRadioButton("Teacher");
        teacherRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        teacherRadio.setBackground(new Color(236, 240, 241));
        teacherRadio.setBounds(340, 30, 100, 25);
        formPanel.add(teacherRadio);

        // Group radios
        roleGroup = new ButtonGroup();
        roleGroup.add(adminRadio);
        roleGroup.add(teacherRadio);

        // Username label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setBounds(70, 85, 100, 20);
        formPanel.add(usernameLabel);

        // Username field
        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBounds(70, 110, 360, 40);
        formPanel.add(usernameField);

        // Password label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setBounds(70, 170, 100, 20);
        formPanel.add(passwordLabel);

        // Password field
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBounds(70, 195, 360, 40);
        formPanel.add(passwordField);

        // Show password checkbox
        showPasswordCheckBox = new JCheckBox("Show password");
        showPasswordCheckBox.setFont(new Font("Arial", Font.PLAIN, 12));
        showPasswordCheckBox.setBackground(new Color(236, 240, 241));
        showPasswordCheckBox.setBounds(70, 245, 150, 25);
        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });
        formPanel.add(showPasswordCheckBox);

        // Login button
        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBounds(150, 295, 200, 50);
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));  // Black border added
        loginButton.addActionListener(e -> login());
        formPanel.add(loginButton);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setPreferredSize(new Dimension(500, 50));

        statusLabel = new JLabel("Checking database...");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerPanel.add(statusLabel);

        // Assemble
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Press Enter to login
        passwordField.addActionListener(e -> login());
    }

    private void checkDatabase() {
        if (DatabaseConnection.testConnection()) {
            statusLabel.setText("✓ Database Connected");
            statusLabel.setForeground(new Color(39, 174, 96));
        } else {
            statusLabel.setText("✗ Database Connection Failed");
            statusLabel.setForeground(Color.RED);
            loginButton.setEnabled(false);
        }
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Check if empty
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username!");
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter password!");
            return;
        }

        // Show logging in status
        statusLabel.setText("Logging in...");
        statusLabel.setForeground(Color.BLUE);
        loginButton.setEnabled(false);

        // Try to login
        User user = UserDAO.login(username, password);

        if (user != null) {
            // Check role
            boolean isAdminSelected = adminRadio.isSelected();
            boolean isUserAdmin = user.isAdmin();

            if ((isAdminSelected && isUserAdmin) || (!isAdminSelected && !isUserAdmin)) {
                // Login success
                statusLabel.setText("✓ Login Successful!");
                statusLabel.setForeground(new Color(39, 174, 96));

                dispose();

                // Open correct panel
                if (user.isAdmin()) {
                    new AdminPanel(user).setVisible(true);
                } else {
                    new TeacherPanel(user).setVisible(true);
                }
            } else {
                // Wrong role
                String msg = "This account is not a " + (isAdminSelected ? "Administrator" : "Teacher");
                JOptionPane.showMessageDialog(this, msg, "Role Mismatch", JOptionPane.ERROR_MESSAGE);
                loginButton.setEnabled(true);
                checkDatabase();
            }
        } else {
            // Login failed
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            loginButton.setEnabled(true);
            checkDatabase();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new LoginPanel().setVisible(true));
    }
}