package com.zjgsu.sjs.CourseManagementSystem.repository;

import com.zjgsu.sjs.CourseManagementSystem.model.Course;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CourseRepository {
    // 内存存储：key=课程ID，value=课程对象
    private final Map<String, Course> courses = new ConcurrentHashMap<>();

    // 查询所有课程（文档1-14）
    public List<Course> findAll() {
        return new ArrayList<>(courses.values());
    }

    // 根据ID查询课程（文档1-16）
    public Optional<Course> findById(String id) {
        return Optional.ofNullable(courses.get(id));
    }

    // 创建课程（文档1-18）
    public Course save(Course course) {
        courses.put(course.getId(), course);
        return course;
    }

    // 更新课程（文档1-22）
    public void update(Course course) {
        courses.put(course.getId(), course);
    }

    // 删除课程（文档1-24）
    public void deleteById(String id) {
        courses.remove(id);
    }
}