package com.dev.demo_spring_boot_sql.exception;

public class PhotoRetrievalException extends RuntimeException {
    public PhotoRetrievalException(String message) {
        super(message);
    }
}
