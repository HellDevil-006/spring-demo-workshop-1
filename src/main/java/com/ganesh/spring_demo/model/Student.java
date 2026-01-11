package com.ganesh.spring_demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "studentName", nullable = false)
    private String studentName;

    @Column(unique = true, name = "emailAddress")
    private String emailAddress;

    @Column(nullable = true)
    private String address;

    public Student() {
    }

    public Student(Long id, String studentName, String address, String emailAddress) {
        this.id = id;
        this.studentName = studentName;
        this.address = address;
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
