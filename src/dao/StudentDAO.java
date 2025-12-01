package dao;

import database.DatabaseConnection;
import models.Student;
import java.sql.*;
import java.util.ArrayList;

/*
 * Student Data Access Object
 * Handle all database operations for Student table
 */
public class StudentDAO {

    /**
     * Add new student
     * @param student Student object
     * @return true if successful, false otherwise
     */
    public static boolean addStudent(Student student) {
        String sql = "INSERT INTO students (student_number, first_name, last_name, email, phone, address) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, student.getStudentNumber());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPhone());
            pstmt.setString(6, student.getAddress());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Get generated student_id
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    student.setStudentId(rs.getInt(1));
                }
                System.out.println("[INFO] Student added: " + student.getFullName());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to add student");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get student by ID
     * @param studentId Student ID
     * @return Student object or null
     */
    public static Student getStudentById(int studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setStudentNumber(rs.getString("student_number"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setAddress(rs.getString("address"));
                return student;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get student");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get student by student number
     * @param studentNumber Student number
     * @return Student object or null
     */
    public static Student getStudentByNumber(String studentNumber) {
        String sql = "SELECT * FROM students WHERE student_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setStudentNumber(rs.getString("student_number"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setAddress(rs.getString("address"));
                return student;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get student");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get all students
     * @return ArrayList of Student objects
     */
    public static ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY student_number";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setStudentNumber(rs.getString("student_number"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setAddress(rs.getString("address"));
                students.add(student);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get students");
            e.printStackTrace();
        }

        return students;
    }

    /**
     * Search students by name
     * @param name Name to search (first name or last name)
     * @return ArrayList of matching students
     */
    public static ArrayList<Student> searchStudentsByName(String name) {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE first_name LIKE ? OR last_name LIKE ? " +
                "ORDER BY student_number";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + name + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setStudentNumber(rs.getString("student_number"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setAddress(rs.getString("address"));
                students.add(student);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to search students");
            e.printStackTrace();
        }

        return students;
    }

    /**
     * Update student information
     * @param student Student object with updated information
     * @return true if successful, false otherwise
     */
    public static boolean updateStudent(Student student) {
        String sql = "UPDATE students SET student_number = ?, first_name = ?, last_name = ?, " +
                "email = ?, phone = ?, address = ? WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getStudentNumber());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPhone());
            pstmt.setString(6, student.getAddress());
            pstmt.setInt(7, student.getStudentId());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[INFO] Student updated: " + student.getFullName());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to update student");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Delete student
     * @param studentId Student ID
     * @return true if successful, false otherwise
     */
    public static boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[INFO] Student deleted: ID " + studentId);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to delete student");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get total number of students
     * @return Count of students
     */
    public static int getStudentCount() {
        String sql = "SELECT COUNT(*) as count FROM students";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get student count");
            e.printStackTrace();
        }

        return 0;
    }
}