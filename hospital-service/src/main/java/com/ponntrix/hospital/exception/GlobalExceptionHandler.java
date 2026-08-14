package com.ponntrix.hospital.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Global exception handler for the Hospital microservice.
 *
 * This class handles common exceptions thrown by all
 * controllers and services in the application.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles resource-not-found errors.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException exception,
            HttpServletRequest request
    ) {

        log.error(
                "Resource not found: {}",
                exception.getMessage()
        );

        ErrorResponse response = new ErrorResponse();

        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setError("NOT_FOUND");
        response.setMessage(exception.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    /**
     * Handles duplicate resource errors.
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(
            DuplicateResourceException exception,
            HttpServletRequest request
    ) {

        log.error(
                "Duplicate resource: {}",
                exception.getMessage()
        );

        ErrorResponse response = new ErrorResponse();

        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setError("DUPLICATE_RESOURCE");
        response.setMessage(exception.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    /**
     * Handles bad request errors.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException exception,
            HttpServletRequest request
    ) {

        log.error(
                "Bad request: {}",
                exception.getMessage()
        );

        ErrorResponse response = new ErrorResponse();

        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError("BAD_REQUEST");
        response.setMessage(exception.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    /**
     * Handles @Valid validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {

        log.error("Request validation failed");

        List<String> errors = new ArrayList<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.add(
                                error.getField()
                                        + ": "
                                        + error.getDefaultMessage()
                        )
                );

        ErrorResponse response = new ErrorResponse();

        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError("VALIDATION_ERROR");
        response.setMessage(errors.toString());
        response.setPath(request.getRequestURI());

        return ResponseEntity
                .badRequest()
                .body(response);
    }


    /**
     * Handles standard Java illegal argument errors.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException exception,
            HttpServletRequest request
    ) {

        log.error(
                "Illegal argument: {}",
                exception.getMessage()
        );

        ErrorResponse response = new ErrorResponse();

        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError("BAD_REQUEST");
        response.setMessage(exception.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    /**
     * Handles unexpected exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception exception,
            HttpServletRequest request
    ) {

        log.error(
                "Unexpected error occurred",
                exception
        );

        ErrorResponse response = new ErrorResponse();

        response.setTimestamp(OffsetDateTime.now());
        response.setStatus(
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        response.setError("INTERNAL_SERVER_ERROR");
        response.setMessage(
                "An unexpected error occurred"
        );
        response.setPath(request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}

