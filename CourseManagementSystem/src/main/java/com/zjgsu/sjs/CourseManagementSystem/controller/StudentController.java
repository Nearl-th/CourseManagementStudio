package com.zjgsu.sjs.CourseManagementSystem.controller;

import com.zjgsu.sjs.CourseManagementSystem.controller.Response;
import com.zjgsu.sjs.CourseManagementSystem.model.Student;
import com.zjgsu.sjs.CourseManagementSystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students") // 文档1-26：学生管理API根路径
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    // 1. 创建学生（文档1-28）
    @PostMapping
    public ResponseEntity<Response<Student>> createStudent(@RequestBody Student student) {
        Student savedStudent = studentService.createStudent(student);
        return new ResponseEntity<>(
                Response.success(HttpStatus.CREATED.value(), "Student created successfully", savedStudent),
                HttpStatus.CREATED // 201 Created
        );
    }

    // 2. 查询所有学生（文档1-32）
    @GetMapping
    public ResponseEntity<Response<List<Student>>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(Response.success(students)); // 200 OK
    }

    // 3. 根据ID查询学生（文档1-35）
    @GetMapping("/{id}")
    public ResponseEntity<Response<Student>> getStudentById(@PathVariable String id) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new RuntimeException("学生不存在（ID：" + id + "）"));
        return ResponseEntity.ok(Response.success(student));
    }

    // 4. 更新学生（文档1-39）
    @PutMapping("/{id}")
    public ResponseEntity<Response<Void>> updateStudent(
            @PathVariable String id,
            @RequestBody Student updatedStudent
    ) {
        studentService.updateStudent(id, updatedStudent);
        return ResponseEntity.ok(Response.success()); // 200 OK
    }

    // 5. 删除学生（文档1-42）
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(
                Response.success(HttpStatus.NO_CONTENT.value(), "Student deleted successfully", null),
                HttpStatus.NO_CONTENT // 204 No Content
        );
    }
}