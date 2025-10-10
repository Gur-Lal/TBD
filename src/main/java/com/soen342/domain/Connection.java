package com.soen342.domain;

import java.sql.Time;

public class Connection {
    private String routeID;
    private Parameters parameters;

    public Connection(String routeID, Parameters parameters) {
        this.routeID = routeID;
        this.parameters = parameters;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public long calculateTime() {
        Time departureTime = parameters.getDepartureTime();
        Time arrivalTime = parameters.getArrivalTime();

        long departureMillis = departureTime.getTime();
        long arrivalMillis = arrivalTime.getTime();

        // Handle overnight travel
        if (arrivalMillis < departureMillis) {
            arrivalMillis += 24 * 60 * 60 * 1000;
        }

        return arrivalMillis - departureMillis;
    }

    public String toString() {
    return "Connection Details:\n" +
           "From: " + parameters.getDepartureCity() +
           ", To: " + parameters.getArrivalCity() +
           ", Departure: " + parameters.getDepartureTime() +
           ", Arrival: " + parameters.getArrivalTime() +
           ", Train: " + parameters.getTrainType() +
           ", Days: " + parameters.getDaysOfOperation() +
           ", 1st Class: EUR " + parameters.getFirstClassRate() + "0" +
           ", 2nd Class: EUR " + parameters.getSecondClassRate() + "0";
    }
}
