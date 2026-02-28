package com.student.exception;

public class InvalidStudentDataException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidStudentDataException() {
        super("Invalid student data provided.");
    }

    public InvalidStudentDataException(String message) {
        super(message);
    }

    public InvalidStudentDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
