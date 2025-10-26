package com.zjgsu.sjs.CourseManagementSystem.controller;

import com.zjgsu.sjs.CourseManagementSystem.controller.Response;
import com.zjgsu.sjs.CourseManagementSystem.model.Course;
import com.zjgsu.sjs.CourseManagementSystem.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses") // 文档1-12：课程管理API根路径
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    // 1. 创建课程（文档1-18）
    @PostMapping
    public ResponseEntity<Response<Course>> createCourse(@RequestBody Course course) {
        Course savedCourse = courseService.createCourse(course);
        return new ResponseEntity<>(
                Response.success(HttpStatus.CREATED.value(), "Course created successfully", savedCourse),
                HttpStatus.CREATED // 201 Created（文档1-89）
        );
    }

    // 2. 查询所有课程（文档1-14）
    @GetMapping
    public ResponseEntity<Response<List<Course>>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(Response.success(courses)); // 200 OK（文档1-88）
    }

    // 3. 根据ID查询课程（文档1-16）
    @GetMapping("/{id}")
    public ResponseEntity<Response<Course>> getCourseById(@PathVariable String id) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new RuntimeException("课程不存在（ID：" + id + "）"));
        return ResponseEntity.ok(Response.success(course));
    }

    // 4. 更新课程（文档1-22）
    @PutMapping("/{id}")
    public ResponseEntity<Response<Void>> updateCourse(
            @PathVariable String id,
            @RequestBody Course updatedCourse
    ) {
        courseService.updateCourse(id, updatedCourse);
        return ResponseEntity.ok(Response.success()); // 200 OK（文档1-88）
    }

    // 5. 删除课程（文档1-24）
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(
                Response.success(HttpStatus.NO_CONTENT.value(), "Course deleted successfully", null),
                HttpStatus.NO_CONTENT // 204 No Content（文档1-90）
        );
    }
}