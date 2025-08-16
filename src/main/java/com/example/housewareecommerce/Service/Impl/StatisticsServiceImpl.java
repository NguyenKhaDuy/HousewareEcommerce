package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Model.DTO.StatisticsDTO;
import com.example.housewareecommerce.Repository.StatisticsRepository;
import com.example.housewareecommerce.Service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    StatisticsRepository statisticsRepository;
    @Override
    public StatisticsDTO statistic() {
        Integer numberOrderAtMonth = statisticsRepository.numberOrderAtCurrentMonth();
        Float sumPriceAtMonth = statisticsRepository.sumPriceAtCurrentMonth();
        Integer numberSuccessfulOrders = statisticsRepository.numberSuccessfulOrders();
        Integer numberCancelOrdersAtCurrentMonth = statisticsRepository.numberCancelOrdersAtCurrentMonth();
        Integer numberShippingOrdersAtCurrentMonth = statisticsRepository.numberShippingOrdersAtCurrentMonth();
        Integer numberOrderAtCurrentDate = statisticsRepository.numberOrderAtCurrentDate();
        Float sumPriceAtCurrentDate = statisticsRepository.sumPriceAtCurrentDate();
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setNumberOrderAtCurrentMonth(numberOrderAtMonth);
        statisticsDTO.setSumPriceAtCurrentMonth(sumPriceAtMonth);
        statisticsDTO.setNumberSuccessfulOrders(numberSuccessfulOrders);
        statisticsDTO.setNumberCancelOrdersAtCurrentMonth(numberCancelOrdersAtCurrentMonth);
        statisticsDTO.setNumberShippingOrdersAtCurrentMonth(numberShippingOrdersAtCurrentMonth);
        statisticsDTO.setNumberOrderAtCurrentDate(numberOrderAtCurrentDate);
        statisticsDTO.setSumPriceAtCurrentDate(sumPriceAtCurrentDate);
        return statisticsDTO;
    }
}
