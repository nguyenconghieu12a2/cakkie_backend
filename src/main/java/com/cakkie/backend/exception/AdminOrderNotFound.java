package com.cakkie.backend.exception;

public class AdminOrderNotFound extends RuntimeException {
    public AdminOrderNotFound(String message) {
        super(message);
    }
}
