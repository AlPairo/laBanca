package com.example.fibonacci.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FibonacciNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="position")
    private Long position;

    @Column(name="fib_value", columnDefinition = "TEXT")
    private String fibValue;


    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getFibValue() {
        return fibValue;
    }

    public void setFibValue(String fibValue) {
        this.fibValue = fibValue;
    }
}
