package com.cakkie.backend.exception;

public class AdminCategoryNotFound extends RuntimeException {
    public AdminCategoryNotFound(String message) {
        super(message);
    }
}
