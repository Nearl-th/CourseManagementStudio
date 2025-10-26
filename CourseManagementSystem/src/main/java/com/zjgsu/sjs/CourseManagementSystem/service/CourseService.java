package com.zjgsu.sjs.CourseManagementSystem.service;

import com.zjgsu.sjs.CourseManagementSystem.model.Course;
import com.zjgsu.sjs.CourseManagementSystem.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    // 创建课程（文档1-18）
    public Course createCourse(Course course) {
        // 校验必填字段（文档1-129要求：缺少必填字段返回400）
        if (course.getCode() == null || course.getTitle() == null
                || course.getInstructor() == null || course.getCapacity() == null) {
            throw new IllegalArgumentException("课程编码、名称、讲师信息、容量为必填字段");
        }
        return courseRepository.save(course);
    }

    // 查询所有课程（文档1-14）
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // 根据ID查询课程（文档1-16）
    public Optional<Course> getCourseById(String id) {
        return courseRepository.findById(id);
    }

    // 更新课程（文档1-22）
    public void updateCourse(String id, Course updatedCourse) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("课程不存在（ID：" + id + "）"));
        // 仅更新允许修改的字段（编码、名称、讲师、安排、容量）
        if (updatedCourse.getCode() != null) {
            existingCourse.setCode(updatedCourse.getCode());
        }
        if (updatedCourse.getTitle() != null) {
            existingCourse.setTitle(updatedCourse.getTitle());
        }
        if (updatedCourse.getInstructor() != null) {
            existingCourse.setInstructor(updatedCourse.getInstructor());
        }
        if (updatedCourse.getScheduleSlot() != null) {
            existingCourse.setScheduleSlot(updatedCourse.getScheduleSlot());
        }
        if (updatedCourse.getCapacity() != null) {
            existingCourse.setCapacity(updatedCourse.getCapacity());
        }
        courseRepository.update(existingCourse);
    }

    // 删除课程（文档1-24）
    public void deleteCourse(String id) {
        if (!courseRepository.findById(id).isPresent()) {
            throw new RuntimeException("课程不存在（ID：" + id + "）");
        }
        courseRepository.deleteById(id);
    }

    // 选课成功后更新已选人数（文档1-72：级联更新）
    public void incrementEnrolled(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("课程不存在（ID：" + courseId + "）"));
        course.setEnrolled(course.getEnrolled() + 1);
        courseRepository.update(course);
    }

    // 退课成功后更新已选人数
    public void decrementEnrolled(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("课程不存在（ID：" + courseId + "）"));
        course.setEnrolled(Math.max(0, course.getEnrolled() - 1)); // 避免负数
        courseRepository.update(course);
    }
}