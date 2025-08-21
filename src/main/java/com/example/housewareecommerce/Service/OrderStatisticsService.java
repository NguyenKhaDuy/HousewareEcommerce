package com.example.housewareecommerce.Service;

import com.example.housewareecommerce.Model.DTO.MonthlyStatisticsDTO;
import com.example.housewareecommerce.Model.DTO.OrderStatisticsDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderStatisticsService {

    OrderStatisticsDTO getGeneralStatistics(LocalDate fromDate, LocalDate toDate);

    public List<MonthlyStatisticsDTO> getMonthlyStatistics(int year);

    public List<MonthlyStatisticsDTO> getLast12MonthsStatistics();

    public Map<String, Long> getTopSellingProducts(LocalDate fromDate, LocalDate toDate, int limit);

    public OrderStatisticsDTO getTodayStatistics();

    public OrderStatisticsDTO getCurrentMonthStatistics();

    public OrderStatisticsDTO getCurrentYearStatistics();
}
