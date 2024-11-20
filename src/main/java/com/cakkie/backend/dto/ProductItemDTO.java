package com.cakkie.backend.dto;

public class ProductItemDTO {
    private int id;
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
    private int isDeleted;

    public ProductItemDTO() {

    }

    public ProductItemDTO(int productItemId, int productID,  String name, String description, String categoryName, long price, String productImage, int productRating, String size, long quantityStock, int isDeleted) {
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
        this.isDeleted = isDeleted;
    }

    public ProductItemDTO(int productID, int productItemId, String name, String description, long price, String productImage, int productRating, String size, long quantityStock, int isDeleted) {
        this.productID = productID;
        this.productItemId = productItemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productImage = productImage;
        this.productRating = productRating;
        this.size = size;
        this.quantityStock = quantityStock;
        this.isDeleted = isDeleted;
    }

    public ProductItemDTO(int id, int productID, int productItemId, int categoryID, String name, String description, String categoryName, long price, String productImage, int productRating, String size, long quantityStock, int isDeleted) {
        this.productID = productID;
        this.productItemId = productItemId;
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
        this.categoryName = categoryName;
        this.price = price;
        this.productImage = productImage;
        this.productRating = productRating;
        this.size = size;
        this.quantityStock = quantityStock;
        this.isDeleted = isDeleted;
    }

    public ProductItemDTO(int productItemId, long price, String productImage, String size, long quantityStock, int isDeleted) {
        this.productItemId = productItemId;
        this.price = price;
        this.productImage = productImage;
        this.size = size;
        this.quantityStock = quantityStock;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getProductItemId() {
        return productItemId;
    }

    public void setProductItemId(int productItemId) {
        this.productItemId = productItemId;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
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

    public long getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(long quantityStock) {
        this.quantityStock = quantityStock;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}

