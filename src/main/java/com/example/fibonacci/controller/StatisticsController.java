package com.example.fibonacci.controller;

import com.example.fibonacci.dto.FibonacciStatisticsDto;
import com.example.fibonacci.exception.InvalidNumberException;
import com.example.fibonacci.exception.NumberOutOfRangeException;
import com.example.fibonacci.service.StatisticsService;
import java.math.BigInteger;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {
    
    private final StatisticsService statisticsService;
    
    StatisticsController(StatisticsService statisticsService){
        this.statisticsService = statisticsService;
    }

    @GetMapping("/statistics")
    public List<FibonacciStatisticsDto> getAllStats() {
        return statisticsService.getAllStats();
    }

    @GetMapping("/statistics/{n}")
    public FibonacciStatisticsDto getStatsById(@PathVariable String n) {
        try {
            BigInteger number = new BigInteger(n);
            if (number.compareTo(BigInteger.ZERO) < 0) {
                throw new NumberOutOfRangeException("El número no puede ser menor a 0");
            }
                return statisticsService.getByPosition(n);
            } catch (NumberFormatException e) {
                throw new InvalidNumberException("El valor proporcionado no es un número válido");
            }
        }
}