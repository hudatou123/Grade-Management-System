package ui;

import dao.GradeDAO;
import dao.StudentDAO;
import models.Grade;
import models.Student;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// Grade Management Panel
public class GradeManagementPanel extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterCombo;

    public GradeManagementPanel() {
        setTitle("Grade Management");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadGrades();
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Top panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Grade Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel);

        topPanel.add(Box.createHorizontalStrut(150));

        topPanel.add(new JLabel("Filter by Student:"));
        filterCombo = new JComboBox<>();
        filterCombo.addItem("All Students");

        ArrayList<Student> students = StudentDAO.getAllStudents();
        for (Student s : students) {
            filterCombo.addItem(s.getStudentId() + " - " + s.getFullName());
        }

        filterCombo.addActionListener(e -> filterGrades());
        topPanel.add(filterCombo);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Grade ID", "Student", "Course", "Assignment", "Midterm", "Final", "Total", "Letter"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton addBtn = new JButton("Add Grade");
        addBtn.setBackground(new Color(200, 255, 220));
        addBtn.setForeground(Color.BLACK);
        addBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        addBtn.addActionListener(e -> addGrade());
        buttonPanel.add(addBtn);

        JButton editBtn = new JButton("Edit Grade");
        editBtn.setBackground(new Color(200, 230, 255));
        editBtn.setForeground(Color.BLACK);
        editBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        editBtn.addActionListener(e -> editGrade());
        buttonPanel.add(editBtn);

        JButton deleteBtn = new JButton("Delete Grade");
        deleteBtn.setBackground(new Color(255, 200, 200));
        deleteBtn.setForeground(Color.BLACK);
        deleteBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        deleteBtn.addActionListener(e -> deleteGrade());
        buttonPanel.add(deleteBtn);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadGrades());
        buttonPanel.add(refreshBtn);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> dispose());
        buttonPanel.add(backBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadGrades() {
        tableModel.setRowCount(0);
        ArrayList<Grade> grades = GradeDAO.getAllGrades();

        for (Grade g : grades) {
            Object[] row = {
                    g.getGradeId(),
                    g.getStudentName(),
                    g.getCourseName(),
                    g.getAssignmentScore(),
                    g.getMidtermScore(),
                    g.getFinalScore(),
                    String.format("%.2f", g.getTotalScore()),
                    g.getLetterGrade()
            };
            tableModel.addRow(row);
        }
    }

    private void filterGrades() {
        String selected = (String) filterCombo.getSelectedItem();

        if (selected.equals("All Students")) {
            loadGrades();
            return;
        }

        int studentId = Integer.parseInt(selected.split(" - ")[0]);

        tableModel.setRowCount(0);
        ArrayList<Grade> grades = GradeDAO.getGradesByStudentId(studentId);

        for (Grade g : grades) {
            Object[] row = {
                    g.getGradeId(),
                    g.getStudentName(),
                    g.getCourseName(),
                    g.getAssignmentScore(),
                    g.getMidtermScore(),
                    g.getFinalScore(),
                    String.format("%.2f", g.getTotalScore()),
                    g.getLetterGrade()
            };
            tableModel.addRow(row);
        }
    }

    private void addGrade() {
        JDialog dialog = new JDialog(this, "Add Grade", true);
        dialog.setSize(400, 380);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);

        JLabel lbl1 = new JLabel("Student:");
        lbl1.setBounds(20, 20, 100, 25);
        dialog.add(lbl1);

        JComboBox<String> studentCombo = new JComboBox<>();
        ArrayList<Student> students = StudentDAO.getAllStudents();
        for (Student s : students) {
            studentCombo.addItem(s.getStudentId() + " - " + s.getFullName());
        }
        studentCombo.setBounds(130, 20, 240, 25);
        dialog.add(studentCombo);

        JLabel lbl2 = new JLabel("Course:");
        lbl2.setBounds(20, 60, 100, 25);
        dialog.add(lbl2);
        JTextField field2 = new JTextField();
        field2.setBounds(130, 60, 240, 25);
        dialog.add(field2);

        JLabel lbl3 = new JLabel("Assignment:");
        lbl3.setBounds(20, 100, 100, 25);
        dialog.add(lbl3);
        JTextField field3 = new JTextField();
        field3.setBounds(130, 100, 240, 25);
        dialog.add(field3);

        JLabel lbl4 = new JLabel("Midterm:");
        lbl4.setBounds(20, 140, 100, 25);
        dialog.add(lbl4);
        JTextField field4 = new JTextField();
        field4.setBounds(130, 140, 240, 25);
        dialog.add(field4);

        JLabel lbl5 = new JLabel("Final:");
        lbl5.setBounds(20, 180, 100, 25);
        dialog.add(lbl5);
        JTextField field5 = new JTextField();
        field5.setBounds(130, 180, 240, 25);
        dialog.add(field5);

        JLabel lbl6 = new JLabel("Semester:");
        lbl6.setBounds(20, 220, 100, 25);
        dialog.add(lbl6);
        JTextField field6 = new JTextField("2024-Fall");
        field6.setBounds(130, 220, 240, 25);
        dialog.add(field6);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(100, 280, 80, 30);
        saveBtn.addActionListener(e -> {
            try {
                String studentStr = (String) studentCombo.getSelectedItem();
                int studentId = Integer.parseInt(studentStr.split(" - ")[0]);
                String studentName = studentStr.split(" - ")[1];

                String course = field2.getText().trim();
                double assignment = Double.parseDouble(field3.getText().trim());
                double midterm = Double.parseDouble(field4.getText().trim());
                double finalScore = Double.parseDouble(field5.getText().trim());
                String semester = field6.getText().trim();

                if (course.isEmpty() || semester.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill all fields!");
                    return;
                }

                if (assignment < 0 || assignment > 100 || midterm < 0 || midterm > 100 ||
                        finalScore < 0 || finalScore > 100) {
                    JOptionPane.showMessageDialog(dialog, "Scores must be 0-100!");
                    return;
                }

                Grade grade = new Grade();
                grade.setStudentId(studentId);
                grade.setStudentName(studentName);
                grade.setCourseName(course);
                grade.setAssignmentScore(assignment);
                grade.setMidtermScore(midterm);
                grade.setFinalScore(finalScore);
                grade.setSemester(semester);

                if (GradeDAO.addGrade(grade)) {
                    JOptionPane.showMessageDialog(dialog, "Grade added successfully!");
                    dialog.dispose();
                    loadGrades();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to add grade!");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers!");
            }
        });
        dialog.add(saveBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(200, 280, 80, 30);
        cancelBtn.addActionListener(e -> dialog.dispose());
        dialog.add(cancelBtn);

        dialog.setVisible(true);
    }

    private void editGrade() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a grade!");
            return;
        }

        int gradeId = (int) tableModel.getValueAt(row, 0);
        String studentName = (String) tableModel.getValueAt(row, 1);
        String course = (String) tableModel.getValueAt(row, 2);
        double assignment = (double) tableModel.getValueAt(row, 3);
        double midterm = (double) tableModel.getValueAt(row, 4);
        double finalScore = (double) tableModel.getValueAt(row, 5);

        JDialog dialog = new JDialog(this, "Edit Grade", true);
        dialog.setSize(400, 320);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);

        JLabel lbl1 = new JLabel("Student:");
        lbl1.setBounds(20, 20, 100, 25);
        dialog.add(lbl1);
        JLabel studentLabel = new JLabel(studentName);
        studentLabel.setBounds(130, 20, 240, 25);
        dialog.add(studentLabel);

        JLabel lbl2 = new JLabel("Course:");
        lbl2.setBounds(20, 50, 100, 25);
        dialog.add(lbl2);
        JLabel courseLabel = new JLabel(course);
        courseLabel.setBounds(130, 50, 240, 25);
        dialog.add(courseLabel);

        JLabel lbl3 = new JLabel("Assignment:");
        lbl3.setBounds(20, 85, 100, 25);
        dialog.add(lbl3);
        JTextField field3 = new JTextField(String.valueOf(assignment));
        field3.setBounds(130, 85, 240, 25);
        dialog.add(field3);

        JLabel lbl4 = new JLabel("Midterm:");
        lbl4.setBounds(20, 120, 100, 25);
        dialog.add(lbl4);
        JTextField field4 = new JTextField(String.valueOf(midterm));
        field4.setBounds(130, 120, 240, 25);
        dialog.add(field4);

        JLabel lbl5 = new JLabel("Final:");
        lbl5.setBounds(20, 155, 100, 25);
        dialog.add(lbl5);
        JTextField field5 = new JTextField(String.valueOf(finalScore));
        field5.setBounds(130, 155, 240, 25);
        dialog.add(field5);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(100, 220, 80, 30);
        updateBtn.addActionListener(e -> {
            try {
                double newAssignment = Double.parseDouble(field3.getText().trim());
                double newMidterm = Double.parseDouble(field4.getText().trim());
                double newFinal = Double.parseDouble(field5.getText().trim());

                if (newAssignment < 0 || newAssignment > 100 || newMidterm < 0 ||
                        newMidterm > 100 || newFinal < 0 || newFinal > 100) {
                    JOptionPane.showMessageDialog(dialog, "Scores must be 0-100!");
                    return;
                }

                Grade grade = GradeDAO.getGradeById(gradeId);
                if (grade != null) {
                    grade.setAssignmentScore(newAssignment);
                    grade.setMidtermScore(newMidterm);
                    grade.setFinalScore(newFinal);

                    if (GradeDAO.updateGrade(grade)) {
                        JOptionPane.showMessageDialog(dialog, "Grade updated successfully!");
                        dialog.dispose();
                        loadGrades();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Failed to update!");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers!");
            }
        });
        dialog.add(updateBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(200, 220, 80, 30);
        cancelBtn.addActionListener(e -> dialog.dispose());
        dialog.add(cancelBtn);

        dialog.setVisible(true);
    }

    private void deleteGrade() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a grade!");
            return;
        }

        int gradeId = (int) tableModel.getValueAt(row, 0);
        String studentName = (String) tableModel.getValueAt(row, 1);
        String course = (String) tableModel.getValueAt(row, 2);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete grade for " + studentName + " in " + course + "?",
                "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (GradeDAO.deleteGrade(gradeId)) {
                JOptionPane.showMessageDialog(this, "Grade deleted successfully!");
                loadGrades();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete!");
            }
        }
    }
}
