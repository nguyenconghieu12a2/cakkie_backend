package com.cakkie.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductResponse {
    // Getters and setters
    private List<products> productList;
    private List<productsDescription> descriptionList;
    private List<coupons> couponList;

}
