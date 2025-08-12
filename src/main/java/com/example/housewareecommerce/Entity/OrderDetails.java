package com.example.housewareecommerce.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orderdetails")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quality")
    private Long quality;
    @Column(name = "pricequotation")
    private Float priceQuotation;
    @Column(name = "totalamount")
    private Float totalAmount;

    @ManyToOne
    @JoinColumn(name = "productid")
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "orderid")
    private OrderEntity orderEntity;

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

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }
}
