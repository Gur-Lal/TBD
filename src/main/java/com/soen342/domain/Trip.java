package com.soen342.domain;

import java.sql.Time;
import java.util.List;

public class Trip {
    private Time totalTime;
    private double totalFCRate;
    private double totalSCRate;
    private List<Connection> connections;

    public Trip(Time totalTime, double totalFCRate, double totalSCRate, List<Connection> connections) {
        this.totalTime = totalTime;
        this.totalFCRate = totalFCRate;
        this.totalSCRate = totalSCRate;
        this.connections = connections;
    }

    public Time getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Time totalTime) {
        this.totalTime = totalTime;
    }

    public double getTotalFCRate() {
        return totalFCRate;
    }

    public void setTotalFCRate(double totalFCRate) {
        this.totalFCRate = totalFCRate;
    }

    public double getTotalSCRate() {
        return totalSCRate;
    }

    public void setTotalSCRate(double totalSCRate) {
        this.totalSCRate = totalSCRate;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public int getNumberOfConnections() {
        return connections.size();
    }

    @Override 
    public String toString() {
        // If only one connection, just return its toString()
        if (connections.size() == 1) {
            return connections.get(0).toString();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Trip Details:\n");
        sb.append("Total Time: ").append(totalTime).append("\n");
        sb.append("Total First Class Rate: EUR ").append(totalFCRate).append("0\n");
        sb.append("Total Second Class Rate: EUR ").append(totalSCRate).append("0\n");
        sb.append("Connections:\n");

        for (Connection conn : connections) {
            sb.append(conn.toString()).append("\n");
        }
        sb.append("\n");

        return sb.toString();
    }
    //Needed to compare two Trip objects to avoid duplicates in the list of trips
    @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Trip)) return false;
    Trip t = (Trip) o;
    return this.getConnections().equals(t.getConnections());
}


}
