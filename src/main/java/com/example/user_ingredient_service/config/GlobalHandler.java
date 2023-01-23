package com.example.user_ingredient_service.config;

import com.example.user_ingredient_service.dto.ErrorResponse;
import com.example.user_ingredient_service.dto.MultiplyErrorResponse;
import com.example.user_ingredient_service.exception.EntityNotExistException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MultiplyErrorResponse handle(ConstraintViolationException e) {

        final Map<String,String> errorMap = new HashMap<>();

        e.getConstraintViolations().forEach(constraintViolation ->
            constraintViolation.getPropertyPath()
                    .forEach(node -> errorMap.put(constraintViolation.getMessage(), node.getName())));

        final List<Map<String, String>> errors = errorMap
                .entrySet()
                .stream()
                .map(entryString -> Map.of("error", entryString.getKey(),"field_name", entryString.getValue()))
                .toList();

        return MultiplyErrorResponse.builder()
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withMessages(errors)
                .withTimestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(value = {
            EntityNotExistException.class,
            IllegalArgumentException.class,
            AuthenticationException.class,
            ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(Throwable e) {
        return errorResponse(e.getMessage(),HttpStatus.BAD_REQUEST,LocalDateTime.now());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handle(AccessDeniedException e) {
        return errorResponse(e.getMessage(),HttpStatus.FORBIDDEN,LocalDateTime.now());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handle(HttpClientErrorException e) {
        return errorResponse(e.getMessage(),HttpStatus.UNAUTHORIZED,LocalDateTime.now());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(IllegalStateException e) {
        return errorResponse(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR,LocalDateTime.now());
    }

    private ErrorResponse errorResponse(String message,HttpStatus httpStatus,LocalDateTime dateTime) {
        return ErrorResponse.builder()
                .withMessage(message)
                .withTimestamp(dateTime)
                .withHttpStatus(httpStatus)
                .build();
    }
}
