package com.soen342.domain;

public class Connection {
    private int routeID;
    private Parameters parameters;

    public Connection(int routeID, Parameters parameters) {
        this.routeID = routeID;
        this.parameters = parameters;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int id) {
        this.routeID = id;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }
}
