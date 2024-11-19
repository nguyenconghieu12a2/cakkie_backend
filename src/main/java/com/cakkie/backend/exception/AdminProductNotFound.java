package com.cakkie.backend.exception;

public class AdminProductNotFound extends RuntimeException {
    public AdminProductNotFound(String message) {
        super(message);
    }
}
