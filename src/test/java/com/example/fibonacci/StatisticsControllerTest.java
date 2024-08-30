package com.example.fibonacci;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.fibonacci.controller.StatisticsController;
import com.example.fibonacci.dto.FibonacciStatisticsDto;
import com.example.fibonacci.exception.InvalidNumberException;
import com.example.fibonacci.exception.NumberOutOfRangeException;
import com.example.fibonacci.service.StatisticsService;

@SpringBootTest
@AutoConfigureMockMvc
class StatisticsControllerTest {

    @MockBean
    private StatisticsService statisticsService;

    @InjectMocks
    private StatisticsController statisticsController;

    @Autowired
    private MockMvc mockMvc;
    @Test
    void testGetAllStats() throws Exception {
        List<FibonacciStatisticsDto> statsList = new ArrayList<>();
        FibonacciStatisticsDto dto1 = new FibonacciStatisticsDto();
        dto1.setN("1");
        dto1.setCantidadRequests("10");
        FibonacciStatisticsDto dto2 = new FibonacciStatisticsDto();
        dto2.setN("2");
        dto2.setCantidadRequests("20");
        statsList.add(dto1);
        statsList.add(dto2);

        when(statisticsService.getAllStats()).thenReturn(statsList);

        mockMvc.perform(get("/statistics")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].n").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cantidadRequests").value("10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].n").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].cantidadRequests").value("20"));
    }

    @Test
    void testGetStatsByIdValidNumber() throws Exception {
        FibonacciStatisticsDto dto = new FibonacciStatisticsDto();
        dto.setN("1");
        dto.setCantidadRequests("10");

        when(statisticsService.getByPosition("1")).thenReturn(dto);

        mockMvc.perform(get("/statistics/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.n").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cantidadRequests").value("10"));
    }

    @Test
    void testGetStatsByIdInvalidNumberFormat() throws Exception {
        String invalidNumber = "abc";
        mockMvc.perform(get("/statistics/"+invalidNumber))
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidNumberException))
        .andExpect(result -> assertEquals("El valor proporcionado no es un número válido", result.getResolvedException().getMessage()));
    }

    @Test
    void testGetStatsByIdNumberOutOfRange() throws Exception {
        String negativeNumber = "-5";
        mockMvc.perform(get("/statistics/"+negativeNumber))
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof NumberOutOfRangeException))
        .andExpect(result -> assertEquals("El número no puede ser menor a 0", result.getResolvedException().getMessage()));
    }
}
