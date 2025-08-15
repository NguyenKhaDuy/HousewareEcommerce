package com.example.housewareecommerce.Model.Request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdersRequest {
    private Long id;
    private Float totalPrice;
    private LocalDate dateOrder;
    private String note;
    private Long paymentMethodId;
    private Long userId;
    private Long statusId;
    private String discoutCode;
    private List<OrdersDetailsRequest> ordersDetailsRequests = new ArrayList<>();

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

    public LocalDate getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(LocalDate dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getDiscoutCode() {
        return discoutCode;
    }

    public void setDiscoutCode(String discoutCode) {
        this.discoutCode = discoutCode;
    }

    public List<OrdersDetailsRequest> getOrdersDetailsRequests() {
        return ordersDetailsRequests;
    }

    public void setOrdersDetailsRequests(List<OrdersDetailsRequest> ordersDetailsRequests) {
        this.ordersDetailsRequests = ordersDetailsRequests;
    }
}
