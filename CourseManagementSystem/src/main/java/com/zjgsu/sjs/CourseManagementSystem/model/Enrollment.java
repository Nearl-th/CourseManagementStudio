package com.zjgsu.sjs.CourseManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    private String id = UUID.randomUUID().toString(); // 选课记录ID
    private String courseId; // 课程ID（关联Course）
    private String studentId; // 学生ID（关联Student）
}