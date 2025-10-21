package com.api.demo.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("error", "User Not Found");
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("status", HttpStatus.NOT_FOUND.value());
    errorResponse.put("timestamp", LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Map<String, Object>> handleTypeMismatch(
      MethodArgumentTypeMismatchException ex) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("error", "Invalid Input");
    errorResponse.put("message", "Invalid ID format: " + ex.getValue());
    errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
    errorResponse.put("timestamp", LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationErrors(
      MethodArgumentNotValidException ex) {
    Map<String, Object> errorResponse = new HashMap<>();
    Map<String, String> fieldErrors = new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

    errorResponse.put("error", "Validation Failed");
    errorResponse.put("message", "Invalid input data");
    errorResponse.put("fieldErrors", fieldErrors);
    errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
    errorResponse.put("timestamp", LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, Object>> handleJsonParseError(
      HttpMessageNotReadableException ex) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("error", "Invalid JSON");
    errorResponse.put("message", "Invalid input data format");
    errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
    errorResponse.put("timestamp", LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("error", "Internal Server Error");
    errorResponse.put("message", "An unexpected error occurred");
    errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    errorResponse.put("timestamp", LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
