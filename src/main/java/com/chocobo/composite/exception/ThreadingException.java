package com.chocobo.composite.exception;

public class ThreadingException extends Exception {

    public ThreadingException() {
        super();
    }

    public ThreadingException(String message) {
        super(message);
    }

    public ThreadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThreadingException(Throwable cause) {
        super(cause);
    }
}
