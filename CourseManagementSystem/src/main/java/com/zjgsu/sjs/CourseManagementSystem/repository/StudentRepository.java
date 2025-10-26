package com.zjgsu.sjs.CourseManagementSystem.repository;

import com.zjgsu.sjs.CourseManagementSystem.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StudentRepository {
    private final Map<String, Student> students = new ConcurrentHashMap<>();
    // 额外存储学号与学生ID的映射，用于校验学号唯一性（文档1-31）
    private final Map<String, String> studentIdToUserId = new ConcurrentHashMap<>();

    // 查询所有学生（文档1-32）
    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }

    // 根据ID查询学生（文档1-35）
    public Optional<Student> findById(String id) {
        return Optional.ofNullable(students.get(id));
    }

    // 根据学号查询学生（用于校验唯一性和选课验证）
    public Optional<Student> findByStudentId(String studentId) {
        String userId = studentIdToUserId.get(studentId);
        return userId != null ? findById(userId) : Optional.empty();
    }

    // 创建学生（文档1-28）
    public Student save(Student student) {
        students.put(student.getId(), student);
        studentIdToUserId.put(student.getStudentId(), student.getId());
        return student;
    }

    // 更新学生（文档1-39）
    public void update(Student student) {
        // 若学号变更，需更新学号映射
        Student oldStudent = students.get(student.getId());
        if (!oldStudent.getStudentId().equals(student.getStudentId())) {
            studentIdToUserId.remove(oldStudent.getStudentId());
            studentIdToUserId.put(student.getStudentId(), student.getId());
        }
        students.put(student.getId(), student);
    }

    // 删除学生（文档1-42）
    public void deleteById(String id) {
        Student student = students.get(id);
        if (student != null) {
            studentIdToUserId.remove(student.getStudentId());
            students.remove(id);
        }
    }

    // 校验学号是否已存在（文档1-31）
    public boolean existsByStudentId(String studentId) {
        return studentIdToUserId.containsKey(studentId);
    }
}