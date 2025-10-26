package com.zjgsu.sjs.CourseManagementSystem.service;

import com.zjgsu.sjs.CourseManagementSystem.model.Student;
import com.zjgsu.sjs.CourseManagementSystem.repository.EnrollmentRepository;
import com.zjgsu.sjs.CourseManagementSystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    // 邮箱格式正则（文档1-31要求：验证邮箱格式）
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    // 创建学生（文档1-28）
    public Student createStudent(Student student) {
        // 1. 校验必填字段（文档1-31）
        if (!StringUtils.hasText(student.getStudentId()) || !StringUtils.hasText(student.getName())
                || !StringUtils.hasText(student.getMajor()) || student.getGrade() == null
                || !StringUtils.hasText(student.getEmail())) {
            throw new IllegalArgumentException("学号、姓名、专业、入学年份、邮箱为必填字段");
        }
        // 2. 校验学号唯一性（文档1-31）
        if (studentRepository.existsByStudentId(student.getStudentId())) {
            throw new IllegalArgumentException("学号已存在：" + student.getStudentId());
        }
        // 3. 校验邮箱格式（文档1-31）
        if (!EMAIL_PATTERN.matcher(student.getEmail()).matches()) {
            throw new IllegalArgumentException("邮箱格式无效：" + student.getEmail());
        }
        return studentRepository.save(student);
    }

    // 查询所有学生（文档1-32）
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // 根据ID查询学生（文档1-35）
    public Optional<Student> getStudentById(String id) {
        return studentRepository.findById(id);
    }

    // 根据学号查询学生（用于选课验证）
    public Optional<Student> getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    // 更新学生（文档1-39）
    public void updateStudent(String id, Student updatedStudent) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("学生不存在（ID：" + id + "）"));
        // 1. 校验必填字段（若更新则需符合要求）
        if (updatedStudent.getStudentId() != null && !StringUtils.hasText(updatedStudent.getStudentId())) {
            throw new IllegalArgumentException("学号不能为空");
        }
        if (updatedStudent.getEmail() != null && !EMAIL_PATTERN.matcher(updatedStudent.getEmail()).matches()) {
            throw new IllegalArgumentException("邮箱格式无效：" + updatedStudent.getEmail());
        }
        // 2. 校验学号唯一性（若学号变更）
        if (updatedStudent.getStudentId() != null && !updatedStudent.getStudentId().equals(existingStudent.getStudentId())
                && studentRepository.existsByStudentId(updatedStudent.getStudentId())) {
            throw new IllegalArgumentException("学号已存在：" + updatedStudent.getStudentId());
        }
        // 3. 更新字段（忽略id和createdAt）
        if (updatedStudent.getStudentId() != null) {
            existingStudent.setStudentId(updatedStudent.getStudentId());
        }
        if (updatedStudent.getName() != null) {
            existingStudent.setName(updatedStudent.getName());
        }
        if (updatedStudent.getMajor() != null) {
            existingStudent.setMajor(updatedStudent.getMajor());
        }
        if (updatedStudent.getGrade() != null) {
            existingStudent.setGrade(updatedStudent.getGrade());
        }
        if (updatedStudent.getEmail() != null) {
            existingStudent.setEmail(updatedStudent.getEmail());
        }
        studentRepository.update(existingStudent);
    }

    // 删除学生（文档1-42：需检查选课记录）
    public void deleteStudent(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("学生不存在（ID：" + id + "）"));
        // 检查是否有活跃选课记录（文档1-44）
        if (!enrollmentRepository.findByStudentId(id).isEmpty()) {
            throw new RuntimeException("无法删除：该学生存在选课记录");
        }
        studentRepository.deleteById(id);
    }
}