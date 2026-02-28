package com.student.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.model.Student;

/**
 * Oracle implementation of StudentDAO interface.
 * This is the SECOND implementation class to demonstrate runtime polymorphism.
 * Uses PreparedStatement for all database operations.
 * 
 * NOTE: For this assessment, both MySQLStudentDAO and OracleStudentDAO
 * connect to the same MySQL database to keep the setup simple.
 * In a real-world scenario, this class would use Oracle-specific
 * JDBC driver and connection URL.
 */
public class OracleStudentDAO implements StudentDAO {

    // ============ ADD STUDENT ============
    @Override
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (name, email, age, mobile) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getMobile());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("[OracleDAO] Error adding student: " + e.getMessage());
            return false;
        }
    }

    // ============ GET ALL STUDENTS ============
    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setAge(rs.getInt("age"));
                student.setMobile(rs.getString("mobile"));
                students.add(student);
            }

        } catch (SQLException e) {
            System.out.println("[OracleDAO] Error retrieving students: " + e.getMessage());
        }
        return students;
    }

    // ============ DELETE STUDENT ============
    @Override
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("[OracleDAO] Error deleting student: " + e.getMessage());
            return false;
        }
    }

    // ============ UPDATE STUDENT ============
    @Override
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, email = ?, age = ?, mobile = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getMobile());
            pstmt.setInt(5, student.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("[OracleDAO] Error updating student: " + e.getMessage());
            return false;
        }
    }

    // ============ GET STUDENT BY ID ============
    @Override
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("age"),
                    rs.getString("mobile")
                );
            }

        } catch (SQLException e) {
            System.out.println("[OracleDAO] Error fetching student: " + e.getMessage());
        }
        return null;
    }
}
