import java.sql.*;
import java.util.*;
import java.time.LocalDate;

public class StudentManagementSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String USER = "root";
    private static final String PASSWORD = "Add Your Password";

    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException if JDBC driver is not found
            System.out.println("MySQL JDBC Driver not found.");
            return;
        }

        // Main loop for displaying menu
        while (true) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    listStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    System.out.println("\nExiting...");
                    return;
                default:
                    System.out.println("\nInvalid choice. Please enter a number between 1 and 6.");
            }
        }
    }

    // Method to display the main menu
    private static void displayMenu() {
        System.out.println("\n<< Welcome to Student Management System >>\n");
        System.out.println("1. Add Student");
        System.out.println("2. List Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit\n");
        System.out.print("Enter Your Choice: ");
    }

    private static int getUserChoice() {
        int choice = -1;
        while (true) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                break;
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid choice. Please enter a number between 1 and 6.");
                scanner.nextLine(); // Consume invalid input
                displayMenu();
            }
        }
        return choice;
    }

    private static void addStudent() {
        System.out.println("\n<< Add Student >>");

        int studentId = 0;
        boolean validId = false;

        // Prompt the user to enter the student ID and validate input
        while (!validId) {
            System.out.print("\nEnter Student ID: ");
            try {
                studentId = Integer.parseInt(scanner.nextLine().trim());
                validId = true;  // Mark ID as valid if parsing succeeds
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid numeric student ID.");
            }
        }

        // Check if the student ID is already taken
        if (isStudentIdTaken(studentId)) {
            System.out.println("\nThis student ID is already taken. Please try again.");
            return;
        }

        // Prompt the user to enter the student's name and validate it
        String name;
        while (true) {
            System.out.print("Enter Name: ");
            name = scanner.nextLine();
            if (name.matches("[a-zA-Z ]+")) { // Ensure name contains only letters and spaces
                break;
            } else {
                System.out.println("\nInvalid name. Name can only contain letters. Please try again.\n");
            }
        }

        // Prompt the user to enter the student's birthday and validate it
        String birthdayStr;
        LocalDate birthday;
        while (true) {
            System.out.print("Enter Birthday (YYYY-MM-DD): ");
            birthdayStr = scanner.nextLine();
            try {
                birthday = LocalDate.parse(birthdayStr); // Ensure the date is in the correct format
                break;
            } catch (Exception e) {
                System.out.println("\nInvalid date format. Please enter the birthday in YYYY-MM-DD format.\n");
            }
        }

        // Prompt the user to enter the student's email and validate it
        String email;
        while (true) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine();
            if (email.contains("@") && email.contains(".")) { // Ensure email contains '@' and '.'
                break;
            } else {
                System.out.println("\nInvalid email. Please enter a valid email address.\n");
            }
        }

        // Create a new Student object
        Student student = new Student(studentId, name, birthday, email);

        // Insert the new student into the database
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO students (student_id, name, birthday, email) VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, student.getStudentId());
            stmt.setString(2, student.getName());
            stmt.setDate(3, java.sql.Date.valueOf(student.getBirthday()));
            stmt.setString(4, student.getEmail());
            stmt.executeUpdate();
            System.out.println("\nStudent added successfully!");

            // Add the student object to the ArrayList after successful database insertion
            students.add(student);

        } catch (SQLException e) {
            System.out.println("\nError adding student: " + e.getMessage());
        }
    }


    private static boolean isStudentIdTaken(int studentId) {
        // Try-with-resources block to automatically close resources
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT COUNT(*) FROM students WHERE student_id = ?")) {
            // Set the student ID parameter in the prepared statement
            stmt.setInt(1, studentId);

            // Execute the query and process the result set
            try (ResultSet rs = stmt.executeQuery()) {
                // Check if there are any rows returned and if the count is greater than 0
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // Student ID is already taken
                }
            }
        } catch (SQLException e) {
            System.out.println("\nError checking student ID: " + e.getMessage());
        }
        return false; // Student ID is not taken or an error occurred
    }

    private static void listStudents() {
        // Print header for the student list
        System.out.println("\n<< Student List >>");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {

            boolean found = false;

            // Iterate through the result set and print each student's details
            while (rs.next()) {
                found = true;
                System.out.println("\nID: " + rs.getInt("student_id")); // Print student ID
                System.out.println("Name: " + rs.getString("name")); // Print student name
                System.out.println("Birthday: " + rs.getDate("birthday")); // Print student birthday
                System.out.println("Email: " + rs.getString("email")); // Print student email
            }

            if (!found) {
                System.out.println("\nNo students found.");
            }
        } catch (SQLException e) {
            System.out.println("Error listing students: " + e.getMessage()); // Print error message if SQL exception occurs
        }
    }



    private static void searchStudent() {
        // Print header for the search student operation
        System.out.println("\n<< Search Student >>");

        // Prompt user to enter student name to search
        System.out.print("\nEnter student name to search: ");
        String searchName = scanner.nextLine();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students WHERE UPPER(name) = UPPER(?)")) {

            // Set the searchName parameter in the prepared statement
            stmt.setString(1, searchName);

            // Execute the query and get the result set
            ResultSet rs = stmt.executeQuery();
            boolean found = false;

            // Iterate through the result set and print each matching student's details
            while (rs.next()) {
                found = true;
                System.out.println("\nID: " + rs.getInt("student_id")); // Print student ID
                System.out.println("Name: " + rs.getString("name")); // Print student name
                System.out.println("Birthday: " + rs.getDate("birthday")); // Print student birthday
                System.out.println("Email: " + rs.getString("email")); // Print student email
            }

            // Print message if no student with the given name was found
            if (!found) {
                System.out.println("\nNo student found with that name.");
            }
        } catch (SQLException e) {
            System.out.println("\nError searching for student: " + e.getMessage()); // Print error message if SQL exception occurs
        }
    }


    private static void updateStudent() {
        System.out.println("\n<< Update Student >>");

        int studentId = 0;
        while (true) {
            System.out.print("\nEnter Student ID to Update: ");
            try {
                studentId = Integer.parseInt(scanner.nextLine().trim());
                break; // Exit the loop if input is successfully parsed as an integer
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid numeric student ID.");
            }
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmtSelect = conn.prepareStatement("SELECT * FROM students WHERE student_id = ?");
             PreparedStatement stmtUpdate = conn.prepareStatement(
                     "UPDATE students SET name = ?, birthday = ?, email = ? WHERE student_id = ?")) {

            // First, retrieve the current student details from the database
            stmtSelect.setInt(1, studentId);
            ResultSet rs = stmtSelect.executeQuery();

            if (rs.next()) {
                // Retrieve current details
                String currentName = rs.getString("name");
                LocalDate currentBirthday = rs.getDate("birthday").toLocalDate();
                String currentEmail = rs.getString("email");

                // Prompt user for updated details
                String newName = promptForName(currentName); // Validate name
                LocalDate newBirthday = promptForBirthday(currentBirthday); // Validate birthday
                String newEmail = promptForEmail(currentEmail); // Validate email

                // Perform the database update
                stmtUpdate.setString(1, newName);
                stmtUpdate.setDate(2, java.sql.Date.valueOf(newBirthday));
                stmtUpdate.setString(3, newEmail);
                stmtUpdate.setInt(4, studentId);

                int affectedRows = stmtUpdate.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("\nStudent with ID " + studentId + " updated successfully!");

                    // Update local ArrayList if the update was successful
                    for (Student student : students) {
                        if (student.getStudentId() == studentId) {
                            student.setName(newName);
                            student.setBirthday(newBirthday);
                            student.setEmail(newEmail);
                            break;
                        }
                    }
                } else {
                    System.out.println("\nNo student found with that ID.");
                }
            } else {
                System.out.println("\nNo student found with that ID.");
            }
        } catch (SQLException e) {
            System.out.println("\nError updating student: " + e.getMessage());
        }
    }

    private static String promptForName(String currentName) {
        String newName;

        // Loop until valid name is entered
        while (true) {
            // Prompt user for new name, showing current name as reference
            System.out.print("\nEnter New Name (Current: " + currentName + "): ");
            newName = scanner.nextLine().trim(); // Read user input and trim leading/trailing whitespace

            // Validate the new name
            if (newName.isEmpty()) {
                System.out.println("\nName cannot be empty. Please enter a valid name.");
            } else if (!newName.matches("[a-zA-Z ]+")) {
                System.out.println("\nInvalid name. Name can only contain letters and spaces.");
            } else {
                break; // Exit loop if name is valid
            }
        }

        return newName; // Return the validated new name
    }

    private static LocalDate promptForBirthday(LocalDate currentBirthday) {
        LocalDate newBirthday = currentBirthday; // Initialize new birthday with the current birthday

        // Loop until a valid birthday is entered
        while (true) {
            // Prompt user for new birthday, showing current birthday as reference
            System.out.print("\nEnter New Birthday (YYYY-MM-DD) (Current: " + currentBirthday + "): ");
            String newBirthdayStr = scanner.nextLine().trim(); // Read user input and trim leading/trailing whitespace

            // If input is empty, keep the current birthday
            if (newBirthdayStr.isEmpty()) {
                break;
            }

            // Attempt to parse the input string to LocalDate
            try {
                newBirthday = LocalDate.parse(newBirthdayStr); // Parse input to LocalDate
                break; // Exit loop if parsing is successful
            } catch (Exception e) {
                System.out.println("\nInvalid date format. Please enter the birthday in YYYY-MM-DD format.");
            }
        }

        return newBirthday; // Return the validated new birthday
    }

    private static String promptForEmail(String currentEmail) {
        String newEmail; // Initialize variable to hold new email

        // Loop until a valid email is entered
        while (true) {
            // Prompt user for new email, showing current email as reference
            System.out.print("\nEnter New Email (Current: " + currentEmail + "): ");
            newEmail = scanner.nextLine().trim(); // Read user input and trim leading/trailing whitespace

            // If input is empty, keep the current email
            if (newEmail.isEmpty()) {
                break;
            }

            // Validate email format
            if (!newEmail.contains("@") || !newEmail.contains(".")) {
                System.out.println("\nInvalid email format. Please enter a valid email address.");
            } else {
                break; // Exit loop if email format is valid
            }
        }

        return newEmail; // Return the validated new email
    }


    private static void deleteStudent() {
        System.out.println("\n<< Delete Student >>");

        System.out.print("\nEnter Student ID to Delete: ");
        int studentId;
        try {
            studentId = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("\nPlease enter a valid numeric student ID.");
            scanner.nextLine(); // Consume newline in case of incorrect input
            return;
        }
        scanner.nextLine(); // Consume newline after reading the integer input


        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM students WHERE student_id = ?")) {
            // Set the student ID parameter for the SQL DELETE statement
            stmt.setInt(1, studentId);

            // Execute the DELETE operation and get the number of affected rows
            int affectedRows = stmt.executeUpdate();

            // Check if any rows were affected
            if (affectedRows > 0) {
                System.out.println("\nStudent with ID " + studentId + " deleted successfully!");

                // Remove the student from the local ArrayList if deletion was successful
                students.removeIf(s -> s.getStudentId() == studentId);
            } else {
                System.out.println("\nNo student found with that ID.");
            }
        } catch (SQLException e) {
            System.out.println("\nError deleting student: " + e.getMessage());
        }
    }

}


