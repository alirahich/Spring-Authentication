package com.SpringCrud.Students.Services;

import com.SpringCrud.Students.Models.Student;

import java.util.List;

public interface StudentService {
    public List<Student> getAllStudents();

    public Student CreateStudent(Student student);

    public void DeleteStudent(Long id);

    Student UpdateStudent(Student student, Long id);
}
