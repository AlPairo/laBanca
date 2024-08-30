package com.example.fibonacci.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FibonacciStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="position")
    private Long position;

    @Column(name="request_count")
    private Long requestCount;

    public Long getPosition() {
        return position;
    }
    public Long getRequestCount() {
        return requestCount;
    }
    public void setPosition(Long position) {
        this.position = position;
    }
    public void setRequestCount(Long requestCount) {
        this.requestCount = requestCount;
    }
}
