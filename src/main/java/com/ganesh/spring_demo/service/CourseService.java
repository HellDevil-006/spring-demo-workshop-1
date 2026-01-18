package com.ganesh.spring_demo.service;

import com.ganesh.spring_demo.model.Course;
import com.ganesh.spring_demo.model.Teacher;
import com.ganesh.spring_demo.repository.CourseRepository;
import com.ganesh.spring_demo.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public CourseService(CourseRepository courseRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    public Course createCourse(Course course) {
        // If a teacher ID is provided, associate the course with the teacher
        if (course.getTeacher() != null && course.getTeacher().getId() != null) {
            Teacher teacher = teacherRepository.findById(course.getTeacher().getId()).orElse(null);
            if (teacher != null) {
                course.setTeacher(teacher);
            }
        }
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.orElse(null);
    }

    public Course updateCourse(Long id, Course courseDetails) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null) {
            course.setCourseCode(courseDetails.getCourseCode());
            course.setCourseName(courseDetails.getCourseName());
            course.setCredits(courseDetails.getCredits());
            
            // Handle teacher association if provided
            if (courseDetails.getTeacher() != null && courseDetails.getTeacher().getId() != null) {
                Teacher teacher = teacherRepository.findById(courseDetails.getTeacher().getId()).orElse(null);
                if (teacher != null) {
                    course.setTeacher(teacher);
                }
            }
            return courseRepository.save(course);
        }
        return null;
    }

    public boolean deleteCourseById(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Course> getCoursesByTeacherId(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }
}