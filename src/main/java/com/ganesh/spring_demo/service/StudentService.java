package com.ganesh.spring_demo.service;

import com.ganesh.spring_demo.dto.StudentResponse;
import com.ganesh.spring_demo.model.Student;
import com.ganesh.spring_demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentResponse addStudent(Student student){
        return new StudentResponse("Student added successfully!",studentRepository.save(student));
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student deleteStudentById(Long id){
        Student student = studentRepository.findById(id).orElse(null);
        if(Objects.nonNull(student)){
            studentRepository.deleteById(id);
            return student;
        } else {
            return null;
        }
    }
}
