package com.vili.demo.core.controller;

import com.vili.demo.core.exception.MultipleApiError;
import com.vili.demo.core.exception.SingleApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

public class CoreControllerAdvice {

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<SingleApiError> propertyReference(PropertyReferenceException e, HttpServletRequest request) {
        String name = "Property not found";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        SingleApiError err = new SingleApiError(Instant.now(), status.value(), name, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<SingleApiError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        String name = "Validation error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        MultipleApiError err = MultipleApiError.multipleErrorBuilder()
                .timestamp(Instant.now())
                .status(status.value())
                .name(name)
                .path(request.getRequestURI())
                .build();

        for (FieldError x : e.getBindingResult().getFieldErrors()) {
            err.add(x.getField(), x.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<SingleApiError> httpMessageNotReadable(HttpMessageNotReadableException e, HttpServletRequest request) {
        String name = "Deserialization error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        MultipleApiError err = MultipleApiError.multipleErrorBuilder()
                .timestamp(Instant.now())
                .status(status.value())
                .name(name)
                .path(request.getRequestURI())
                .build();

        String errorMsg = e.getMessage();
        String simpleMsg = errorMsg.split(";")[0];
        int fieldNameStart = errorMsg.indexOf("DTO[") + 5;
        int fieldNameEnd = errorMsg.indexOf("\"]", fieldNameStart);
        String field = errorMsg.substring(fieldNameStart, fieldNameEnd);
        err.add(field, simpleMsg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
