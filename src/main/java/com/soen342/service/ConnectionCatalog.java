package com.soen342.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import com.soen342.domain.Connection;
import com.soen342.domain.Parameters;

public class ConnectionCatalog {

    private List<Connection> connections;

    public ConnectionCatalog() {
        this.connections = new ArrayList<>();
    }

    public List<Connection> getAllConnections() {
        return connections;
    }

    public void loadFromFile(String filePath) {
        connections.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (parts.length < 9) continue;

                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].replace("\"", "").trim();
                }

                // Extract values
                String routeID = parts[0];
                String departureCity = parts[1];
                String arrivalCity = parts[2];

                String depTimeStr = parts[3].trim() + ":00";
                Time departureTime = Time.valueOf(depTimeStr);

                String arrTimeStr = parts[4].trim();
                arrTimeStr = arrTimeStr.split(" ")[0] + ":00"; // Remove (+1d)
                Time arrivalTime = Time.valueOf(arrTimeStr);

                String trainType = parts[5];
                String daysOfOperation = parts[6];
                double firstClassRate = Double.parseDouble(parts[7]);
                double secondClassRate = Double.parseDouble(parts[8]);

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Connection Catalog ===\n");
        for (Connection c : connections) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }

}
