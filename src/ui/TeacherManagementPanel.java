package ui;

import dao.TeacherDAO;
import dao.UserDAO;
import models.Teacher;
import models.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// Teacher Management Panel
public class TeacherManagementPanel extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public TeacherManagementPanel() {
        setTitle("Teacher Management");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadTeachers();
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Top
        JLabel titleLabel = new JLabel("Teacher Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Employee ID", "Name", "Department", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton addBtn = new JButton("Add");
        addBtn.setBackground(new Color(200, 255, 220));
        addBtn.setForeground(Color.BLACK);
        addBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        addBtn.addActionListener(e -> addTeacher());
        buttonPanel.add(addBtn);

        JButton editBtn = new JButton("Edit");
        editBtn.setBackground(new Color(200, 230, 255));
        editBtn.setForeground(Color.BLACK);
        editBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        editBtn.addActionListener(e -> editTeacher());
        buttonPanel.add(editBtn);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(new Color(255, 200, 200));
        deleteBtn.setForeground(Color.BLACK);
        deleteBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        deleteBtn.addActionListener(e -> deleteTeacher());
        buttonPanel.add(deleteBtn);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadTeachers());
        buttonPanel.add(refreshBtn);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> dispose());
        buttonPanel.add(backBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadTeachers() {
        tableModel.setRowCount(0);
        ArrayList<Teacher> teachers = TeacherDAO.getAllTeachers();

        for (Teacher t : teachers) {
            Object[] row = {t.getTeacherId(), t.getEmployeeId(), t.getFullName(),
                    t.getDepartment(), t.getPhone()};
            tableModel.addRow(row);
        }
    }

    private void addTeacher() {
        JDialog dialog = new JDialog(this, "Add Teacher", true);
        dialog.setSize(350, 380);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);

        JLabel lbl1 = new JLabel("Username:");
        lbl1.setBounds(20, 20, 100, 25);
        dialog.add(lbl1);
        JTextField field1 = new JTextField();
        field1.setBounds(130, 20, 190, 25);
        dialog.add(field1);

        JLabel lbl2 = new JLabel("Password:");
        lbl2.setBounds(20, 55, 100, 25);
        dialog.add(lbl2);
        JPasswordField field2 = new JPasswordField();
        field2.setBounds(130, 55, 190, 25);
        dialog.add(field2);

        JLabel lbl3 = new JLabel("Full Name:");
        lbl3.setBounds(20, 90, 100, 25);
        dialog.add(lbl3);
        JTextField field3 = new JTextField();
        field3.setBounds(130, 90, 190, 25);
        dialog.add(field3);

        JLabel lbl4 = new JLabel("Employee ID:");
        lbl4.setBounds(20, 125, 100, 25);
        dialog.add(lbl4);
        JTextField field4 = new JTextField();
        field4.setBounds(130, 125, 190, 25);
        dialog.add(field4);

        JLabel lbl5 = new JLabel("Department:");
        lbl5.setBounds(20, 160, 100, 25);
        dialog.add(lbl5);
        JTextField field5 = new JTextField();
        field5.setBounds(130, 160, 190, 25);
        dialog.add(field5);

        JLabel lbl6 = new JLabel("Phone:");
        lbl6.setBounds(20, 195, 100, 25);
        dialog.add(lbl6);
        JTextField field6 = new JTextField();
        field6.setBounds(130, 195, 190, 25);
        dialog.add(field6);

        JLabel lbl7 = new JLabel("Email:");
        lbl7.setBounds(20, 230, 100, 25);
        dialog.add(lbl7);
        JTextField field7 = new JTextField();
        field7.setBounds(130, 230, 190, 25);
        dialog.add(field7);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(80, 290, 80, 30);
        saveBtn.addActionListener(e -> {
            String username = field1.getText().trim();
            String password = new String(field2.getPassword());
            String fullName = field3.getText().trim();
            String empId = field4.getText().trim();

            if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || empId.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill required fields!");
                return;
            }

            // Create user account
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setRole("teacher");
            user.setEmail(field7.getText().trim());

            if (UserDAO.addUser(user)) {
                // Create teacher record
                Teacher teacher = new Teacher();
                teacher.setUserId(user.getUserId());
                teacher.setEmployeeId(empId);
                teacher.setFullName(fullName);
                teacher.setDepartment(field5.getText().trim());
                teacher.setPhone(field6.getText().trim());

                if (TeacherDAO.addTeacher(teacher)) {
                    JOptionPane.showMessageDialog(dialog, "Teacher added successfully!");
                    dialog.dispose();
                    loadTeachers();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to add teacher!");
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to create user account!");
            }
        });
        dialog.add(saveBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(180, 290, 80, 30);
        cancelBtn.addActionListener(e -> dialog.dispose());
        dialog.add(cancelBtn);

        dialog.setVisible(true);
    }

    private void editTeacher() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a teacher!");
            return;
        }

        int teacherId = (int) tableModel.getValueAt(row, 0);
        String empId = (String) tableModel.getValueAt(row, 1);
        String name = (String) tableModel.getValueAt(row, 2);
        String dept = (String) tableModel.getValueAt(row, 3);
        String phone = (String) tableModel.getValueAt(row, 4);

        // Get teacher to find user_id
        Teacher teacher = TeacherDAO.getTeacherById(teacherId);
        if (teacher == null) {
            JOptionPane.showMessageDialog(this, "Teacher not found!");
            return;
        }

        JDialog dialog = new JDialog(this, "Edit Teacher", true);
        dialog.setSize(350, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(null);

        JLabel lbl1 = new JLabel("Full Name:");
        lbl1.setBounds(20, 20, 100, 25);
        dialog.add(lbl1);
        JTextField field1 = new JTextField(name);
        field1.setBounds(130, 20, 190, 25);
        dialog.add(field1);

        JLabel lbl2 = new JLabel("Employee ID:");
        lbl2.setBounds(20, 60, 100, 25);
        dialog.add(lbl2);
        JTextField field2 = new JTextField(empId);
        field2.setBounds(130, 60, 190, 25);
        dialog.add(field2);

        JLabel lbl3 = new JLabel("Department:");
        lbl3.setBounds(20, 100, 100, 25);
        dialog.add(lbl3);
        JTextField field3 = new JTextField(dept);
        field3.setBounds(130, 100, 190, 25);
        dialog.add(field3);

        JLabel lbl4 = new JLabel("Phone:");
        lbl4.setBounds(20, 140, 100, 25);
        dialog.add(lbl4);
        JTextField field4 = new JTextField(phone);
        field4.setBounds(130, 140, 190, 25);
        dialog.add(field4);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(80, 200, 80, 30);
        updateBtn.addActionListener(e -> {
            String newName = field1.getText().trim();
            String newEmpId = field2.getText().trim();
            String newDept = field3.getText().trim();
            String newPhone = field4.getText().trim();

            if (newName.isEmpty() || newEmpId.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Name and Employee ID are required!");
                return;
            }

            // Update teacher info
            teacher.setFullName(newName);
            teacher.setEmployeeId(newEmpId);
            teacher.setDepartment(newDept);
            teacher.setPhone(newPhone);

            // Update user info (to change full_name in users table)
            User user = UserDAO.getUserById(teacher.getUserId());
            if (user != null) {
                user.setFullName(newName);
                UserDAO.updateUser(user);
            }

            if (TeacherDAO.updateTeacher(teacher)) {
                JOptionPane.showMessageDialog(dialog, "Teacher updated successfully!");
                dialog.dispose();
                loadTeachers();
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to update!");
            }
        });
        dialog.add(updateBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(180, 200, 80, 30);
        cancelBtn.addActionListener(e -> dialog.dispose());
        dialog.add(cancelBtn);

        dialog.setVisible(true);
    }

    private void deleteTeacher() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a teacher!");
            return;
        }

        int teacherId = (int) tableModel.getValueAt(row, 0);
        String name = (String) tableModel.getValueAt(row, 2);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete teacher: " + name + "?",
                "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Teacher teacher = TeacherDAO.getTeacherById(teacherId);

            if (teacher != null) {
                if (TeacherDAO.deleteTeacher(teacherId)) {
                    UserDAO.deleteUser(teacher.getUserId());
                    JOptionPane.showMessageDialog(this, "Teacher deleted successfully!");
                    loadTeachers();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete!");
                }
            }
        }
    }
}
