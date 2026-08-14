package com.ponntrix.hospital.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

/**
 * Standard error response returned by Hospital APIs.
 */
@Getter
@Setter
public class ErrorResponse {

    /**
     * Time when the error occurred.
     */
    private OffsetDateTime timestamp;

    /**
     * HTTP status code.
     */
    private int status;

    /**
     * HTTP error name.
     */
    private String error;

    /**
     * Application-specific error message.
     */
    private String message;

    /**
     * API path where the error occurred.
     */
    private String path;
}