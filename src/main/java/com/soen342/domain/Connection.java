package com.soen342.domain;

public class Connection {
    private String routeID;
    private Parameters parameters;

    public Connection(String routeID, Parameters parameters) {
        this.routeID = routeID;
        this.parameters = parameters;
    }

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String id) {
        this.routeID = id;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public String toString() {
    return "RouteID: " + routeID +
           ", From: " + parameters.getDepartureCity() +
           ", To: " + parameters.getArrivalCity() +
           ", Departure: " + parameters.getDepartureTime() +
           ", Arrival: " + parameters.getArrivalTime() +
           ", Train: " + parameters.getTrainType() +
           ", Days: " + parameters.getDaysOfOperation() +
           ", 1st Class: " + parameters.getFirstClassRate() +
           ", 2nd Class: " + parameters.getSecondClassRate();
    }
}
