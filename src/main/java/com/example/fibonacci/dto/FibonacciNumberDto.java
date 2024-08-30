package com.example.fibonacci.dto;

public class FibonacciNumberDto {
    private String n;
    private String value; 

    public FibonacciNumberDto(String n, String value){
        this.n = n;
        this.value = value;
    }

    public FibonacciNumberDto() {
    }

    public String getN() {
        return n;
    }

    public String getValue() {
        return value;
    }

    public void setN(String n) {
        this.n = n;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
