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

    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Trip Details:\n");
        sb.append("Total Time: ").append(totalTime).append("\n");
        sb.append("Total First Class Rate: ").append(totalFCRate).append("\n");
        sb.append("Total Second Class Rate: ").append(totalSCRate).append("\n");
        sb.append("Connections:\n");
        for (Connection conn : connections) {
            sb.append(conn.toString()).append("\n");
        }
        return sb.toString();
    }

}
