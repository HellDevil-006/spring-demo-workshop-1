package com.ganesh.spring_demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ganesh.spring_demo.model.Attendance;
import com.ganesh.spring_demo.model.Course;
import com.ganesh.spring_demo.model.Student;
import com.ganesh.spring_demo.repository.AttendanceRepository;
import com.ganesh.spring_demo.repository.CourseRepository;
import com.ganesh.spring_demo.repository.StudentRepository;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, 
                           StudentRepository studentRepository, 
                           CourseRepository courseRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Attendance markAttendance(Long studentId, Long courseId, LocalDate date, Boolean present, String remarks) {
        // Get student and course by ID
        Student student = studentRepository.findById(studentId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);

        if (student == null || course == null) {
            throw new RuntimeException("Student or Course not found");
        }

        // Check if attendance already exists for this student, course, and date
        List<Attendance> existingAttendance = attendanceRepository.findByStudentIdAndCourseId(studentId, courseId);
        for (Attendance att : existingAttendance) {
            if (att.getAttendanceDate().equals(date)) {
                throw new RuntimeException("Attendance already marked for this date");
            }
        }

        // Create and save new attendance record
        Attendance attendance = new Attendance(student, course, date, present);
        attendance.setRemarks(remarks);
        
        return attendanceRepository.save(attendance);
    }

    public Attendance createAttendance(Attendance attendance) {
        // If student and course IDs are provided, associate them
        if (attendance.getStudent() != null && attendance.getStudent().getId() != null) {
            Student student = studentRepository.findById(attendance.getStudent().getId()).orElse(null);
            if (student != null) {
                attendance.setStudent(student);
            }
        }
        
        if (attendance.getCourse() != null && attendance.getCourse().getId() != null) {
            Course course = courseRepository.findById(attendance.getCourse().getId()).orElse(null);
            if (course != null) {
                attendance.setCourse(course);
            }
        }
        
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceById(Long id) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        return attendance.orElse(null);
    }

    public Attendance updateAttendance(Long id, Attendance attendanceDetails) {
        Attendance attendance = attendanceRepository.findById(id).orElse(null);
        if (attendance != null) {
            attendance.setAttendanceDate(attendanceDetails.getAttendanceDate());
            attendance.setPresent(attendanceDetails.getPresent());
            attendance.setRemarks(attendanceDetails.getRemarks());
            
            // Handle student and course associations if provided
            if (attendanceDetails.getStudent() != null && attendanceDetails.getStudent().getId() != null) {
                Student student = studentRepository.findById(attendanceDetails.getStudent().getId()).orElse(null);
                if (student != null) {
                    attendance.setStudent(student);
                }
            }
            
            if (attendanceDetails.getCourse() != null && attendanceDetails.getCourse().getId() != null) {
                Course course = courseRepository.findById(attendanceDetails.getCourse().getId()).orElse(null);
                if (course != null) {
                    attendance.setCourse(course);
                }
            }
            
            return attendanceRepository.save(attendance);
        }
        return null;
    }

    public boolean deleteAttendanceById(Long id) {
        if (attendanceRepository.existsById(id)) {
            attendanceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Attendance> getAttendanceByStudentId(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    public List<Attendance> getAttendanceByCourseId(Long courseId) {
        return attendanceRepository.findByCourseId(courseId);
    }

    public List<Attendance> getAttendanceByStudentAndCourse(Long studentId, Long courseId) {
        return attendanceRepository.findByStudentIdAndCourseId(studentId, courseId);
    }
}