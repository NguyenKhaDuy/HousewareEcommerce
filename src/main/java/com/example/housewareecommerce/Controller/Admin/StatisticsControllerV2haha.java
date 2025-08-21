package com.example.housewareecommerce.Controller.Admin;

import com.example.housewareecommerce.Model.DTO.MonthlyStatisticsDTO;
import com.example.housewareecommerce.Model.DTO.OrderStatisticsDTO;
import com.example.housewareecommerce.Service.OrderStatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsControllerV2haha {

    private final OrderStatisticsService statisticsService;

    public StatisticsControllerV2haha(OrderStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }


    @GetMapping("/general")
    public ResponseEntity<OrderStatisticsDTO> getGeneralStatistics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        OrderStatisticsDTO statistics = statisticsService.getGeneralStatistics(fromDate, toDate);
        return ResponseEntity.ok(statistics);
    }


    @GetMapping("/today")
    public ResponseEntity<OrderStatisticsDTO> getTodayStatistics() {
        return ResponseEntity.ok(statisticsService.getTodayStatistics());
    }


    @GetMapping("/current-month")
    public ResponseEntity<OrderStatisticsDTO> getCurrentMonthStatistics() {
        return ResponseEntity.ok(statisticsService.getCurrentMonthStatistics());
    }


    @GetMapping("/current-year")
    public ResponseEntity<OrderStatisticsDTO> getCurrentYearStatistics() {
        return ResponseEntity.ok(statisticsService.getCurrentYearStatistics());
    }


    @GetMapping("/monthly")
    public ResponseEntity<List<MonthlyStatisticsDTO>> getMonthlyStatistics(
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getYear()}") int year) {

        List<MonthlyStatisticsDTO> statistics = statisticsService.getMonthlyStatistics(year);
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/last-12-months")
    public ResponseEntity<List<MonthlyStatisticsDTO>> getLast12MonthsStatistics() {
        return ResponseEntity.ok(statisticsService.getLast12MonthsStatistics());
    }


    @GetMapping("/top-products")
    public ResponseEntity<Map<String, Long>> getTopSellingProducts(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        Map<String, Long> topProducts = statisticsService.getTopSellingProducts(fromDate, toDate, limit);
        return ResponseEntity.ok(topProducts);
    }
}