package com.blumbit.compras_ventas.exception;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blumbit.compras_ventas.common.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse<String>> handleException (Exception ex, HttpServletRequest request){

        return new ResponseEntity<>(ErrorResponse.<String>builder()
        .path(request.getRequestURI())
        .timestamp(new Date().toString())
        .message(ex.getMessage())
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
        .build(),new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse<List<String>>> handleValidationException (MethodArgumentNotValidException ex, HttpServletRequest request){
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": "+ error.getDefaultMessage())
            .collect(Collectors.toList());
        return new ResponseEntity<>(ErrorResponse.<List<String>>builder()
        .path(request.getRequestURI())
        .timestamp(new Date().toString())
        .message(errors)
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
        .build(),new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
