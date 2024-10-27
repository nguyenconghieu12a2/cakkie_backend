package com.cakkie.backend.dto;

public class ProductDTO {
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
    private String desInfo;
    private String desTitleName;
    public ProductDTO() {
    }

    public ProductDTO(int productID, String name, String description, long price) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ProductDTO(int productID, String name, String description, String categoryName, long price, String productImage, String size, long quantityStock, String desInfo, String desTitleName) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
        this.productImage = productImage;
        this.size = size;
        this.quantityStock = quantityStock;
        this.desInfo = desInfo;
        this.desTitleName = desTitleName;
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

    public ProductDTO(int id, int productID, int categoryID, String name, String description, String categoryName, long price, String productImage, int productRating, String size, long quantityStock, int isDeleted) {
        this.id = id;
        this.productID = productID;
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

    public ProductDTO( int productID, int productItemId, String name, String description, String categoryName, long price, String productImage, int productRating, String size, long quantityStock, int isDeleted) {
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

    public int getProductItemId() {
        return productItemId;
    }

    public void setProductItemId(int productItemId) {
        this.productItemId = productItemId;
    }

    public String getDesInfo() {
        return desInfo;
    }

    public String getDesTitleName() {
        return desTitleName;
    }

    public void setDesInfo(String desInfo) {
        this.desInfo = desInfo;
    }

    public void setDesTitleName(String desTitleName) {
        this.desTitleName = desTitleName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setProductRating(int productRating) {
        this.productRating = productRating;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setQuantityStock(long quantityStock) {
        this.quantityStock = quantityStock;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public int getProductID() {
        return productID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public long getPrice() {
        return price;
    }

    public String getProductImage() {
        return productImage;
    }

    public int getProductRating() {
        return productRating;
    }

    public String getSize() {
        return size;
    }

    public long getQuantityStock() {
        return quantityStock;
    }

    public int getIsDeleted() {
        return isDeleted;
    }



}
