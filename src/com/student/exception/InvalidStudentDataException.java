package com.student.exception;

/**
 * Custom exception class for invalid student data.
 * Thrown when validation fails during student data input.
 */
public class InvalidStudentDataException extends Exception {

    // Serial version UID for serialization
    private static final long serialVersionUID = 1L;

    // Default constructor
    public InvalidStudentDataException() {
        super("Invalid student data provided.");
    }

    // Constructor with custom message
    public InvalidStudentDataException(String message) {
        super(message);
    }

    // Constructor with custom message and cause
    public InvalidStudentDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
