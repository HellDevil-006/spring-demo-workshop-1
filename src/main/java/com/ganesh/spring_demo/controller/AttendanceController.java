package com.ganesh.spring_demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ganesh.spring_demo.model.Attendance;
import com.ganesh.spring_demo.service.AttendanceService;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/attendance")
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    @GetMapping("/attendance/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable("id") Long id) {
        Attendance attendance = attendanceService.getAttendanceById(id);
        if (attendance == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(attendance);
        }
    }

    @PostMapping("/attendance")
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        Attendance createdAttendance = attendanceService.createAttendance(attendance);
        return ResponseEntity.ok().body(createdAttendance);
    }

    @PutMapping("/attendance/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable("id") Long id, @RequestBody Attendance attendanceDetails) {
        Attendance updatedAttendance = attendanceService.updateAttendance(id, attendanceDetails);
        if (updatedAttendance == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(updatedAttendance);
        }
    }

    @DeleteMapping("/attendance/{id}")
    public ResponseEntity<String> deleteAttendanceById(@PathVariable("id") Long id) {
        boolean deleted = attendanceService.deleteAttendanceById(id);
        if (deleted) {
            return ResponseEntity.ok().body("Attendance record deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/mark-attendance")
    public ResponseEntity<Attendance> markAttendance(
            @RequestParam("studentId") Long studentId,
            @RequestParam("courseId") Long courseId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("present") Boolean present,
            @RequestParam(value = "remarks", required = false) String remarks) {
        
        Attendance attendance = attendanceService.markAttendance(studentId, courseId, date, present, remarks);
        return ResponseEntity.ok().body(attendance);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudentId(@PathVariable("studentId") Long studentId) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByStudentId(studentId);
        return ResponseEntity.ok().body(attendanceList);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Attendance>> getAttendanceByCourseId(@PathVariable("courseId") Long courseId) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByCourseId(courseId);
        return ResponseEntity.ok().body(attendanceList);
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudentAndCourse(
            @PathVariable("studentId") Long studentId,
            @PathVariable("courseId") Long courseId) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByStudentAndCourse(studentId, courseId);
        return ResponseEntity.ok().body(attendanceList);
    }
}