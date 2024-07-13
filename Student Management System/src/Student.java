import java.time.LocalDate;

class Student {
    private int studentId;
    private String name;
    private LocalDate birthday;
    private String email;

    // Constructor to initialize a Student object with provided details
    public Student(int studentId, String name, LocalDate birthday, String email) {
        this.studentId = studentId;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
    }

    // Getter for student ID
    public int getStudentId() {
        return studentId;
    }

    // Setter for student ID
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    // Getter for student name
    public String getName() {
        return name;
    }

    // Setter for student name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for student birthday
    public LocalDate getBirthday() {
        return birthday;
    }

    // Setter for student birthday
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    // Getter for student email
    public String getEmail() {
        return email;
    }

    // Setter for student email
    public void setEmail(String email) {
        this.email = email;
    }
}