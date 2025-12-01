package dao;

import config.DatabaseConfig;
import database.DatabaseConnection;
import models.User;
import java.sql.*;
import java.util.ArrayList;

/*
 * User Data Access Object
 * Handles all database operations for User table
 */
public class UserDAO {

    /**
     * Authenticate user login
     * @param username Username
     * @param password Password
     * @return User object if login successful, null otherwise
     */
    public static User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));

                System.out.println("[INFO] User logged in: " + username);
                return user;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Login failed");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Add new user
     * @param user User object
     * @return true if successful, false otherwise
     */
    public static boolean addUser(User user) {
        String sql = "INSERT INTO users (username, password, full_name, role, email) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFullName());
            pstmt.setString(4, user.getRole());
            pstmt.setString(5, user.getEmail());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Get generated user_id
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                }
                System.out.println("[INFO] User added: " + user.getUsername());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to add user");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get user by ID
     * @param userId User ID
     * @return User object or null
     */
    public static User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                return user;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get user");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get all users
     * @return ArrayList of User objects
     */
    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY user_id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get users");
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Update user
     * @param user User object with updated information
     * @return true if successful, false otherwise
     */
    public static boolean updateUser(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, " +
                "full_name = ?, role = ?, email = ? WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFullName());
            pstmt.setString(4, user.getRole());
            pstmt.setString(5, user.getEmail());
            pstmt.setInt(6, user.getUserId());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[INFO] User updated: " + user.getUsername());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to update user");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Delete user
     * @param userId User ID
     * @return true if successful, false otherwise
     */
    public static boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[INFO] User deleted: ID " + userId);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to delete user");
            e.printStackTrace();
        }

        return false;
    }
}