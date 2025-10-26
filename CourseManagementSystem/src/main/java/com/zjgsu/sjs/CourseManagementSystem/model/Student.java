package com.zjgsu.sjs.CourseManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String id = UUID.randomUUID().toString(); // 系统自动生成UUID（1-46）
    private String studentId; // 学号（全局唯一，如2024001，1-47）
    private String name;      // 姓名（必填，1-48）
    private String major;     // 专业（必填，1-49）
    private Integer grade;    // 入学年份（必填，1-50）
    private String email;     // 邮箱（必填且符合格式，1-51）
    private LocalDateTime createdAt = LocalDateTime.now(); // 系统自动生成时间戳（1-52）
}