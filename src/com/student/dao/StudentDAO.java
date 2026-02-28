package com.student.dao;

import java.util.List;

import com.student.model.Student;

/**
 * StudentDAO interface declaring all CRUD methods.
 * Any DAO implementation class must implement this interface.
 * This enables runtime polymorphism - we can use interface reference
 * to access any implementation class object.
 */
public interface StudentDAO {

    /**
     * Add a new student to the database.
     * @param student the Student object to add
     * @return true if insertion was successful, false otherwise
     */
    boolean addStudent(Student student);

    /**
     * Retrieve all students from the database.
     * @return List of all Student objects
     */
    List<Student> getAllStudents();

    /**
     * Delete a student from the database by ID.
     * @param id the student ID to delete
     * @return true if deletion was successful, false otherwise
     */
    boolean deleteStudent(int id);

    /**
     * Update an existing student's details.
     * @param student the Student object with updated fields
     * @return true if update was successful, false otherwise
     */
    boolean updateStudent(Student student);

    /**
     * Retrieve a single student by ID.
     * @param id the student ID
     * @return Student object if found, null otherwise
     */
    Student getStudentById(int id);
}
