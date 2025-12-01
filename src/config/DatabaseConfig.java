package config;

/*
 * Database Configuration Class
 * Stores all database connection information
 */
public class DatabaseConfig {

    // Database URL
    public static final String DB_URL = "jdbc:mysql://localhost:3306/student_grade_system";

    // Database username
    public static final String DB_USER = "root";

    // Database password
    public static final String DB_PASSWORD = "20010527HHGmysql";

    // JDBC Driver
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    // Full URL with parameters
    public static final String DB_URL_WITH_PARAMS =
            "jdbc:mysql://localhost:3306/student_grade_system" +
                    "?useSSL=false" +
                    "&serverTimezone=UTC" +
                    "&allowPublicKeyRetrieval=true";

// Print configuration
    public static void printConfig() {
        System.out.println("===== Database Configuration =====");
        System.out.println("URL: " + DB_URL);
        System.out.println("User: " + DB_USER);
        System.out.println("Password: " + (DB_PASSWORD.isEmpty() ? "(empty)" : "********"));
        System.out.println("Driver: " + JDBC_DRIVER);
        System.out.println("==================================");
    }

// Get database URL with parameters
    public static String getDbUrl() {
        return DB_URL_WITH_PARAMS;
    }
}