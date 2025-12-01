package dao;

import database.DatabaseConnection;
import models.Teacher;
import java.sql.*;
import java.util.ArrayList;

/*
 * Teacher Data Access Object
 * Handle all database operations for Teacher table
 */
public class TeacherDAO {

    /**
     * Add new teacher
     * @param teacher Teacher object
     * @return true if successful, false otherwise
     */
    public static boolean addTeacher(Teacher teacher) {
        String sql = "INSERT INTO teachers (user_id, employee_id, department, phone) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, teacher.getUserId());
            pstmt.setString(2, teacher.getEmployeeId());
            pstmt.setString(3, teacher.getDepartment());
            pstmt.setString(4, teacher.getPhone());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Get generated teacher_id
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    teacher.setTeacherId(rs.getInt(1));
                }
                System.out.println("[INFO] Teacher added: " + teacher.getFullName());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to add teacher");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get teacher by ID
     * @param teacherId Teacher ID
     * @return Teacher object or null
     */
    public static Teacher getTeacherById(int teacherId) {
        String sql = "SELECT t.*, u.username, u.full_name, u.email " +
                "FROM teachers t " +
                "JOIN users u ON t.user_id = u.user_id " +
                "WHERE t.teacher_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, teacherId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(rs.getInt("teacher_id"));
                teacher.setUserId(rs.getInt("user_id"));
                teacher.setEmployeeId(rs.getString("employee_id"));
                teacher.setFullName(rs.getString("full_name"));
                teacher.setDepartment(rs.getString("department"));
                teacher.setPhone(rs.getString("phone"));
                return teacher;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get teacher");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get teacher by user ID
     * @param userId User ID
     * @return Teacher object or null
     */
    public static Teacher getTeacherByUserId(int userId) {
        String sql = "SELECT t.*, u.username, u.full_name, u.email " +
                "FROM teachers t " +
                "JOIN users u ON t.user_id = u.user_id " +
                "WHERE t.user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(rs.getInt("teacher_id"));
                teacher.setUserId(rs.getInt("user_id"));
                teacher.setEmployeeId(rs.getString("employee_id"));
                teacher.setFullName(rs.getString("full_name"));
                teacher.setDepartment(rs.getString("department"));
                teacher.setPhone(rs.getString("phone"));
                return teacher;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get teacher");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get teacher by employee ID
     * @param employeeId Employee ID
     * @return Teacher object or null
     */
    public static Teacher getTeacherByEmployeeId(String employeeId) {
        String sql = "SELECT t.*, u.username, u.full_name, u.email " +
                "FROM teachers t " +
                "JOIN users u ON t.user_id = u.user_id " +
                "WHERE t.employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employeeId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(rs.getInt("teacher_id"));
                teacher.setUserId(rs.getInt("user_id"));
                teacher.setEmployeeId(rs.getString("employee_id"));
                teacher.setFullName(rs.getString("full_name"));
                teacher.setDepartment(rs.getString("department"));
                teacher.setPhone(rs.getString("phone"));
                return teacher;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get teacher");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get all teachers
     * @return ArrayList of Teacher objects
     */
    public static ArrayList<Teacher> getAllTeachers() {
        ArrayList<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT t.*, u.username, u.full_name, u.email " +
                "FROM teachers t " +
                "JOIN users u ON t.user_id = u.user_id " +
                "ORDER BY t.employee_id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(rs.getInt("teacher_id"));
                teacher.setUserId(rs.getInt("user_id"));
                teacher.setEmployeeId(rs.getString("employee_id"));
                teacher.setFullName(rs.getString("full_name"));
                teacher.setDepartment(rs.getString("department"));
                teacher.setPhone(rs.getString("phone"));
                teachers.add(teacher);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get teachers");
            e.printStackTrace();
        }

        return teachers;
    }

    /**
     * Update teacher information
     * @param teacher Teacher object with updated information
     * @return true if successful, false otherwise
     */
    public static boolean updateTeacher(Teacher teacher) {
        String sql = "UPDATE teachers SET employee_id = ?, department = ?, phone = ? " +
                "WHERE teacher_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, teacher.getEmployeeId());
            pstmt.setString(2, teacher.getDepartment());
            pstmt.setString(3, teacher.getPhone());
            pstmt.setInt(4, teacher.getTeacherId());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[INFO] Teacher updated: " + teacher.getFullName());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to update teacher");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Delete teacher
     * @param teacherId Teacher ID
     * @return true if successful, false otherwise
     */
    public static boolean deleteTeacher(int teacherId) {
        String sql = "DELETE FROM teachers WHERE teacher_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, teacherId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[INFO] Teacher deleted: ID " + teacherId);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to delete teacher");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Delete teacher by user ID
     * This is useful when deleting a user account
     * @param userId User ID
     * @return true if successful, false otherwise
     */
    public static boolean deleteTeacherByUserId(int userId) {
        String sql = "DELETE FROM teachers WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[INFO] Teacher deleted for user ID: " + userId);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to delete teacher");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get total number of teachers
     * @return Count of teachers
     */
    public static int getTeacherCount() {
        String sql = "SELECT COUNT(*) as count FROM teachers";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get teacher count");
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Search teachers by name or department
     * @param keyword Search keyword
     * @return ArrayList of matching teachers
     */
    public static ArrayList<Teacher> searchTeachers(String keyword) {
        ArrayList<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT t.*, u.username, u.full_name, u.email " +
                "FROM teachers t " +
                "JOIN users u ON t.user_id = u.user_id " +
                "WHERE u.full_name LIKE ? OR t.department LIKE ? OR t.employee_id LIKE ? " +
                "ORDER BY t.employee_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(rs.getInt("teacher_id"));
                teacher.setUserId(rs.getInt("user_id"));
                teacher.setEmployeeId(rs.getString("employee_id"));
                teacher.setFullName(rs.getString("full_name"));
                teacher.setDepartment(rs.getString("department"));
                teacher.setPhone(rs.getString("phone"));
                teachers.add(teacher);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to search teachers");
            e.printStackTrace();
        }

        return teachers;
    }
}