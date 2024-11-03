package com.cakkie.backend.exception;

public class ProductReviewNotFoundException extends RuntimeException {
    public ProductReviewNotFoundException(String message) {
        super(message);
    }
}
