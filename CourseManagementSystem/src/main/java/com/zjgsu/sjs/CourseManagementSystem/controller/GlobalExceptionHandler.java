package com.zjgsu.sjs.CourseManagementSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // 文档Q3要求的全局异常处理
public class GlobalExceptionHandler {
    // 处理业务异常（如资源不存在、参数错误）
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response<Void>> handleRuntimeException(RuntimeException e) {
        String message = e.getMessage();
        HttpStatus status;
        // 根据异常信息匹配HTTP状态码（文档1-87）
        if (message.contains("不存在")) {
            status = HttpStatus.NOT_FOUND; // 404
        } else if (message.contains("必填") || message.contains("无效") || message.contains("已存在")) {
            status = HttpStatus.BAD_REQUEST; // 400
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        }
        return new ResponseEntity<>(Response.error(status.value(), message), status);
    }

    // 处理参数校验异常
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(
                Response.error(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}