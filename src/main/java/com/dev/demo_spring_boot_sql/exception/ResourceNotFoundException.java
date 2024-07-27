package com.dev.demo_spring_boot_sql.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super((message));
    }
}
