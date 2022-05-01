package com.java.expensetracker.exception;

public class InvalidUserException extends RuntimeException {
    private static final long serialVersionUID = -7490254703386969537L;

    public InvalidUserException() {
        super();
    }

    public InvalidUserException(String message) {
        super(message);
    }

    public InvalidUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUserException(Throwable cause) {
        super(cause);
    }
}
