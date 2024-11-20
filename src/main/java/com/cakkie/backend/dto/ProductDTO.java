package com.cakkie.backend.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private int productID;
    private int productItemId;
    private int categoryID;
    private String name;
    private String description;
    private String categoryName;
    private long price;
    private String productImage;
    private int productRating;
    private String size;
    private long quantityStock;
    private double discount;
    private String subCategoryName;
    private String subsubCategoryName;
    private int isDeleted;
    private double averageRating;
    public ProductDTO() {
    }

    public ProductDTO(int productID, int productItemId, String name, String description,
                      String categoryName, long price, String productImage, int productRating,
                      String size, long quantityStock, double discount, String subCategoryName,
                      String subsubCategoryName, double averageRating) {
        this.productID = productID;
        this.productItemId = productItemId;
        this.name = name;
        this.description = description;
        this.categoryName = categoryName;
        this.price = price;
        this.productImage = productImage;
        this.productRating = productRating;
        this.size = size;
        this.quantityStock = quantityStock;
        this.discount = discount;
        this.subCategoryName = subCategoryName;
        this.subsubCategoryName = subsubCategoryName;
        this.averageRating = averageRating;
    }

    public ProductDTO(int productID, int productItemId, String name, String description, String categoryName, long price, String productImage, int productRating, String size, long quantityStock, double discount, String subCategoryName, String subsubCategoryName) {
        this.productID = productID;
        this.productItemId = productItemId;
        this.name = name;
        this.description = description;
        this.categoryName = categoryName;
        this.price = price;
        this.productImage = productImage;
        this.productRating = productRating;
        this.size = size;
        this.quantityStock = quantityStock;
        this.discount = discount;
        this.subCategoryName = subCategoryName;
        this.subsubCategoryName = subsubCategoryName;
    }

    public ProductDTO(int productID, int productItemId, String name, String description, String categoryName, long price, String productImage, int productRating, String size, long quantityStock, double discount) {
        this.productID = productID;
        this.productItemId = productItemId;
        this.name = name;
        this.description = description;
        this.categoryName = categoryName;
        this.price = price;
        this.productImage = productImage;
        this.productRating = productRating;
        this.size = size;
        this.quantityStock = quantityStock;
        this.discount = discount;

    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public ProductDTO(int productID, String name, String description, long price, String productImage) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productImage = productImage;
    }

    public ProductDTO(int productID, String name, String description, String categoryName, long price) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.categoryName = categoryName;
        this.price = price;
    }


    public ProductDTO(int productID, String name, String description, String categoryName, long price, String productImage, String size, long quantityStock) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.categoryName = categoryName;
        this.price = price;
        this.productImage = productImage;
        this.size = size;
        this.quantityStock = quantityStock;
    }


    public ProductDTO( int productID, int productItemId, String name, String description, String categoryName, long price, String productImage, int productRating, String size, long quantityStock) {
        this.productID = productID;
        this.productItemId = productItemId;
        this.name = name;
        this.description = description;
        this.categoryName = categoryName;
        this.price = price;
        this.productImage = productImage;
        this.productRating = productRating;
        this.size = size;
        this.quantityStock = quantityStock;
    }

}
