package com.zjgsu.sjs.CourseManagementSystem.controller;

import com.zjgsu.sjs.CourseManagementSystem.controller.Response;
import com.zjgsu.sjs.CourseManagementSystem.model.Enrollment;
import com.zjgsu.sjs.CourseManagementSystem.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments") // 文档1-53：选课管理API根路径
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    // 1. 学生选课（文档1-55）
    @PostMapping
    public ResponseEntity<Response<Enrollment>> enrollCourse(@RequestBody Enrollment enrollment) {
        Enrollment savedEnrollment = enrollmentService.enrollCourse(enrollment);
        return new ResponseEntity<>(
                Response.success(HttpStatus.CREATED.value(), "Enrolled successfully", savedEnrollment),
                HttpStatus.CREATED // 201 Created
        );
    }

    // 2. 学生退课（文档1-59）
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> dropCourse(@PathVariable String id) {
        enrollmentService.dropCourse(id);
        return new ResponseEntity<>(
                Response.success(HttpStatus.NO_CONTENT.value(), "Dropped successfully", null),
                HttpStatus.NO_CONTENT // 204 No Content
        );
    }

    // 3. 查询所有选课记录（文档1-60）
    @GetMapping
    public ResponseEntity<Response<List<Enrollment>>> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(Response.success(enrollments)); // 200 OK
    }

    // 4. 按课程ID查询选课记录（文档1-62）
    @GetMapping("/course/{courseId}")
    public ResponseEntity<Response<List<Enrollment>>> getEnrollmentsByCourseId(@PathVariable String courseId) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourseId(courseId);
        return ResponseEntity.ok(Response.success(enrollments));
    }

    // 5. 按学生ID查询选课记录（文档1-64）
    @GetMapping("/student/{studentId}")
    public ResponseEntity<Response<List<Enrollment>>> getEnrollmentsByStudentId(@PathVariable String studentId) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);
        return ResponseEntity.ok(Response.success(enrollments));
    }
}