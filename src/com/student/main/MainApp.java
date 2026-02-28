package com.student.main;

import java.util.List;
import java.util.Scanner;

import com.student.dao.MySQLStudentDAO;
import com.student.dao.OracleStudentDAO;
import com.student.dao.StudentDAO;
import com.student.exception.InvalidStudentDataException;
import com.student.model.Student;

/**
 * Main class - Entry point of the Student Management System.
 * Demonstrates:
 *   1. Runtime Polymorphism - Using StudentDAO interface reference
 *      to access MySQLStudentDAO / OracleStudentDAO objects
 *   2. Custom Exception Handling - InvalidStudentDataException
 *   3. Input Validation - Name, Email, Age, Mobile
 *   4. Menu-driven console interface
 */
public class MainApp {

    // ===== RUNTIME POLYMORPHISM =====
    // Interface reference pointing to implementation class object.
    // We can switch between MySQLStudentDAO and OracleStudentDAO
    // without changing any other code — this IS runtime polymorphism.
    private static StudentDAO studentDAO;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // ---- Demonstrate Runtime Polymorphism ----
        // The user chooses which DAO implementation to use at RUNTIME.
        System.out.println("==========================================");
        System.out.println("   STUDENT MANAGEMENT SYSTEM (JDBC)");
        System.out.println("==========================================");
        System.out.println();
        System.out.println("Select Database Implementation:");
        System.out.println("1. MySQL (MySQLStudentDAO)");
        System.out.println("2. Oracle (OracleStudentDAO)");
        System.out.print("Enter choice (1 or 2): ");

        int dbChoice = 0;
        try {
            dbChoice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            dbChoice = 1; // default to MySQL
        }

        // RUNTIME POLYMORPHISM: Interface reference = Implementation object
        if (dbChoice == 2) {
            studentDAO = new OracleStudentDAO();  // StudentDAO ref -> OracleStudentDAO obj
            System.out.println(">> Using OracleStudentDAO implementation.\n");
        } else {
            studentDAO = new MySQLStudentDAO();   // StudentDAO ref -> MySQLStudentDAO obj
            System.out.println(">> Using MySQLStudentDAO implementation.\n");
        }

