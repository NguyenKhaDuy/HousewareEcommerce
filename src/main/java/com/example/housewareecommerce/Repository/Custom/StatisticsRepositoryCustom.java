package com.example.housewareecommerce.Repository.Custom;

import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepositoryCustom {
    Integer numberOrderAtCurrentMonth();
    Float sumPriceAtCurrentMonth();
    Integer numberSuccessfulOrders();
    Integer numberCancelOrdersAtCurrentMonth();
    Integer numberShippingOrdersAtCurrentMonth();
    Integer numberOrderAtCurrentDate();
    Float sumPriceAtCurrentDate();
}
