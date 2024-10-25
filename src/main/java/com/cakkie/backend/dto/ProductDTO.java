package com.cakkie.backend.dto;

public class ProductDTO {
    private int proId;
    private int categoryId;
    private String productName;
    private String description;
    private String productImage;
    private int productRating;
    private String size;
    private Long quantity;
    private String productItemImage;
    private Long price;

    public ProductDTO(int proId, int categoryId, String productName, String description, String productImage, int productRating, String size, Long quantity, String productItemImage, Long price) {
        this.proId = proId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.description = description;
        this.productImage = productImage;
        this.productRating = productRating;
        this.size = size;
        this.quantity = quantity;
        this.productItemImage = productItemImage;
        this.price = price;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getProductRating() {
        return productRating;
    }

    public void setProductRating(int productRating) {
        this.productRating = productRating;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getProductItemImage() {
        return productItemImage;
    }

    public void setProductItemImage(String productItemImage) {
        this.productItemImage = productItemImage;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
