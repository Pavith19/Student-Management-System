![GitHub top language](https://img.shields.io/github/languages/top/Pavith19/Student-Management-System?style=flat)
![GitHub last commit](https://img.shields.io/github/last-commit/Pavith19/Student-Management-System?style=flat)
![ViewCount](https://views.whatilearened.today/views/github/Pavith19/Student-Management-System.svg?cache=remove)

# Student Management System

This Java-based Student Management System allows you to manage student records with functionalities to add, list, search, update, and delete student details. The system interacts with a MySQL database to store and retrieve student information.

## Features

- **Add Student**: Add new students to the system with details like student ID, name, birthday, and email.
- **List Students**: Display a list of all students in the system.
- **Search Student**: Search for a student by name.
- **Update Student**: Update details of an existing student by entering their student ID.
- **Delete Student**: Remove a student from the system by entering their student ID.
- **Exit**: Terminate the application.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- MySQL Server
- MySQL Connector/J

## Database Setup

1. Install MySQL and start the MySQL server.
2. Create a database named `student_management`.
3. Create a `students` table with the following SQL script:

```sql
CREATE DATABASE student_management;

USE student_management;

CREATE TABLE students (
    student_id INT PRIMARY KEY,
    name VARCHAR(100),
    birthday DATE,
    email VARCHAR(100)
);
```

## Getting Started

**1- Clone the repository:**
```
git clone https://github.com/yourusername/student-management-system.git
cd student-management-system
```
**2- Configure the database connection:**

- Open the StudentManagementSystem.java file.
- Update the URL, USER and PASSWORD variables with your MySQL database connection details:
  
```java
private static final String URL = "jdbc:mysql://localhost:3306/student_management";
private static final String USER = "root";
private static final String PASSWORD = "YourPassword";
```
**3- Add MySQL Connector/J to the project:**

- Download the MySQL Connector/J from the official MySQL website.
- Add the JAR file to your project's build path.

**4- Compile and run the application:**
```sh
javac StudentManagementSystem.java Student.java
java StudentManagementSystem
```

## Usage

Upon running the application (`StudentManagementSystem.java`), a menu will display with options to add, list, search, update, or delete student records. You can add a new student by entering their details, list all existing students, search for a student by name, update their information, delete a student by ID, or exit the application. Follow the on-screen prompts for each operation.

## Input Validations and Error Handling

- **Student ID**:
  - Must be a numeric value.
  - The system checks for duplicate IDs to ensure each student has a unique ID.
  - Error message: "Please enter a valid numeric student ID."

- **Name**:
  - Must contain only letters and spaces.
  - Error message: "Invalid name. Name can only contain letters and spaces."

- **Birthday**:
  - Must be in the format YYYY-MM-DD.
  - Error message: "Invalid date format. Please enter the birthday in YYYY-MM-DD format."

- **Email**:
  - Must contain '@' and '.' to be considered valid.
  - Error message: "Invalid email. Please enter a valid email address."

- **General Error Handling**:
  - SQL exceptions are caught and displayed to the user with relevant messages.
  - Input mismatch exceptions are handled to prompt the user for correct input types.

## 🛠 Skills
MySQL, Java Programming & OOP

<h3 align="center">Connect with me:</h3>
<p align="center">
  <a href="https://instagram.com/_mr_2001__" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/instagram.svg" alt="_mr_2001__" height="30" width="40" /></a>
  <a href="https://linkedin.com/in/www.linkedin.com/in/pavith-bambaravanage-465300293" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/linked-in-alt.svg" alt="pavith-bambaravanage-465300293" height="25" width="35" /></a>
  <a href="https://www.hackerrank.com/@pavith_db" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/hackerrank.svg" alt="@pavith_db" height="40" width="45" /></a>
  <a href="https://www.leetcode.com/pavith_db" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/leet-code.svg" alt="pavith_db" height="30" width="40" /></a>
  <a href="mailto:pavithd2020@gmail.com" target="blank"><img align="center" src="https://github.com/TheDudeThatCode/TheDudeThatCode/raw/master/Assets/Gmail.svg" alt="pavithd2020@gmail.com" height="30" width="40" /></a>
</p>


