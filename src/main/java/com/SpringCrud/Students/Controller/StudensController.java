package com.SpringCrud.Students.Controller;

import com.SpringCrud.Students.Models.Student;
import com.SpringCrud.Students.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudensController {

    @Autowired
    StudentService studentService;

    @GetMapping("/Students")
    public List<Student> students() {
        return studentService.getAllStudents();
    }

    @PostMapping("/saveStudent")
    public Student createStudent(@RequestBody Student student) {
        return studentService.CreateStudent(student);
    }

    @DeleteMapping("/delStudent/{id}")
    public void deletonestudent(@PathVariable Long id) {
        studentService.DeleteStudent(id);
    }

    @PutMapping("/updateSt/{id}")
    public void updateStudent(@RequestBody Student  student,@PathVariable Long id){
        studentService.UpdateStudent(student,id);
    }
}