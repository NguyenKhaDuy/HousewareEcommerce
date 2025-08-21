package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Model.DTO.MonthlyStatisticsDTO;
import com.example.housewareecommerce.Model.DTO.OrderStatisticsDTO;
import com.example.housewareecommerce.Repository.OrdersRepository;
import com.example.housewareecommerce.Service.OrderStatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrderStatisticsServiceImpl implements OrderStatisticsService {

    private final OrdersRepository orderRepository;

    public OrderStatisticsServiceImpl(OrdersRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public OrderStatisticsDTO getGeneralStatistics(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null) fromDate = LocalDate.now().withDayOfMonth(1); // Đầu tháng hiện tại
        if (toDate == null) toDate = LocalDate.now(); // Hôm nay

        Long totalOrders = orderRepository.countOrdersByDateRange(fromDate, toDate);
        Double totalRevenue = orderRepository.sumRevenueByDateRange(fromDate, toDate);
        Double averageOrderValue = orderRepository.averageOrderValueByDateRange(fromDate, toDate);
        Long totalProductsSold = orderRepository.sumProductsSoldByDateRange(fromDate, toDate);

        OrderStatisticsDTO statistics = new OrderStatisticsDTO(totalOrders, totalRevenue, averageOrderValue, totalProductsSold);
        statistics.setFromDate(fromDate);
        statistics.setToDate(toDate);

        return statistics;
    }

    @Override
    public List<MonthlyStatisticsDTO> getMonthlyStatistics(int year) {
        LocalDate fromDate = LocalDate.of(year, 1, 1);
        return orderRepository.getMonthlyStatistics(fromDate);
    }

    @Override
    public List<MonthlyStatisticsDTO> getLast12MonthsStatistics() {
        LocalDate fromDate = LocalDate.now().minusMonths(12);
        return orderRepository.getMonthlyStatistics(fromDate);
    }

    public Map<String, Long> getTopSellingProducts(LocalDate fromDate, LocalDate toDate, int limit) {
        if (fromDate == null) fromDate = LocalDate.now().withDayOfMonth(1);
        if (toDate == null) toDate = LocalDate.now();

        List<Object[]> results = orderRepository.getTopSellingProducts(fromDate, toDate);

        Map<String, Long> topProducts = new LinkedHashMap<>();
        results.stream()
                .limit(limit)
                .forEach(row -> {
                    String productName = (String) row[0];
                    Long totalSold = ((Number) row[1]).longValue();
                    topProducts.put(productName, totalSold);
                });

        return topProducts;
    }

    @Override
    public OrderStatisticsDTO getTodayStatistics() {
        LocalDate today = LocalDate.now();
        return getGeneralStatistics(today, today);
    }

    @Override
    public OrderStatisticsDTO getCurrentMonthStatistics() {
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate today = LocalDate.now();
        return getGeneralStatistics(firstDayOfMonth, today);
    }

    @Override
    public OrderStatisticsDTO getCurrentYearStatistics() {
        LocalDate firstDayOfYear = LocalDate.now().withDayOfYear(1);
        LocalDate today = LocalDate.now();
        return getGeneralStatistics(firstDayOfYear, today);
    }
}
