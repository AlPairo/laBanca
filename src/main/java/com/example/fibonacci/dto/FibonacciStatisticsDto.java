package com.example.fibonacci.dto;

public class FibonacciStatisticsDto {

    private String n;
    private String cantidadRequests; 

    public FibonacciStatisticsDto(String n, String cantidadRequests){
        this.n = n;
        this.cantidadRequests = cantidadRequests;
    }

    public FibonacciStatisticsDto() {
    }

    public String getN() {
        return n;
    }

    public String getCantidadRequests() {
        return cantidadRequests;
    }

    public void setN(String n) {
        this.n = n;
    }

    public void setCantidadRequests(String cantidadRequests) {
        this.cantidadRequests = cantidadRequests;
    }
}
