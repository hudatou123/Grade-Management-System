**_Illustration of the project:_**

## Project Introduction:
This is a Grade Management System built with Java and MySQL.

## Administrator Functions:
- Manage Teachers (Add, Edit, Delete)
- Manage Students (Add, Edit, Delete)
- View All Grades
- Generate Reports
- System Settings
- print mark sheets in TXT format.
 
## Teacher Functions:
- View Students
- Manage Grades (Add, Edit, Delete)
- Generate Reports
- print mark sheets in TXT format.

## Technology Stack
- Language: Java 21(Swing GUI)
- Database: MySQL 8.4.7
- Architecture: DAO Pattern (Data Access Object)

## Project Structure:

GradeManagementSystem/
├── src/
│   ├── Main.java             
│   │
│   ├── config/
│   │   └── DatabaseConfig.java
│   │
│   ├── database/
│   │   └── DatabaseConnection.java
│   │
│   ├── models/ 
│   │   ├── User.java
│   │   ├── Teacher.java
│   │   ├── Student.java
│   │   └── Grade.java
│   │
│   ├── dao/
│   │   ├── UserDAO.java
│   │   ├── TeacherDAO.java
│   │   ├── StudentDAO.java
│   │   └── GradeDAO.java
│   │
│   └── ui/
│       ├── LoginPanel.java
│       ├── AdminPanel.java
│       ├── TeacherPanel.java
│       ├── TeacherManagementPanel.java
│       ├── StudentManagementPanel.java
│       ├── GradeManagementPanel.java
│       ├── ReportPanel.java
│       └── SettingsPanel.java
│
├── lib/
│   └── mysql-connector-j-8.0.33.jar
│
└── README.md

## Package Functions

* config/
   Purpose: Configuration information management
   Contains: Database connection settings (URL, username, password)

* database/
   Purpose: Database connection management
   Contains: Connection handler using Singleton pattern

* models/
   Purpose: Data structure definitions
   Contains: Java classes representing database tables (User, Teacher, Student, Grade)

* dao/
   Purpose: Separates database logic from UI logic and Database operations (Data Access Layer)
   Contains: Classes that handle CRUD operations for each table

* ui/
   Purpose: User interface (Presentation Layer) by Java Swing
   Contains: All GUI windows and user interaction logic

* lib/
   Purpose: Connect Java to MySQL
   Contains: MySQL JDBC Driver

## Data Flow
User Action (UI Layer)
↓
DAO Method Call (Data Access Layer)
↓
SQL Query Execution (DatabaseConnection)
↓
MySQL Database
↓
Return Data to DAO
↓
Display in UI

## Testing Accounts:
The login access is divided into admin and teacher.

For admin
user name: admin
password: admin123

For teacher
user name: teacher1 / teacher2
password: teacher123

