package com.soen342.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import domain.Connection;

public class ConnectionCatalog {

    private List<Connection> connections;

    public ConnectionCatalog() {
        this.connections = new ArrayList<>();
    }

    public void loadFromFile(String filePath) {
        connections.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 9) continue;

                // Extract values
                String routeID = parts[0].trim();
                String departureCity = parts[1].trim();
                String arrivalCity = parts[2].trim();
                Time departureTime = Time.valueOf(parts[3].trim() + ":00");
                Time arrivalTime = Time.valueOf(parts[4].trim() + ":00");
                String trainType = parts[5].trim();
                String daysOfOperation = parts[6].trim();
                double firstClassRate = Double.parseDouble(parts[7].trim());
                double secondClassRate = Double.parseDouble(parts[8].trim());

                // Create parameter object
                Parameters parameters = new Parameters(
                        departureCity, arrivalCity, departureTime, arrivalTime,
                        trainType, daysOfOperation, firstClassRate, secondClassRate
                );

                // Create connection object 
                Connection connection = new Connection(routeID, parameters);
                connections.add(connection);
            }

        } catch (IOException e) {
            System.err.println("Error loading connections: " + e.getMessage());
        }
    }

    public List<Connection> getAllConnections() {
        return connections;
    }
}
