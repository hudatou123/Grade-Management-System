package models;

public class Grade {
    private int gradeId;
    private int studentId;
    private String studentName;
    private String courseName;
    private double assignmentScore;
    private double midtermScore;
    private double finalScore;
    private double totalScore;
    private String letterGrade;     // Grade（A, B, C, D, F）
    private String semester;

    // Parameterless constructor method creating an empty object
    public Grade() {
    }

    // Parameterized constructor method
    public Grade(int gradeId, int studentId, String studentName, String courseName) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseName = courseName;
    }

    // Getter method - obtain the value of the attribute
    public int getGradeId() {
        return gradeId;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getAssignmentScore() {
        return assignmentScore;
    }

    public double getMidtermScore() {
        return midtermScore;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public String getSemester() {
        return semester;
    }

    // Setter method - set the value of the attribute
    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setAssignmentScore(double assignmentScore) {
        this.assignmentScore = assignmentScore;
        calculateTotal();
    }

    public void setMidtermScore(double midtermScore) {
        this.midtermScore = midtermScore;
        calculateTotal();
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
        calculateTotal();
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    private void calculateTotal() {

        totalScore = (assignmentScore * 0.3) + (midtermScore * 0.3) + (finalScore * 0.4);

        if (totalScore >= 90) {
            letterGrade = "A";
        } else if (totalScore >= 80) {
            letterGrade = "B";
        } else if (totalScore >= 70) {
            letterGrade = "C";
        } else if (totalScore >= 60) {
            letterGrade = "D";
        } else {
            letterGrade = "F";
        }
    }

    public String toString() {
        return studentName + " - " + courseName + ": " + totalScore + " (" + letterGrade + ")";
    }
}