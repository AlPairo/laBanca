package com.example.fibonacci.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fibonacci.entity.FibonacciStatistics;

public interface FibonacciStatisticsRepository extends JpaRepository<FibonacciStatistics, Long> {

    Optional<FibonacciStatistics> findByPosition(Long position);
}
