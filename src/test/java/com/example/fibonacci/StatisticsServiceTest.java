package com.example.fibonacci;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.fibonacci.dto.FibonacciStatisticsDto;
import com.example.fibonacci.entity.FibonacciStatistics;
import com.example.fibonacci.repository.FibonacciStatisticsRepository;
import com.example.fibonacci.service.StatisticsService;

@SpringBootTest
class StatisticsServiceTest {

    @InjectMocks
    private StatisticsService statisticsService;

    @Mock
    private FibonacciStatisticsRepository statisticsRepository;

    @Test
    void testGetAllStats() {
        List<FibonacciStatistics> statisticsList = new ArrayList<>();
        FibonacciStatistics stat1 = new FibonacciStatistics();
        stat1.setPosition(1L);
        stat1.setRequestCount(10L);
        FibonacciStatistics stat2 = new FibonacciStatistics();
        stat2.setPosition(2L);
        stat2.setRequestCount(20L);
        statisticsList.add(stat1);
        statisticsList.add(stat2);

        when(statisticsRepository.findAll()).thenReturn(statisticsList);

        List<FibonacciStatisticsDto> result = statisticsService.getAllStats();
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getN());
        assertEquals("10", result.get(0).getCantidadRequests());
        assertEquals("2", result.get(1).getN());
        assertEquals("20", result.get(1).getCantidadRequests());
    }

    @Test
    void testGetByPositionFound() {
        FibonacciStatistics stat = new FibonacciStatistics();
        stat.setPosition(1L);
        stat.setRequestCount(10L);

        when(statisticsRepository.findByPosition(1L)).thenReturn(Optional.of(stat));

        FibonacciStatisticsDto result = statisticsService.getByPosition("1");
        assertEquals("1", result.getN());
        assertEquals("10", result.getCantidadRequests());
    }

    @Test
    void testGetByPositionNotFound() {
        when(statisticsRepository.findByPosition(1L)).thenReturn(Optional.empty());

        FibonacciStatisticsDto result = statisticsService.getByPosition("1");
        assertEquals("1", result.getN());
        assertEquals("0", result.getCantidadRequests());
    }

    @Test
    void testUpdateStatisticsExisting() {
        FibonacciStatistics existingStat = new FibonacciStatistics();
        existingStat.setPosition(1L);
        existingStat.setRequestCount(5L);

        when(statisticsRepository.findByPosition(1L)).thenReturn(Optional.of(existingStat));

        statisticsService.updateStatistics(BigInteger.valueOf(1L));

        verify(statisticsRepository).save(existingStat);
        assertEquals(6, existingStat.getRequestCount());
    }
}
