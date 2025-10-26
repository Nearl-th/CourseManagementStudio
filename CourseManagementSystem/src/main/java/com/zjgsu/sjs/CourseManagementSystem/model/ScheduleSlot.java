package com.zjgsu.sjs.CourseManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSlot {
    private String dayOfWeek;        // 星期（如MONDAY）
    private String startTime;        // 开始时间（如08:00）
    private String endTime;          // 结束时间（如10:00）
    private Integer expectedAttendance; // 预计出勤人数
}