package ui;

import dao.StudentDAO;
import dao.GradeDAO;
import models.Student;
import models.Grade;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

// Report Panel
public class ReportPanel extends JFrame {

    private JComboBox<String> studentCombo;
    private JTextArea previewArea;

    public ReportPanel() {
        setTitle("Generate Report");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top
        JLabel titleLabel = new JLabel("Generate Grade Report", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Center
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);
        centerPanel.setBackground(Color.WHITE);

        JLabel selectLabel = new JLabel("Select Student:");
        selectLabel.setBounds(20, 20, 120, 25);
        centerPanel.add(selectLabel);

        studentCombo = new JComboBox<>();
        studentCombo.setBounds(140, 20, 420, 30);

        // Load students
        ArrayList<Student> students = StudentDAO.getAllStudents();
        for (Student s : students) {
            studentCombo.addItem(s.getStudentId() + " - " + s.getFullName());
        }
        studentCombo.addActionListener(e -> showPreview());
        centerPanel.add(studentCombo);

        // Preview
        JLabel previewLabel = new JLabel("Preview:");
        previewLabel.setBounds(20, 70, 100, 25);
        centerPanel.add(previewLabel);

        previewArea = new JTextArea();
        previewArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        previewArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(previewArea);
        scrollPane.setBounds(20, 100, 540, 250);
        centerPanel.add(scrollPane);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton exportButton = new JButton("Export to TXT");
        exportButton.setFont(new Font("Arial", Font.BOLD, 16));
        exportButton.setBackground(new Color(46, 204, 113));
        exportButton.setForeground(Color.BLACK);
        exportButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        exportButton.setPreferredSize(new Dimension(180, 40));
        exportButton.addActionListener(e -> exportToTXT());
        buttonPanel.add(exportButton);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.addActionListener(e -> dispose());
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Show initial preview
        if (studentCombo.getItemCount() > 0) {
            showPreview();
        }
    }

    private void showPreview() {
        String selected = (String) studentCombo.getSelectedItem();
        if (selected == null) return;

        int studentId = Integer.parseInt(selected.split(" - ")[0]);
        Student student = StudentDAO.getStudentById(studentId);
        ArrayList<Grade> grades = GradeDAO.getGradesByStudentId(studentId);

        String reportText = generateReportText(student, grades);
        previewArea.setText(reportText);
    }

    private String generateReportText(Student student, ArrayList<Grade> grades) {
        StringBuilder text = new StringBuilder();

        text.append("============================================\n");
        text.append("       STUDENT GRADE REPORT\n");
        text.append("============================================\n\n");

        text.append("Student Number: ").append(student.getStudentNumber()).append("\n");
        text.append("Name: ").append(student.getFullName()).append("\n");
        text.append("Email: ").append(student.getEmail() != null ? student.getEmail() : "N/A").append("\n");
        text.append("Phone: ").append(student.getPhone() != null ? student.getPhone() : "N/A").append("\n\n");

        text.append("--------------------------------------------\n");
        text.append("COURSE GRADES:\n");
        text.append("--------------------------------------------\n\n");

        if (grades.isEmpty()) {
            text.append("No grades recorded.\n\n");
        } else {
            double totalAverage = 0;

            for (Grade g : grades) {
                text.append("Course: ").append(g.getCourseName()).append("\n");
                text.append("Semester: ").append(g.getSemester()).append("\n");
                text.append("  - Assignment Score: ").append(g.getAssignmentScore()).append("\n");
                text.append("  - Midterm Score: ").append(g.getMidtermScore()).append("\n");
                text.append("  - Final Score: ").append(g.getFinalScore()).append("\n");
                text.append("  - Total Score: ").append(String.format("%.2f", g.getTotalScore())).append("\n");
                text.append("  - Letter Grade: ").append(g.getLetterGrade()).append("\n");
                text.append("\n");

                totalAverage += g.getTotalScore();
            }

            totalAverage = totalAverage / grades.size();

            text.append("--------------------------------------------\n");
            text.append("SUMMARY:\n");
            text.append("--------------------------------------------\n");
            text.append("Total Courses: ").append(grades.size()).append("\n");
            text.append("Overall Average: ").append(String.format("%.2f", totalAverage)).append("\n");

            String overallGrade;
            if (totalAverage >= 90) overallGrade = "A";
            else if (totalAverage >= 80) overallGrade = "B";
            else if (totalAverage >= 70) overallGrade = "C";
            else if (totalAverage >= 60) overallGrade = "D";
            else overallGrade = "F";

            text.append("Overall Grade: ").append(overallGrade).append("\n");
        }

        text.append("\n============================================\n");
        text.append("Generated: ").append(java.time.LocalDateTime.now().toString()).append("\n");
        text.append("============================================\n");

        return text.toString();
    }

    private void exportToTXT() {
        String selected = (String) studentCombo.getSelectedItem();
        if (selected == null) return;

        int studentId = Integer.parseInt(selected.split(" - ")[0]);
        Student student = StudentDAO.getStudentById(studentId);
        ArrayList<Grade> grades = GradeDAO.getGradesByStudentId(studentId);

        // Choose save location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report");
        fileChooser.setSelectedFile(new java.io.File(student.getStudentNumber() + "_Report.txt"));

        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Add .txt extension if not present
            if (!filePath.endsWith(".txt")) {
                filePath += ".txt";
            }

            try {
                // Create file writer
                FileWriter fileWriter = new FileWriter(filePath);
                PrintWriter printWriter = new PrintWriter(fileWriter);

                // Write report text
                String reportText = generateReportText(student, grades);
                printWriter.print(reportText);

                // Close writer
                printWriter.close();
                fileWriter.close();

                JOptionPane.showMessageDialog(this,
                        "Report saved successfully!\n" + filePath,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Failed to save report!\n" + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}