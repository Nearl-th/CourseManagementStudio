package com.zjgsu.sjs.CourseManagementSystem;

import com.zjgsu.sjs.CourseManagementSystem.model.Course;
import com.zjgsu.sjs.CourseManagementSystem.model.Instructor;
import com.zjgsu.sjs.CourseManagementSystem.model.ScheduleSlot;
import com.zjgsu.sjs.CourseManagementSystem.service.CourseService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class CourseManagementSystemApplication {
	private final CourseService courseService;

	public static void main(String[] args) {
		SpringApplication.run(CourseManagementSystemApplication.class, args);
	}

	// 初始化测试课程（文档Q2要求的@PostConstruct方式）
	@PostConstruct
	public void initTestData() {
		Course testCourse1 = Course.builder()
				.id("001")
				.code("CS101")
				.title("计算机科学导论")
				.instructor(Instructor.builder()
						.id("T001")
						.name("张教授")
						.email("zhang@example.edu.cn")
						.build())
				.scheduleSlot(ScheduleSlot.builder()
						.dayOfWeek("MONDAY")
						.startTime("08:00")
						.endTime("10:00")
						.expectedAttendance(50)
						.build())
				.capacity(60)
				.enrolled(0) // 初始选课人数为0
				.build();
		Course testCourse2 = Course.builder()
				.id("002")
				.code("CS102")
				.title("数字逻辑")
				.instructor(Instructor.builder()
						.id("T002")
						.name("李教授")
						.email("li@example.edu.cn")
						.build())
				.scheduleSlot(ScheduleSlot.builder()
						.dayOfWeek("MONDAY")
						.startTime("08:00")
						.endTime("10:00")
						.expectedAttendance(50)
						.build())
				.capacity(60)
				.enrolled(0) // 初始选课人数为0
				.build();
		Course testCourse3 = Course.builder()
				.id("003")
				.code("CS103")
				.title("数据结构")
				.instructor(Instructor.builder()
						.id("T003")
						.name("张教授")
						.email("zhang@example.edu.cn")
						.build())
				.scheduleSlot(ScheduleSlot.builder()
						.dayOfWeek("TUESDAY")
						.startTime("08:00")
						.endTime("10:00")
						.expectedAttendance(2)
						.build())
				.capacity(60)
				.enrolled(0) // 初始选课人数为0
				.build();
		courseService.createCourse(testCourse1);
		courseService.createCourse(testCourse2);
		courseService.createCourse(testCourse3);
	}
}