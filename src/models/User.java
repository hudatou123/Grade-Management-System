package models;

// Represents a user (admin or teacher) in the system
public class User {
    private int userId;
    private String username;
    private String password;
    private String fullName;
    private String role;
    private String email;

    // Constructor Instruction: Empty constructor
    public User() {
    }

    // Constructor 2: Constructor with parameters
    public User(int userId, String username, String password, String fullName, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    // Getter methods
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    // Setter methods
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {  // ← 添加这个方法
        this.email = email;
    }

    // Check if user is admin
    public boolean isAdmin() {
        if (role.equals("admin")) {
            return true;
        } else {
            return false;
        }
    }

    // Check if user is teacher
    public boolean isTeacher() {
        if (role.equals("teacher")) {
            return true;
        } else {
            return false;
        }
    }

    // toString method
    public String toString() {
        return fullName + " (" + role + ")";
    }
}