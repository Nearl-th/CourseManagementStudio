package com.zjgsu.sjs.CourseManagementSystem.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private Integer code;    // 状态码（如200、404，文档1-87）
    private String message;  // 提示信息（如Success、Course not found）
    private T data;          // 响应数据（成功时返回，错误时为null）

    // 成功响应（无数据）
    public static <T> Response<T> success() {
        return Response.<T>builder()
                .code(200)
                .message("Success")
                .data(null)
                .build();
    }

    // 成功响应（有数据）
    public static <T> Response<T> success(T data) {
        return Response.<T>builder()
                .code(200)
                .message("Success")
                .data(data)
                .build();
    }

    // 成功响应（自定义状态码，如201创建成功）
    public static <T> Response<T> success(Integer code, String message, T data) {
        return Response.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

    // 错误响应
    public static <T> Response<T> error(Integer code, String message) {
        return Response.<T>builder()
                .code(code)
                .message(message)
                .data(null)
                .build();
    }
}