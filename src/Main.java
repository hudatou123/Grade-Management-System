import ui.LoginPanel;
import database.DatabaseConnection;
import javax.swing.*;

// Main Entry Point
public class Main {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" Grade Management System ");
        System.out.println(" Version 1.0 ");
        System.out.println("========================================");

        System.out.println("\nChecking database connection...");
        if (DatabaseConnection.testConnection()) {
            System.out.println("[SUCCESS] Database connected!");
        } else {
            System.out.println("[ERROR] Database connection failed!");
            System.out.println("Please check:");
            System.out.println("  1. MySQL is running");
            System.out.println("  2. Database 'student_grade_system' exists");
            System.out.println("  3. Username and password are correct");
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("[WARNING] Could not set system look and feel");
        }

        System.out.println("\nStarting login window...");
        SwingUtilities.invokeLater(() -> {
            new LoginPanel().setVisible(true);
        });

        System.out.println("Application started successfully!");
        System.out.println("========================================\n");
    }
}