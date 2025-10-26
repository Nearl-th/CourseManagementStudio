package com.zjgsu.sjs.CourseManagementSystem.repository;

import com.zjgsu.sjs.CourseManagementSystem.model.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EnrollmentRepository {
    private final Map<String, Enrollment> enrollments = new ConcurrentHashMap<>();

    // 查询所有选课记录（文档1-60）
    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments.values());
    }

    // 根据ID查询选课记录（文档1-59）
    public Optional<Enrollment> findById(String id) {
        return Optional.ofNullable(enrollments.get(id));
    }

    // 按课程ID查询选课记录（文档1-62）
    public List<Enrollment> findByCourseId(String courseId) {
        return enrollments.values().stream()
                .filter(enrollment -> courseId.equals(enrollment.getCourseId()))
                .collect(Collectors.toList());
    }

    // 按学生ID查询选课记录（文档1-64）
    public List<Enrollment> findByStudentId(String studentId) {
        return enrollments.values().stream()
                .filter(enrollment -> studentId.equals(enrollment.getStudentId()))
                .collect(Collectors.toList());
    }

    // 校验学生是否已选该课程（文档1-69）
    public boolean existsByCourseIdAndStudentId(String courseId, String studentId) {
        return enrollments.values().stream()
                .anyMatch(enrollment -> courseId.equals(enrollment.getCourseId())
                        && studentId.equals(enrollment.getStudentId()));
    }

    // 创建选课记录（文档1-55）
    public Enrollment save(Enrollment enrollment) {
        enrollments.put(enrollment.getId(), enrollment);
        return enrollment;
    }

    // 删除选课记录（文档1-59）
    public void deleteById(String id) {
        enrollments.remove(id);
    }

    // 按学生ID删除所有选课记录（用于学生删除后的级联处理）
    public void deleteByStudentId(String studentId) {
        List<String> idsToDelete = enrollments.values().stream()
                .filter(enrollment -> studentId.equals(enrollment.getStudentId()))
                .map(Enrollment::getId)
                .collect(Collectors.toList());
        idsToDelete.forEach(enrollments::remove);
    }
}