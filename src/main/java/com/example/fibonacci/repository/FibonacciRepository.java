package com.example.fibonacci.repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.fibonacci.entity.FibonacciNumber;

public interface FibonacciRepository extends JpaRepository<FibonacciNumber, Long>  {
    
    Optional<FibonacciNumber> findByPosition(Long position);

    @Query(value = "SELECT * FROM fibonacci_number order by position desc limit 2", nativeQuery = true)
    List<FibonacciNumber> getLastTwo();

}
