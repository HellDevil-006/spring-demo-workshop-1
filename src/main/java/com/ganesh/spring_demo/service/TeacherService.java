package com.ganesh.spring_demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ganesh.spring_demo.model.Course;
import com.ganesh.spring_demo.model.Teacher;
import com.ganesh.spring_demo.repository.TeacherRepository;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseService courseService;

    public TeacherService(TeacherRepository teacherRepository, CourseService courseService) {
        this.teacherRepository = teacherRepository;
        this.courseService = courseService;
    }

    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return teacher.orElse(null);
    }

    public Teacher updateTeacher(Long id, Teacher teacherDetails) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        if (teacher != null) {
            teacher.setTeacherName(teacherDetails.getTeacherName());
            teacher.setEmailAddress(teacherDetails.getEmailAddress());
            teacher.setSubject(teacherDetails.getSubject());
            return teacherRepository.save(teacher);
        }
        return null;
    }

    public boolean deleteTeacherById(Long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public List<Course> getCoursesByTeacherId(Long teacherId) {
        return courseService.getCoursesByTeacherId(teacherId);
    }
}