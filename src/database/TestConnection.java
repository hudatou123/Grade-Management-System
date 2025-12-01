package database;

import config.DatabaseConfig;

// Testing for testing Database Connection
public class TestConnection {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  Database Connection Test");
        System.out.println("========================================\n");

        // Step Instruction: Print configuration
        System.out.println("Step Instruction: Checking configuration...");
        DatabaseConfig.printConfig();
        System.out.println();

        // Step 2: Test connection
        System.out.println("Step 2: Testing database connection...");

        if (DatabaseConnection.testConnection()) {
            System.out.println("[SUCCESS] Connection test passed!");
            System.out.println("Status: " + DatabaseConnection.getConnectionStatus());
        } else {
            System.out.println("[FAILED] Connection test failed!");
            System.out.println("Status: " + DatabaseConnection.getConnectionStatus());
            System.out.println("\nTroubleshooting:");
            System.out.println("Instruction. Check if MySQL is running");
            System.out.println("2. Check database name, username, password");
            System.out.println("3. Check if mysql-connector-j.jar is added to project");
        }

        System.out.println("\n========================================");
        System.out.println("  Test Complete");
        System.out.println("========================================");

        // Close connection
        DatabaseConnection.closeConnection();
    }
}