        // Main menu loop
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.\n");
                continue;
            }

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    running = false;
                    System.out.println("\nThank you for using Student Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please select 1-5.\n");
            }
        }

        scanner.close();
    }

    // ==================== DISPLAY MENU ====================
    private static void displayMenu() {
        System.out.println("------------------------------------------");
        System.out.println("            MAIN MENU");
        System.out.println("------------------------------------------");
        System.out.println("1. Add a New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student Details");
        System.out.println("4. Delete a Student");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    // ==================== ADD STUDENT ====================
    private static void addStudent() {
        System.out.println("\n--- Add New Student ---");

        try {
            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter Age: ");
            String ageStr = scanner.nextLine().trim();

            System.out.print("Enter Mobile (10 digits): ");
            String mobile = scanner.nextLine().trim();

            // ---- Validate all inputs ----
            validateStudentData(name, email, ageStr, mobile);

            int age = Integer.parseInt(ageStr);

            Student student = new Student(name, email, age, mobile);

            // Using interface reference (runtime polymorphism)
            boolean result = studentDAO.addStudent(student);

            if (result) {
                System.out.println(">> Student added successfully!\n");
            } else {
                System.out.println(">> Failed to add student.\n");
            }

        } catch (InvalidStudentDataException e) {
            // ---- Custom exception handling ----
            System.out.println(">> VALIDATION ERROR: " + e.getMessage());
            System.out.println(">> Student was NOT added. Please try again.\n");
        }
    }

    // ==================== VIEW ALL STUDENTS ====================
    private static void viewAllStudents() {
        System.out.println("\n--- All Students ---");

        // Using interface reference (runtime polymorphism)
        List<Student> students = studentDAO.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found in the database.\n");
        } else {
            System.out.println(String.format("%-5s %-20s %-25s %-5s %-15s",
                    "ID", "Name", "Email", "Age", "Mobile"));
            System.out.println("----------------------------------------------------------------------");

            for (Student s : students) {
                System.out.println(String.format("%-5d %-20s %-25s %-5d %-15s",
                        s.getId(), s.getName(), s.getEmail(), s.getAge(), s.getMobile()));
            }
            System.out.println();
        }
    }

    // ==================== UPDATE STUDENT ====================
    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");

        System.out.print("Enter Student ID to update: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(">> Invalid ID format.\n");
            return;
        }

        // First check if student exists
        Student existing = studentDAO.getStudentById(id);
        if (existing == null) {
            System.out.println(">> No student found with ID: " + id + "\n");
            return;
        }

        System.out.println("Current details: " + existing);
        System.out.println("Enter new details (press Enter to keep current value):\n");

        try {
            System.out.print("Enter New Name [" + existing.getName() + "]: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) name = existing.getName();

            System.out.print("Enter New Email [" + existing.getEmail() + "]: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) email = existing.getEmail();

            System.out.print("Enter New Age [" + existing.getAge() + "]: ");
            String ageStr = scanner.nextLine().trim();
            if (ageStr.isEmpty()) ageStr = String.valueOf(existing.getAge());

            System.out.print("Enter New Mobile [" + existing.getMobile() + "]: ");
            String mobile = scanner.nextLine().trim();
            if (mobile.isEmpty()) mobile = existing.getMobile();

            // ---- Validate updated inputs ----
            validateStudentData(name, email, ageStr, mobile);

            int age = Integer.parseInt(ageStr);

            Student updated = new Student(id, name, email, age, mobile);

            // Using interface reference (runtime polymorphism)
            boolean result = studentDAO.updateStudent(updated);

            if (result) {
                System.out.println(">> Student updated successfully!\n");
            } else {
                System.out.println(">> Failed to update student.\n");
            }

        } catch (InvalidStudentDataException e) {
            // ---- Custom exception handling ----
            System.out.println(">> VALIDATION ERROR: " + e.getMessage());
            System.out.println(">> Student was NOT updated. Please try again.\n");
        }
    }

    // ==================== DELETE STUDENT ====================
    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");

        System.out.print("Enter Student ID to delete: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(">> Invalid ID format.\n");
            return;
        }

        // Using interface reference (runtime polymorphism)
        boolean result = studentDAO.deleteStudent(id);

        if (result) {
            System.out.println(">> Student with ID " + id + " deleted successfully!\n");
        } else {
            System.out.println(">> No student found with ID: " + id + " (or deletion failed).\n");
        }
    }

    // ==================== VALIDATION METHOD ====================
    /**
     * Validates student data and throws InvalidStudentDataException
     * if any validation rule is violated.
     *
     * Rules:
     *   - Name must not be empty or numeric
     *   - Email must contain '@'
     *   - Age must be positive
     *   - Mobile must be exactly 10 digits
     */
    private static void validateStudentData(String name, String email, String ageStr, String mobile)
            throws InvalidStudentDataException {

        // 1. Name must not be empty
        if (name == null || name.isEmpty()) {
            throw new InvalidStudentDataException("Name cannot be empty!");
        }

        // 2. Name must not be fully numeric
        if (name.matches("[0-9]+")) {
            throw new InvalidStudentDataException("Name cannot be numeric! Please enter a valid name.");
        }

        // 3. Email must contain '@'
        if (email == null || !email.contains("@")) {
            throw new InvalidStudentDataException("Email must contain '@' symbol! Invalid email: " + email);
        }

        // 4. Age must be a valid positive number
        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            throw new InvalidStudentDataException("Age must be a valid number!");
        }
        if (age <= 0) {
            throw new InvalidStudentDataException("Age must be a positive number! You entered: " + age);
        }

        // 5. Mobile must be exactly 10 digits
        if (mobile == null || !mobile.matches("[0-9]{10}")) {
            throw new InvalidStudentDataException(
                "Mobile number must contain exactly 10 digits! You entered: " + mobile);
        }
    }
}
