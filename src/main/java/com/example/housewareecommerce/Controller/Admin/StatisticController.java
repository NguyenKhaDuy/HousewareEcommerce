package com.example.housewareecommerce.Controller.Admin;

import com.example.housewareecommerce.Model.DTO.StatisticsDTO;
import com.example.housewareecommerce.Service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {
    @Autowired
    StatisticsService statisticsService;
    @GetMapping(value = "/statistics")
    public ResponseEntity<?> statistics(){
        StatisticsDTO statisticsDTO = statisticsService.statistic();
        return new ResponseEntity<>(statisticsDTO, HttpStatus.OK);
    }
}
