package com.student.dao;

import java.util.List;
import com.student.model.Student;

public interface StudentDAO {
    boolean addStudent(Student student);
    List<Student> getAllStudents();
    boolean deleteStudent(int id);
    boolean updateStudent(Student student);
    Student getStudentById(int id);
}
