package com.example.housewareecommerce.Model.DTO;

import com.example.housewareecommerce.Entity.OrderEntity;
import com.example.housewareecommerce.Entity.ProductEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class OrdersDetailDTO {
    private Long id;
    private Long quality;
    private Float priceQuotation;
    private Float totalAmount;
    private String nameProduct;
    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuality() {
        return quality;
    }

    public void setQuality(Long quality) {
        this.quality = quality;
    }

    public Float getPriceQuotation() {
        return priceQuotation;
    }

    public void setPriceQuotation(Float priceQuotation) {
        this.priceQuotation = priceQuotation;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }
}
