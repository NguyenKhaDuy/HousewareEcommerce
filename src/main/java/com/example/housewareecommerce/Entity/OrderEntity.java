package com.example.housewareecommerce.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "totalprice")
    private Float totalPrice;

    @Column(name = "dateorder")
    private Date dateOrder;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "methodid")
    private PaymentMethodEntity paymentMethodEntity;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "statusid")
    private StatusEntity statusEntity;

    @OneToMany(mappedBy = "orderEntity", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<OrderDetails> orderDetails = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "discountid")
    private DiscountEntity discountEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public PaymentMethodEntity getPaymentMethodEntity() {
        return paymentMethodEntity;
    }

    public void setPaymentMethodEntity(PaymentMethodEntity paymentMethodEntity) {
        this.paymentMethodEntity = paymentMethodEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public StatusEntity getStatusEntity() {
        return statusEntity;
    }

    public void setStatusEntity(StatusEntity statusEntity) {
        this.statusEntity = statusEntity;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public DiscountEntity getDiscountEntity() {
        return discountEntity;
    }

    public void setDiscountEntity(DiscountEntity discountEntity) {
        this.discountEntity = discountEntity;
    }
}
