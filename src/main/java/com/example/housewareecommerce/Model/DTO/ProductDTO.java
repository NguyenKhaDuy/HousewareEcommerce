package com.example.housewareecommerce.Model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDTO {
    private Long id;
    private String nameProduct;
    private String description;
    private Float price;
    private Long quantity;
    private Float importPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime created;
    private String categoryName;

    private String statusCode;

    // Images
    private List<byte[]> images = new ArrayList<>();
    private List<String> imagesBase64 = new ArrayList<>();



    public ProductDTO(Long id, String nameProduct, String description, Float price, Long quantity, List<String> imagesBase64, String statusCode) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imagesBase64 = imagesBase64;
        this.statusCode = statusCode;
    }

    public ProductDTO() {
    }

    public List<String> getImagesBase64() {
        return imagesBase64;
    }

    public void setImagesBase64(List<String> imagesBase64) {
        this.imagesBase64 = imagesBase64;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Float getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(Float importPrice) {
        this.importPrice = importPrice;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }
}
