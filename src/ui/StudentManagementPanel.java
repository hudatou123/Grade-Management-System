package ui;

import dao.StudentDAO;
import models.Student;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// Student Management Panel
public class StudentManagementPanel extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public StudentManagementPanel() {
        setTitle("Student Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadStudents();
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Top panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Student Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel);

        topPanel.add(Box.createHorizontalStrut(200));

        topPanel.add(new JLabel("Search:"));
        searchField = new JTextField(15);
        topPanel.add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchStudents());
        topPanel.add(searchBtn);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Number", "First Name", "Last Name", "Email", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton addBtn = new JButton("Add");
        addBtn.setBackground(new Color(200, 255, 220));
        addBtn.setForeground(Color.BLACK);
        addBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        addBtn.addActionListener(e -> addStudent());
        buttonPanel.add(addBtn);

        JButton editBtn = new JButton("Edit");
        editBtn.setBackground(new Color(200, 230, 255));
        editBtn.setForeground(Color.BLACK);
        editBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        editBtn.addActionListener(e -> editStudent());
        buttonPanel.add(editBtn);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(new Color(255, 200, 200));
        deleteBtn.setForeground(Color.BLACK);
        deleteBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        deleteBtn.addActionListener(e -> deleteStudent());
        buttonPanel.add(deleteBtn);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadStudents());
        buttonPanel.add(refreshBtn);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> dispose());
        buttonPanel.add(backBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        ArrayList<Student> students = StudentDAO.getAllStudents();

        for (Student s : students) {
            Object[] row = {s.getStudentId(), s.getStudentNumber(), s.getFirstName(),
                    s.getLastName(), s.getEmail(), s.getPhone()};
            tableModel.addRow(row);
        }
    }

    private void searchStudents() {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            loadStudents();
            return;
        }

        tableModel.setRowCount(0);
        ArrayList<Student> students = StudentDAO.searchStudentsByName(keyword);

        for (Student s : students) {
            Object[] row = {s.getStudentId(), s.getStudentNumber(), s.getFirstName(),
                    s.getLastName(), s.getEmail(), s.getPhone()};
            tableModel.addRow(row);
        }

        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students found!");
        }
    }

    private void addStudent() {
        JDialog dialog = new JDialog(this, "Add Student", true);
        dialog.setSize(350, 330);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);

        JLabel lbl1 = new JLabel("Student Number:");
        lbl1.setBounds(20, 20, 120, 25);
        dialog.add(lbl1);
        JTextField field1 = new JTextField();
        field1.setBounds(140, 20, 180, 25);
        dialog.add(field1);

        JLabel lbl2 = new JLabel("First Name:");
        lbl2.setBounds(20, 55, 120, 25);
        dialog.add(lbl2);
        JTextField field2 = new JTextField();
        field2.setBounds(140, 55, 180, 25);
        dialog.add(field2);

        JLabel lbl3 = new JLabel("Last Name:");
        lbl3.setBounds(20, 90, 120, 25);
        dialog.add(lbl3);
        JTextField field3 = new JTextField();
        field3.setBounds(140, 90, 180, 25);
        dialog.add(field3);

        JLabel lbl4 = new JLabel("Email:");
        lbl4.setBounds(20, 125, 120, 25);
        dialog.add(lbl4);
        JTextField field4 = new JTextField();
        field4.setBounds(140, 125, 180, 25);
        dialog.add(field4);

        JLabel lbl5 = new JLabel("Phone:");
        lbl5.setBounds(20, 160, 120, 25);
        dialog.add(lbl5);
        JTextField field5 = new JTextField();
        field5.setBounds(140, 160, 180, 25);
        dialog.add(field5);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(80, 230, 80, 30);
        saveBtn.addActionListener(e -> {
            String number = field1.getText().trim();
            String firstName = field2.getText().trim();
            String lastName = field3.getText().trim();

            if (number.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill required fields!");
                return;
            }

            Student student = new Student();
            student.setStudentNumber(number);
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setEmail(field4.getText().trim());
            student.setPhone(field5.getText().trim());

            if (StudentDAO.addStudent(student)) {
                JOptionPane.showMessageDialog(dialog, "Student added successfully!");
                dialog.dispose();
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to add student!");
            }
        });
        dialog.add(saveBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(180, 230, 80, 30);
        cancelBtn.addActionListener(e -> dialog.dispose());
        dialog.add(cancelBtn);

        dialog.setVisible(true);
    }

    private void editStudent() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student!");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        String number = (String) tableModel.getValueAt(row, 1);
        String firstName = (String) tableModel.getValueAt(row, 2);
        String lastName = (String) tableModel.getValueAt(row, 3);
        String email = (String) tableModel.getValueAt(row, 4);
        String phone = (String) tableModel.getValueAt(row, 5);

        JDialog dialog = new JDialog(this, "Edit Student", true);
        dialog.setSize(350, 330);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);

        JLabel lbl1 = new JLabel("Student Number:");
        lbl1.setBounds(20, 20, 120, 25);
        dialog.add(lbl1);
        JTextField field1 = new JTextField(number);
        field1.setBounds(140, 20, 180, 25);
        dialog.add(field1);

        JLabel lbl2 = new JLabel("First Name:");
        lbl2.setBounds(20, 55, 120, 25);
        dialog.add(lbl2);
        JTextField field2 = new JTextField(firstName);
        field2.setBounds(140, 55, 180, 25);
        dialog.add(field2);

        JLabel lbl3 = new JLabel("Last Name:");
        lbl3.setBounds(20, 90, 120, 25);
        dialog.add(lbl3);
        JTextField field3 = new JTextField(lastName);
        field3.setBounds(140, 90, 180, 25);
        dialog.add(field3);

        JLabel lbl4 = new JLabel("Email:");
        lbl4.setBounds(20, 125, 120, 25);
        dialog.add(lbl4);
        JTextField field4 = new JTextField(email);
        field4.setBounds(140, 125, 180, 25);
        dialog.add(field4);

        JLabel lbl5 = new JLabel("Phone:");
        lbl5.setBounds(20, 160, 120, 25);
        dialog.add(lbl5);
        JTextField field5 = new JTextField(phone);
        field5.setBounds(140, 160, 180, 25);
        dialog.add(field5);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(80, 230, 80, 30);
        updateBtn.addActionListener(e -> {
            Student student = new Student();
            student.setStudentId(id);
            student.setStudentNumber(field1.getText().trim());
            student.setFirstName(field2.getText().trim());
            student.setLastName(field3.getText().trim());
            student.setEmail(field4.getText().trim());
            student.setPhone(field5.getText().trim());

            if (StudentDAO.updateStudent(student)) {
                JOptionPane.showMessageDialog(dialog, "Student updated successfully!");
                dialog.dispose();
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to update!");
            }
        });
        dialog.add(updateBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(180, 230, 80, 30);
        cancelBtn.addActionListener(e -> dialog.dispose());
        dialog.add(cancelBtn);

        dialog.setVisible(true);
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student!");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        String name = tableModel.getValueAt(row, 2) + " " + tableModel.getValueAt(row, 3);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete student: " + name + "?",
                "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (StudentDAO.deleteStudent(id)) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete!");
            }
        }
    }
}