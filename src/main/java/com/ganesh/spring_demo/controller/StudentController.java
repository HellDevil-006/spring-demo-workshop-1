package com.ganesh.spring_demo.controller;


import com.ganesh.spring_demo.dto.StudentResponse;
import com.ganesh.spring_demo.model.Student;
import com.ganesh.spring_demo.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @PostMapping("/student")
    public StudentResponse addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @GetMapping("student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id){
        Student student = studentService.getStudentById(id);
        if( student == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(student);
        }
    }

    @DeleteMapping("student/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable("id") Long id){
        Student student = studentService.deleteStudentById(id);
        if(Objects.nonNull(student)){
           return ResponseEntity.ok().body("Student deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}