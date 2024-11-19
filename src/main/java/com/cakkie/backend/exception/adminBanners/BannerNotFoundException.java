package com.cakkie.backend.exception.adminBanners;

public class BannerNotFoundException extends RuntimeException {
    public BannerNotFoundException(String message) {
        super(message);
    }
}
