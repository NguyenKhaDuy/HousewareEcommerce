package com.example.housewareecommerce.Model.DTO;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdersDTO {
    private Long id;
    private Float totalPrice;
    private LocalDate dateOrder;
    private String note;
    private String paymentMethod;
    private String userName;
    private String statusCode;
    private String nameDiscount;
    private Float percentDiscount;

    private List<OrdersDetailDTO> ordersDetailDTOS = new ArrayList<>();

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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getNameDiscount() {
        return nameDiscount;
    }

    public void setNameDiscount(String nameDiscount) {
        this.nameDiscount = nameDiscount;
    }

    public Float getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(Float percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public List<OrdersDetailDTO> getOrdersDetailDTOS() {
        return ordersDetailDTOS;
    }

    public void setOrdersDetailDTOS(List<OrdersDetailDTO> ordersDetailDTOS) {
        this.ordersDetailDTOS = ordersDetailDTOS;
    }
}
