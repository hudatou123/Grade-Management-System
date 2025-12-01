package dao;

import models.Student;
import models.Grade;
import models.Teacher;
import java.util.ArrayList;

// Just for test
public class TestAllDAO {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  Testing All DAO Classes");
        System.out.println("========================================\n");

        // Test StudentDAO
        System.out.println("=== Test StudentDAO ===");
        ArrayList<Student> students = StudentDAO.getAllStudents();
        System.out.println("Total students: " + students.size());
        for (Student s : students) {
            System.out.println("  - " + s.getStudentNumber() + " | " + s.getFullName());
        }

        // Test TeacherDAO
        System.out.println("\n=== Test TeacherDAO ===");
        ArrayList<Teacher> teachers = TeacherDAO.getAllTeachers();
        System.out.println("Total teachers: " + teachers.size());
        for (Teacher t : teachers) {
            System.out.println("  - " + t.getEmployeeId() +
                    " | " + t.getFullName() +
                    " | " + t.getDepartment());
        }

        // Search teachers
        System.out.println("\nSearch for 'Computer':");
        ArrayList<Teacher> searchResults = TeacherDAO.searchTeachers("Computer");
        for (Teacher t : searchResults) {
            System.out.println("  - Found: " + t.getFullName() + " in " + t.getDepartment());
        }

        // Test GradeDAO
        System.out.println("\n=== Test GradeDAO ===");
        ArrayList<Grade> allGrades = GradeDAO.getAllGrades();
        System.out.println("Total grades: " + allGrades.size());
        for (Grade g : allGrades) {
            System.out.println("  - " + g.getStudentName() +
                    " | " + g.getCourseName() +
                    " | " + g.getTotalScore() +
                    " (" + g.getLetterGrade() + ")");
        }

        // Calculate average
        double average = GradeDAO.calculateAverageScore(1);
        System.out.println("\nAverage score for student 1: " + String.format("%.2f", average));

        System.out.println("\n========================================");
        System.out.println("  All Tests Complete");
        System.out.println("========================================");
    }
}
