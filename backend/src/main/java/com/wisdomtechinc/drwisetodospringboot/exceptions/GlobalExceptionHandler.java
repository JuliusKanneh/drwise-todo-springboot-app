package com.wisdomtechinc.drwisetodospringboot.exceptions;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionHandler {
    
    @ExceptionHandler(TodoItemNotFoundException.class)
    public ProblemDetail handleNotFound(TodoItemNotFoundException ex) {
       return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                    fieldError -> fieldError.getField(),
                    fieldError -> fieldError.getDefaultMessage()
                ));
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail( //
            HttpStatus.BAD_REQUEST, //
            "One or more fields failed validation");
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }
}
