package dao;

import database.DatabaseConnection;
import models.Grade;
import java.sql.*;
import java.util.ArrayList;

/*
 * Grade Data Access Object
 * Handle all database operations for Grade table
 */
public class GradeDAO {

    /**
     * Add new grade
     * @param grade Grade object
     * @return true if successful, false otherwise
     */
    public static boolean addGrade(Grade grade) {
        String sql = "INSERT INTO grades (student_id, course_name, assignment_score, " +
                "midterm_score, final_score, total_score, letter_grade, semester) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, grade.getStudentId());
            pstmt.setString(2, grade.getCourseName());
            pstmt.setDouble(3, grade.getAssignmentScore());
            pstmt.setDouble(4, grade.getMidtermScore());
            pstmt.setDouble(5, grade.getFinalScore());
            pstmt.setDouble(6, grade.getTotalScore());
            pstmt.setString(7, grade.getLetterGrade());
            pstmt.setString(8, grade.getSemester());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Get generated grade_id
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    grade.setGradeId(rs.getInt(1));
                }
                System.out.println("[INFO] Grade added for student ID: " + grade.getStudentId());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to add grade");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get grade by ID
     * @param gradeId Grade ID
     * @return Grade object or null
     */
    public static Grade getGradeById(int gradeId) {
        String sql = "SELECT g.*, s.first_name, s.last_name " +
                "FROM grades g " +
                "JOIN students s ON g.student_id = s.student_id " +
                "WHERE g.grade_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gradeId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Grade grade = new Grade();
                grade.setGradeId(rs.getInt("grade_id"));
                grade.setStudentId(rs.getInt("student_id"));
                grade.setStudentName(rs.getString("first_name") + " " + rs.getString("last_name"));
                grade.setCourseName(rs.getString("course_name"));
                grade.setAssignmentScore(rs.getDouble("assignment_score"));
                grade.setMidtermScore(rs.getDouble("midterm_score"));
                grade.setFinalScore(rs.getDouble("final_score"));
                grade.setSemester(rs.getString("semester"));
                return grade;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get grade");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get all grades for a specific student
     * @param studentId Student ID
     * @return ArrayList of Grade objects
     */
    public static ArrayList<Grade> getGradesByStudentId(int studentId) {
        ArrayList<Grade> grades = new ArrayList<>();
        String sql = "SELECT g.*, s.first_name, s.last_name " +
                "FROM grades g " +
                "JOIN students s ON g.student_id = s.student_id " +
                "WHERE g.student_id = ? " +
                "ORDER BY g.semester, g.course_name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Grade grade = new Grade();
                grade.setGradeId(rs.getInt("grade_id"));
                grade.setStudentId(rs.getInt("student_id"));
                grade.setStudentName(rs.getString("first_name") + " " + rs.getString("last_name"));
                grade.setCourseName(rs.getString("course_name"));
                grade.setAssignmentScore(rs.getDouble("assignment_score"));
                grade.setMidtermScore(rs.getDouble("midterm_score"));
                grade.setFinalScore(rs.getDouble("final_score"));
                grade.setSemester(rs.getString("semester"));
                grades.add(grade);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get grades");
            e.printStackTrace();
        }

        return grades;
    }

    /**
     * Get all grades
     * @return ArrayList of Grade objects
     */
    public static ArrayList<Grade> getAllGrades() {
        ArrayList<Grade> grades = new ArrayList<>();
        String sql = "SELECT g.*, s.first_name, s.last_name, s.student_number " +
                "FROM grades g " +
                "JOIN students s ON g.student_id = s.student_id " +
                "ORDER BY s.student_number, g.semester, g.course_name";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Grade grade = new Grade();
                grade.setGradeId(rs.getInt("grade_id"));
                grade.setStudentId(rs.getInt("student_id"));
                grade.setStudentName(rs.getString("first_name") + " " + rs.getString("last_name"));
                grade.setCourseName(rs.getString("course_name"));
                grade.setAssignmentScore(rs.getDouble("assignment_score"));
                grade.setMidtermScore(rs.getDouble("midterm_score"));
                grade.setFinalScore(rs.getDouble("final_score"));
                grade.setSemester(rs.getString("semester"));
                grades.add(grade);
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get all grades");
            e.printStackTrace();
        }

        return grades;
    }

    /**
     * Update grade
     * @param grade Grade object with updated information
     * @return true if successful, false otherwise
     */
    public static boolean updateGrade(Grade grade) {
        String sql = "UPDATE grades SET assignment_score = ?, midterm_score = ?, " +
                "final_score = ?, total_score = ?, letter_grade = ?, semester = ? " +
                "WHERE grade_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, grade.getAssignmentScore());
            pstmt.setDouble(2, grade.getMidtermScore());
            pstmt.setDouble(3, grade.getFinalScore());
            pstmt.setDouble(4, grade.getTotalScore());
            pstmt.setString(5, grade.getLetterGrade());
            pstmt.setString(6, grade.getSemester());
            pstmt.setInt(7, grade.getGradeId());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[INFO] Grade updated: ID " + grade.getGradeId());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to update grade");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Delete grade
     * @param gradeId Grade ID
     * @return true if successful, false otherwise
     */
    public static boolean deleteGrade(int gradeId) {
        String sql = "DELETE FROM grades WHERE grade_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gradeId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[INFO] Grade deleted: ID " + gradeId);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to delete grade");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Calculate average score for a student
     * @param studentId Student ID
     * @return Average score
     */
    public static double calculateAverageScore(int studentId) {
        String sql = "SELECT AVG(total_score) as average FROM grades WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("average");
            }

        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to calculate average");
            e.printStackTrace();
        }

        return 0.0;
    }
}