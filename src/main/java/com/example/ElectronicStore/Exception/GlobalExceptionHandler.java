package com.example.ElectronicStore.Exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> globalHandler(MethodArgumentNotValidException ex){
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String,Object> response = new HashMap<>();
        allErrors.stream().forEach(eachError->{
            String message = eachError.getDefaultMessage();
            String field = eachError.getObjectName();
            response.put(field,message);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,Object>> globalValidationHandler(ConstraintViolationException ex){
        List<ConstraintViolation<?>> allErrors  = ex.getConstraintViolations().stream().collect(Collectors.toList());
        Map<String,Object> response = new HashMap<>();
        allErrors.stream().forEach(eachError->{
            String message = eachError.getMessage();
            String field = eachError.getPropertyPath().toString();
            response.put(field,message);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> globalExceptionHandler(Exception ex){
        Map<String,Object> response = new HashMap<>();
        String message = ex.getMessage();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
        String formattedDate = sdf.format(Date.from(Instant.now()));
        response.put("time", formattedDate);
        response.put("message",message);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
