package com.cakkie.backend.dto.adminDiscount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ApiResponseDTO {
    private String message;
    private Object createdDiscount;
    private Object mappedCategory;

    public ApiResponseDTO(String message, Object createdDiscount, Object mappedCategory) {
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

    public Object getMappedCategory() {
        return mappedCategory;
    }

    public void setMappedCategory(Object mappedCategory) {
        this.mappedCategory = mappedCategory;
    }
}
