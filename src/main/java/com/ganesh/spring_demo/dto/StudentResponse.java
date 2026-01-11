package com.ganesh.spring_demo.dto;

import com.ganesh.spring_demo.model.Student;

public class StudentResponse {
    private String message;
    private Student student;

    public StudentResponse(String message, Student student) {
        this.message = message;
        this.student = student;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
