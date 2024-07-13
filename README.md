# Student Management System

This Java-based Student Management System allows you to manage student records with functionalities to add, list, search, update, and delete student details. The system interacts with a MySQL database to store and retrieve student information.

## Features

- **Add Student**: Add new students to the system with details like student ID, name, birthday, and email.
- **List Students**: Display a list of all students in the system.
- **Search Student**: Search for a student by name.
- **Update Student**: Update details of an existing student.
- **Delete Student**: Remove a student from the system.

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

Getting Started

Clone the repository:

git clone https://github.com/yourusername/student-management-system.git
cd student-management-system



