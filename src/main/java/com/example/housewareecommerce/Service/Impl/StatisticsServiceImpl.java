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
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        try {
            Integer numberOrderAtMonth = statisticsRepository.numberOrderAtCurrentMonth();
            Float sumPriceAtMonth = statisticsRepository.sumPriceAtCurrentMonth();
            Integer numberSuccessfulOrders = statisticsRepository.numberSuccessfulOrders();
            Integer numberCancelOrdersAtCurrentMonth = statisticsRepository.numberCancelOrdersAtCurrentMonth();
            Integer numberShippingOrdersAtCurrentMonth = statisticsRepository.numberShippingOrdersAtCurrentMonth();
            Integer numberOrderAtCurrentDate = statisticsRepository.numberOrderAtCurrentDate();
            Float sumPriceAtCurrentDate = statisticsRepository.sumPriceAtCurrentDate();

            statisticsDTO.setNumberOrderAtCurrentMonth(numberOrderAtMonth != null ? numberOrderAtMonth : 0);
            statisticsDTO.setSumPriceAtCurrentMonth(sumPriceAtMonth != null ? sumPriceAtMonth : 0f);
            statisticsDTO.setNumberSuccessfulOrders(numberSuccessfulOrders != null ? numberSuccessfulOrders : 0);
            statisticsDTO.setNumberCancelOrdersAtCurrentMonth(numberCancelOrdersAtCurrentMonth != null ? numberCancelOrdersAtCurrentMonth : 0);
            statisticsDTO.setNumberShippingOrdersAtCurrentMonth(numberShippingOrdersAtCurrentMonth != null ? numberShippingOrdersAtCurrentMonth : 0);
            statisticsDTO.setNumberOrderAtCurrentDate(numberOrderAtCurrentDate != null ? numberOrderAtCurrentDate : 0);
            statisticsDTO.setSumPriceAtCurrentDate(sumPriceAtCurrentDate != null ? sumPriceAtCurrentDate : 0f);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            statisticsDTO.setNumberOrderAtCurrentMonth(0);
            statisticsDTO.setSumPriceAtCurrentMonth(0f);
            statisticsDTO.setNumberSuccessfulOrders(0);
            statisticsDTO.setNumberCancelOrdersAtCurrentMonth(0);
            statisticsDTO.setNumberShippingOrdersAtCurrentMonth(0);
            statisticsDTO.setNumberOrderAtCurrentDate(0);
            statisticsDTO.setSumPriceAtCurrentDate(0f);
        }
        return statisticsDTO;
    }

}
