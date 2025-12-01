package database;

import config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Database Connection Manager
 * Handle connecting to and disconnecting from the database
 */
public class DatabaseConnection {

    // Store the single connection instance
    private static Connection connection = null;

//Get database connection (Singleton pattern)
    public static Connection getConnection() {
        try {

            // Check if connection exists and is not closed
            if (connection == null || connection.isClosed()) {

                // Step Instruction: Load JDBC Driver
                Class.forName(DatabaseConfig.JDBC_DRIVER);
                System.out.println("[INFO] JDBC Driver loaded successfully");

                // Step 2: Establish connection
                connection = DriverManager.getConnection(
                        DatabaseConfig.getDbUrl(),
                        DatabaseConfig.DB_USER,
                        DatabaseConfig.DB_PASSWORD
                );

                System.out.println("[SUCCESS] Database connected successfully!");
                System.out.println("[INFO] Connected to: " + DatabaseConfig.DB_URL);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR] JDBC Driver not found!");
            System.err.println("[ERROR] Make sure mysql-connector-j.jar is in your project");
            e.printStackTrace();

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to connect to database!");
            System.err.println("[ERROR] Check your database configuration:");
            System.err.println("  - Is MySQL running?");
            System.err.println("  - Is the database name correct?");
            System.err.println("  - Is the username/password correct?");
            e.printStackTrace();
        }

        return connection;
    }

// Test if database connection is working
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

// Close database connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[INFO] Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to close connection");
            e.printStackTrace();
        }
    }

// Get connection status as a string
    public static String getConnectionStatus() {
        if (testConnection()) {
            return "Connected";
        } else {
            return "Disconnected";
        }
    }
}