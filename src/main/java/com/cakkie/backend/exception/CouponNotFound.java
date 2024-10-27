package com.cakkie.backend.exception;

public class CouponNotFound extends RuntimeException {
    public CouponNotFound(String message) {
        super(message);
    }
}
