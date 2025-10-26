package com.zjgsu.sjs.CourseManagementSystem.service;

import com.zjgsu.sjs.CourseManagementSystem.model.Course;
import com.zjgsu.sjs.CourseManagementSystem.model.Enrollment;
import com.zjgsu.sjs.CourseManagementSystem.model.Student;
import com.zjgsu.sjs.CourseManagementSystem.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseService courseService;
    private final StudentService studentService;

    // 学生选课（文档1-55）
    public Enrollment enrollCourse(Enrollment enrollment) {
        String courseId = enrollment.getCourseId();
        String studentId = enrollment.getStudentId();

        // 1. 校验课程存在（文档1-70）
        Course course = courseService.getCourseById(courseId)
                .orElseThrow(() -> new RuntimeException("课程不存在（ID：" + courseId + "）"));
        // 2. 校验学生存在（文档1-71：返回404）
        Student student = studentService.getStudentById(studentId)
                .orElseThrow(() -> new RuntimeException("学生不存在（ID：" + studentId + "）"));
        // 3. 校验重复选课（文档1-69）
        if (enrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId)) {
            throw new RuntimeException("学生已选该课程（学生ID：" + studentId + "，课程ID：" + courseId + "）");
        }
        // 4. 校验课程容量（文档1-68）
        if (course.getEnrolled() >= course.getCapacity()) {
            throw new RuntimeException("课程容量已满（当前：" + course.getEnrolled() + "，容量：" + course.getCapacity() + "）");
        }

        // 5. 创建选课记录 + 更新课程已选人数（文档1-72：级联更新）
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        courseService.incrementEnrolled(courseId);
        return savedEnrollment;
    }

    // 学生退课（文档1-59）
    public void dropCourse(String enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("选课记录不存在（ID：" + enrollmentId + "）"));
        // 删除选课记录 + 减少课程已选人数
        enrollmentRepository.deleteById(enrollmentId);
        courseService.decrementEnrolled(enrollment.getCourseId());
    }

    // 查询所有选课记录（文档1-60）
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    // 按课程ID查询选课记录（文档1-62）
    public List<Enrollment> getEnrollmentsByCourseId(String courseId) {
        // 校验课程存在
        if (!courseService.getCourseById(courseId).isPresent()) {
            throw new RuntimeException("课程不存在（ID：" + courseId + "）");
        }
        return enrollmentRepository.findByCourseId(courseId);
    }

    // 按学生ID查询选课记录（文档1-64）
    public List<Enrollment> getEnrollmentsByStudentId(String studentId) {
        // 校验学生存在
        if (!studentService.getStudentById(studentId).isPresent()) {
            throw new RuntimeException("学生不存在（ID：" + studentId + "）");
        }
        return enrollmentRepository.findByStudentId(studentId);
    }
}