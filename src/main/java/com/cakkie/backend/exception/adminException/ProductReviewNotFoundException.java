package com.cakkie.backend.exception.adminException;

public class ProductReviewNotFoundException extends RuntimeException {
    public ProductReviewNotFoundException(String message) {
        super(message);
    }
}
