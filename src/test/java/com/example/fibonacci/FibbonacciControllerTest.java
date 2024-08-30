package com.example.fibonacci;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.fibonacci.controller.FibonacciController;
import com.example.fibonacci.dto.FibonacciNumberDto;
import com.example.fibonacci.exception.InvalidNumberException;
import com.example.fibonacci.exception.NumberOutOfRangeException;
import com.example.fibonacci.service.FibonacciService;
import com.example.fibonacci.service.StatisticsService;

@SpringBootTest
@AutoConfigureMockMvc
class FibbonacciControllerTest {

    @MockBean
    private FibonacciService fibonacciService;

    @MockBean
    private StatisticsService statisticsService;

    @InjectMocks
    private FibonacciController fibonacciController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetFibonacciWithValidNumber() throws Exception {
        String validNumber = "10";
        String validResponse = "55";
        FibonacciNumberDto expectedFibonacciResult = new FibonacciNumberDto(validNumber,validResponse);

        when(fibonacciService.getFibonacci(new BigInteger(validNumber))).thenReturn(expectedFibonacciResult);
        mockMvc.perform(get("/fibonacci/" + validNumber)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.n").value("10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").value("55"));
        verify(statisticsService, times(1)).updateStatistics(new BigInteger(validNumber));
        verify(fibonacciService, times(1)).getFibonacci(new BigInteger(validNumber));
    }

    @Test
    void testGetFibonacciWithNegativeNumber() throws Exception {
        String negativeNumber = "-5";

        mockMvc.perform(get("/fibonacci/" + negativeNumber))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NumberOutOfRangeException))
                .andExpect(result -> assertEquals("El número no puede ser menor a 0", result.getResolvedException().getMessage()));
    }

    @Test
    void testGetFibonacciWithInvalidNumber() throws Exception {
        String invalidNumber = "abc";

        mockMvc.perform(get("/fibonacci/" + invalidNumber))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidNumberException))
                .andExpect(result -> assertEquals("El valor proporcionado no es un número válido", result.getResolvedException().getMessage()));
    }
}