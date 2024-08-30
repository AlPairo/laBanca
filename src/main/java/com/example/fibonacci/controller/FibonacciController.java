package com.example.fibonacci.controller;

import com.example.fibonacci.exception.InvalidNumberException;
import com.example.fibonacci.exception.NumberOutOfRangeException;
import com.example.fibonacci.service.FibonacciService;
import com.example.fibonacci.service.StatisticsService;
import java.math.BigInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FibonacciController {
    
    private final FibonacciService fibonacciService;
    private final StatisticsService statisticsService;

    
    
    FibonacciController(FibonacciService fibonacciService, StatisticsService statisticsService){
        this.fibonacciService = fibonacciService;
        this.statisticsService = statisticsService;
    }

    @GetMapping("/fibonacci/{n}")
    public String getFibonacci(@PathVariable String n) {
        try {
            BigInteger number = new BigInteger(n);
            if (number.compareTo(BigInteger.ZERO) < 0) {
                throw new NumberOutOfRangeException("El número no puede ser menor a 0");
            }
            System.out.println("updateStatistics "+number);
            statisticsService.updateStatistics(number);
            System.out.println("getFibonacci "+number);
            return fibonacciService.getFibonacci(number);
        } catch (NumberFormatException e) {
            throw new InvalidNumberException("El valor proporcionado no es un número válido");
        }
    }
}