package com.cakkie.backend.exception.adminLogin;

public class AdminNotFoundException extends RuntimeException{
    public AdminNotFoundException(String message) {
        super(message);
    }
}
