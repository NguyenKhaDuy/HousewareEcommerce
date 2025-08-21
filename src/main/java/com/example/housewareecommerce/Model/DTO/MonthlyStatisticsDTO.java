package com.example.housewareecommerce.Model.DTO;

public class MonthlyStatisticsDTO {
    private Integer year;
    private Integer month;
    private Long totalOrders;
    private Double totalRevenue;

    public MonthlyStatisticsDTO(Integer year, Integer month, Long totalOrders, Double totalRevenue) {
        this.year = year;
        this.month = month;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
    }

    public MonthlyStatisticsDTO(Number year, Number month, Number totalOrders, Number totalRevenue) {
        this.year = year != null ? year.intValue() : null;
        this.month = month != null ? month.intValue() : null;
        this.totalOrders = totalOrders != null ? totalOrders.longValue() : 0L;
        this.totalRevenue = totalRevenue != null ? totalRevenue.doubleValue() : 0.0;
    }

    // Default constructor
    public MonthlyStatisticsDTO() {}

    // Getters and Setters
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }

    public Long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(Long totalOrders) { this.totalOrders = totalOrders; }

    public Double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(Double totalRevenue) { this.totalRevenue = totalRevenue; }
}