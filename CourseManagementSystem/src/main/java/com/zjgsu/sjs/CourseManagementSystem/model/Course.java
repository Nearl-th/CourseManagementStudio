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
public class Course {
    private String id = UUID.randomUUID().toString(); // 系统自动生成UUID
    private String code;          // 课程编码（如CS101）
    private String title;         // 课程名称
    private Instructor instructor; // 讲师信息
    private ScheduleSlot scheduleSlot; // 课程安排
    private Integer capacity;     // 课程容量（文档1-68要求）
    private Integer enrolled = 0; // 已选课人数（文档1-72要求自动更新）
}