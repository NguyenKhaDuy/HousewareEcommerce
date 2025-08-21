package com.example.housewareecommerce.Model.DTO;

import java.time.LocalDate;

public class OrderStatisticsDTO {
    private Long totalOrders;
    private Double totalRevenue;
    private Double averageOrderValue;
    private Long totalProductsSold;
    private LocalDate fromDate;
    private LocalDate toDate;

    public OrderStatisticsDTO() {}

    public OrderStatisticsDTO(Long totalOrders, Double totalRevenue, Double averageOrderValue, Long totalProductsSold) {
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.averageOrderValue = averageOrderValue;
        this.totalProductsSold = totalProductsSold;
    }

    public Long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(Long totalOrders) { this.totalOrders = totalOrders; }

    public Double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(Double totalRevenue) { this.totalRevenue = totalRevenue; }

    public Double getAverageOrderValue() { return averageOrderValue; }
    public void setAverageOrderValue(Double averageOrderValue) { this.averageOrderValue = averageOrderValue; }

    public Long getTotalProductsSold() { return totalProductsSold; }
    public void setTotalProductsSold(Long totalProductsSold) { this.totalProductsSold = totalProductsSold; }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }
}
