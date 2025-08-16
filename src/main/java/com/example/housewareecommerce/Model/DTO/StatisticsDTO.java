package com.example.housewareecommerce.Model.DTO;

public class StatisticsDTO {
    private Integer numberOrderAtCurrentMonth;
    private Float sumPriceAtCurrentMonth;
    private Integer numberSuccessfulOrders;
    private Integer numberCancelOrdersAtCurrentMonth;
    private Integer numberShippingOrdersAtCurrentMonth;
    private Integer numberOrderAtCurrentDate;
    private Float sumPriceAtCurrentDate;

    public Integer getNumberOrderAtCurrentMonth() {
        return numberOrderAtCurrentMonth;
    }

    public void setNumberOrderAtCurrentMonth(Integer numberOrderAtCurrentMonth) {
        this.numberOrderAtCurrentMonth = numberOrderAtCurrentMonth;
    }

    public Float getSumPriceAtCurrentMonth() {
        return sumPriceAtCurrentMonth;
    }

    public void setSumPriceAtCurrentMonth(Float sumPriceAtCurrentMonth) {
        this.sumPriceAtCurrentMonth = sumPriceAtCurrentMonth;
    }

    public Integer getNumberSuccessfulOrders() {
        return numberSuccessfulOrders;
    }

    public void setNumberSuccessfulOrders(Integer numberSuccessfulOrders) {
        this.numberSuccessfulOrders = numberSuccessfulOrders;
    }

    public Integer getNumberCancelOrdersAtCurrentMonth() {
        return numberCancelOrdersAtCurrentMonth;
    }

    public void setNumberCancelOrdersAtCurrentMonth(Integer numberCancelOrdersAtCurrentMonth) {
        this.numberCancelOrdersAtCurrentMonth = numberCancelOrdersAtCurrentMonth;
    }

    public Integer getNumberShippingOrdersAtCurrentMonth() {
        return numberShippingOrdersAtCurrentMonth;
    }

    public void setNumberShippingOrdersAtCurrentMonth(Integer numberShippingOrdersAtCurrentMonth) {
        this.numberShippingOrdersAtCurrentMonth = numberShippingOrdersAtCurrentMonth;
    }

    public Integer getNumberOrderAtCurrentDate() {
        return numberOrderAtCurrentDate;
    }

    public void setNumberOrderAtCurrentDate(Integer numberOrderAtCurrentDate) {
        this.numberOrderAtCurrentDate = numberOrderAtCurrentDate;
    }

    public Float getSumPriceAtCurrentDate() {
        return sumPriceAtCurrentDate;
    }

    public void setSumPriceAtCurrentDate(Float sumPriceAtCurrentDate) {
        this.sumPriceAtCurrentDate = sumPriceAtCurrentDate;
    }
}
