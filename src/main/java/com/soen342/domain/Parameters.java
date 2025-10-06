package com.soen342.domain;

import java.sql.Time;

public class Parameters {
    private String departureCity;
    private String arrivalCity;
    private Time departureTime;
    private Time arrivalTime;
    private String trainType;
    private String daysOfOperation;
    private double firstClassRate;
    private double secondClassRate;

    // Constructor
    public Parameters(String departureCity, String arrivalCity, java.sql.Time departureTime,java.sql.Time arrivalTime, String trainType, String daysOfOperation, double firstClassRate, double secondClassRate) {
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.trainType = trainType;
        this.daysOfOperation = daysOfOperation;
        this.firstClassRate = firstClassRate;
        this.secondClassRate = secondClassRate;
    }

    // Getters
    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public String getTrainType() {
        return trainType;
    }

    public String getDaysOfOperation() {
        return daysOfOperation;
    }

    public double getFirstClassRate() {
        return firstClassRate;
    }

    public double getSecondClassRate() {
        return secondClassRate;
    }

    // Setters
    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public void setDaysOfOperation(String daysOfOperation) {
        this.daysOfOperation = daysOfOperation;
    }

    public void setFirstClassRate(double firstClassRate) {
        this.firstClassRate = firstClassRate;
    }

    public void setSecondClassRate(double secondClassRate) {
        this.secondClassRate = secondClassRate;
    }
}
