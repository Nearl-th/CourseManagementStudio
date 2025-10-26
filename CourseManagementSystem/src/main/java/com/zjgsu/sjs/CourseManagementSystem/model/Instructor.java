package com.zjgsu.sjs.CourseManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {
    private String id;       // 讲师ID（如T001）
    private String name;     // 讲师姓名
    private String email;    // 讲师邮箱
}