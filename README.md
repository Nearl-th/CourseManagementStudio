# 课程管理系统 (Course Management System)

## 项目概述

本项目是一个基于 Spring Boot 的课程管理系统，支持学生、课程的管理以及选课、退课等核心功能。系统采用 RESTful API 设计，提供简洁的接口供前端调用，实现了课程容量限制、重复选课校验、数据合法性验证等关键业务逻辑。

## 功能特点

- **学生管理**：支持学生信息的创建、查询、更新和删除，包含学号唯一性校验、邮箱格式验证等。
- **课程管理**：支持课程信息的创建（含容量设置）、查询、更新和删除，初始化时自动加载测试课程数据。
- **选课管理**：支持学生选课（含课程存在性、学生存在性、容量限制、重复选课校验）和退课操作，选课成功后自动更新课程已选人数。
- **异常处理**：统一处理参数校验、数据不存在等异常，返回标准化的错误响应。

## 环境要求

- **Java 版本**：JDK 23
- **构建工具**：Maven 3.6+（项目内置`mvnw`，无需手动安装）
- **Spring Boot 版本**：3.5.6

## 快速开始

### 1. 克隆仓库

```bash
git clone https://github.com/Nearl-th/CourseManagementStudio
cd CourseManagementSystem
```

### 2. 构建项目

使用内置的 Maven wrapper 构建：

```bash
# Windows
mvnw clean package
```

### 3. 运行应用

```bash
# Windows
java -jar target/course-management-system-1.0.0.jar
```

应用将在`http://localhost:8080`启动。

## API 接口文档

### 学生管理

| 功能         | 请求方法 | URL                  | 请求体示例                                                   | 响应说明                   |
| ------------ | -------- | -------------------- | ------------------------------------------------------------ | -------------------------- |
| 创建学生     | POST     | `/api/students`      | `{"studentId":"S001","name":"张三","major":"计算机科学","grade":2024,"email":"s001@example.com"}` | 成功返回 201，包含学生信息 |
| 更新学生     | PUT      | `/api/students/{id}` | `{"major":"软件工程","email":"s001_new@example.com"}`（仅含需更新字段） | 成功返回 200               |
| 删除学生     | DELETE   | `/api/students/{id}` | 无                                                           | 成功返回 204（No Content） |
| 查询学生列表 | GET      | `/api/students`      | 无                                                           | 返回学生列表               |

### 课程管理

| 功能         | 请求方法 | URL                 | 请求体示例                                                   | 响应说明                   |
| ------------ | -------- | ------------------- | ------------------------------------------------------------ | -------------------------- |
| 创建课程     | POST     | `/api/courses`      | `{"code":"CS104","title":"小型研讨会","instructor":{"id":"T004","name":"王教授"},"capacity":2}` | 成功返回 201，包含课程信息 |
| 更新课程     | PUT      | `/api/courses/{id}` | `{"title":"高级研讨会","capacity":3}`（仅含需更新字段）      | 成功返回 200               |
| 删除课程     | DELETE   | `/api/courses/{id}` | 无                                                           | 成功返回 204（No Content） |
| 查询课程列表 | GET      | `/api/courses`      | 无                                                           | 返回课程列表               |

### 选课管理

| 功能     | 请求方法 | URL                     | 请求体示例                              | 响应说明                   |
| -------- | -------- | ----------------------- | --------------------------------------- | -------------------------- |
| 学生选课 | POST     | `/api/enrollments`      | `{"courseId":"001","studentId":"S001"}` | 成功返回 201，包含选课记录 |
| 学生退课 | DELETE   | `/api/enrollments/{id}` | 无                                      | 成功返回 204（No Content） |

## 项目结构

```plaintext
src/main/java/com/zjgsu/sjs/CourseManagementSystem/
├── model/            # 实体类（Student、Course、Instructor等）
├── repository/       # 数据访问层（模拟存储）
├── service/          # 业务逻辑层（包含校验、数据处理）
├── controller/       # API接口层（处理HTTP请求）
├── CourseManagementSystemApplication.java  # 应用入口（含初始化数据）
```