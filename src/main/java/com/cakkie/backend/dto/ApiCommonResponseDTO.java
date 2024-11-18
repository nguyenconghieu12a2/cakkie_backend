package com.cakkie.backend.dto;

import java.util.List;

public class ApiCommonResponseDTO {
    private String message;
    private Object createdDiscount;
    private List mappedCategory;

    public ApiCommonResponseDTO(String message, Object createdDiscount, List mappedCategory) {
        this.message = message;
        this.createdDiscount = createdDiscount;
        this.mappedCategory = mappedCategory;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getCreatedDiscount() {
        return createdDiscount;
    }

    public void setCreatedDiscount(Object createdDiscount) {
        this.createdDiscount = createdDiscount;
    }

    public List getMappedCategory() {
        return mappedCategory;
    }

    public void setMappedCategory(List mappedCategory) {
        this.mappedCategory = mappedCategory;
    }
}
