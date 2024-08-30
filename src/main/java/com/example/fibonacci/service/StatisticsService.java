package com.example.fibonacci.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.fibonacci.repository.FibonacciStatisticsRepository;
import com.example.fibonacci.dto.FibonacciStatisticsDto;
import com.example.fibonacci.entity.FibonacciStatistics;


@Service
public class StatisticsService {

    private final FibonacciStatisticsRepository statisticsRepository;

    StatisticsService(FibonacciStatisticsRepository statisticsRepository){
        this.statisticsRepository = statisticsRepository;
    }

    /**
     * Devuelve todas las estadísticas
     * 
     * @return estadísticas
     */
    public List<FibonacciStatisticsDto> getAllStats(){
        List<FibonacciStatistics> stats = statisticsRepository.findAll();
        List<FibonacciStatisticsDto> ret = new ArrayList<>();
        for (FibonacciStatistics stat : stats) {
            FibonacciStatisticsDto dto = new FibonacciStatisticsDto();
            dto.setN(stat.getPosition().toString());
            dto.setCantidadRequests(stat.getRequestCount().toString());
            ret.add(dto);
        }
        return ret;
    }

    /**
     * Devuelve las estadísticas para la posición dada
     * 
     * @param value posición
     * @return estadística de la posición
     */
    public FibonacciStatisticsDto getByPosition(String value){
        Optional<FibonacciStatistics> position = statisticsRepository.findByPosition(Long.valueOf(value));
        FibonacciStatisticsDto ret = new FibonacciStatisticsDto();
        if (position.isPresent()) {
            ret.setCantidadRequests(position.get().getRequestCount().toString());
            ret.setN(position.get().getPosition().toString());
        }else{
            ret.setCantidadRequests("0");
            ret.setN(value);
        }
        return ret;
    }

    /**
     * Actualiza las estadisticas de uso para el valor dado
     * @param n valor
     */
    public void updateStatistics(BigInteger n) {
        FibonacciStatistics statistics = statisticsRepository.findByPosition(n.longValue())
                .orElse(new FibonacciStatistics());
        statistics.setPosition(n.longValue());
        statistics.setRequestCount(statistics.getRequestCount() == null ? 1 : statistics.getRequestCount() + 1);
        statisticsRepository.save(statistics);
    }

}
