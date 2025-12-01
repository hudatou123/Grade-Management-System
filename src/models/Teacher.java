package models;

public class Teacher {
    private int teacherId;
    private int userId;
    private String employeeId;
    private String fullName;
    private String department;
    private String phone;

    public Teacher() {
    }

    public Teacher(int teacherId, int userId, String employeeId, String fullName, String department) {
        this.teacherId = teacherId;
        this.userId = userId;
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.department = department;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDepartment() {
        return department;
    }

    public String getPhone() {
        return phone;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return employeeId + " - " + fullName + " (" + department + ")";
    }
}