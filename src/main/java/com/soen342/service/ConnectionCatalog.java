package com.soen342.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import com.soen342.domain.Trip;
import com.soen342.domain.Search;
import com.soen342.domain.Parameters;
import com.soen342.domain.Connection;

public class ConnectionCatalog {

    private List<Connection> connections;

    public ConnectionCatalog() {
        this.connections = new ArrayList<>();
    }

    public List<Connection> getAllConnections() {
        return connections;
    }

    private String expandDays(String daysStr) {

        List<String> days = List.of("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");


        daysStr = daysStr.trim();
        if (!daysStr.contains("-")) return daysStr;


        String[] range = daysStr.split("-");
        if (range.length != 2) return daysStr;

        String start = range[0].trim();
        String end = range[1].trim();

        int startIndex = days.indexOf(start);
        int endIndex = days.indexOf(end);
        if (startIndex == -1 || endIndex == -1) return daysStr;

        StringBuilder expanded = new StringBuilder();
        int i = startIndex;
        while (true) {
            expanded.append(days.get(i));
            if (i == endIndex) break;
            expanded.append(", ");
            i = (i + 1) % days.size();
        }

        return expanded.toString();
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
                String daysOfOperation = expandDays(parts[6]);
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


    // Checks if a direct connection exists. Is used searchTrips 
    public boolean directConnectionExists(Parameters searchParams) {
        for (Connection conn : connections) {
            Parameters params = conn.getParameters();
            if (params.getDepartureCity().equalsIgnoreCase(searchParams.getDepartureCity()) &&
                params.getArrivalCity().equalsIgnoreCase(searchParams.getArrivalCity())) {
                return true;
            }
        }
        return false;
    }

    // Calculates total time for a list of connections
    public Time calculateTotalTime(List<Connection> connections) {

        if (connections == null || connections.isEmpty()) {
            return Time.valueOf("00:00:00");
        }

        long totalMillis = 0;

        for (Connection conn : connections) {
            long duration = conn.calculateTime();
            totalMillis += duration;
        }

        totalMillis = totalMillis % (24 * 60 * 60 * 1000);

        int hours = (int) (totalMillis / (1000 * 60 * 60));
        int minutes = (int) ((totalMillis / (1000 * 60)) % 60);
        int seconds = (int) ((totalMillis / 1000) % 60);

        String formatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return Time.valueOf(formatted);
    }

    // Calculates total first class rate for a list of connections
    public double calculateTotalFCRate(List<Connection> connections) {
        double total = 0.0;

        for (Connection connection : connections) {
            total += connection.getParameters().getFirstClassRate();
        }

        return total;
    }

    // Calculates total second class rate for a list of connections
    public double calculateTotalSCRate(List<Connection> connections) {
        double total = 0.0;

        for (Connection connection : connections) {
            total += connection.getParameters().getSecondClassRate();
        }

        return total;
    }

    // Searches for direct connections based on search parameters
    public List<Trip> searchDirect(Parameters searchParams) {
        List<Trip> result = new ArrayList<>();

        for (Connection conn : connections) {
            Parameters params = conn.getParameters();

            if (!params.getDepartureCity().equalsIgnoreCase(searchParams.getDepartureCity())) continue;
            if (!params.getArrivalCity().equalsIgnoreCase(searchParams.getArrivalCity())) continue;

            if (searchParams.getDepartureTime() != null && params.getDepartureTime().before(searchParams.getDepartureTime())) continue;
            if (searchParams.getArrivalTime() != null && params.getArrivalTime().after(searchParams.getArrivalTime())) continue;
            if (searchParams.getTrainType() != null && !params.getTrainType().equalsIgnoreCase(searchParams.getTrainType())) continue;
            if (searchParams.getDaysOfOperation() != null && !params.getDaysOfOperation().contains(searchParams.getDaysOfOperation())) continue;
            if (searchParams.getFirstClassRate() > 0 && params.getFirstClassRate() > searchParams.getFirstClassRate()) continue;
            if (searchParams.getSecondClassRate() > 0 && params.getSecondClassRate() > searchParams.getSecondClassRate()) continue;

            List<Connection> connList = new ArrayList<>();
            connList.add(conn);
            Time totalTime = calculateTotalTime(connList);
            double totalFCRate = calculateTotalFCRate(connList);
            double totalSCRate = calculateTotalSCRate(connList);
            Trip trip = new Trip(totalTime, totalFCRate, totalSCRate, connList);
            result.add(trip);
        }

        return result;
        
    }

    public List<Trip> searchIndirect(Parameters searchParams) {
    List<Trip> result = new ArrayList<>();

    // --- Two-hop connections ---
    for (Connection conn1 : connections) {
        String dep1 = conn1.getParameters().getDepartureCity();
        String arr1 = conn1.getParameters().getArrivalCity();

        if (dep1.equalsIgnoreCase(searchParams.getDepartureCity())) {
            for (Connection conn2 : connections) {
                String dep2 = conn2.getParameters().getDepartureCity();
                String arr2 = conn2.getParameters().getArrivalCity();

                // Avoid loops or redundant paths
                if (dep2.equalsIgnoreCase(arr1) &&
                    arr2.equalsIgnoreCase(searchParams.getArrivalCity()) &&
                    !arr2.equalsIgnoreCase(dep1) &&   // don’t go back to start
                    !dep2.equalsIgnoreCase(dep1)) {   // avoid same city twice

                    // Build valid trip
                    List<Connection> connList = new ArrayList<>();
                    connList.add(conn1);
                    connList.add(conn2);

                    Time totalTime = calculateTotalTime(connList);
                    double totalFCRate = calculateTotalFCRate(connList);
                    double totalSCRate = calculateTotalSCRate(connList);
                    Trip trip = new Trip(totalTime, totalFCRate, totalSCRate, connList);

                    // Avoid adding if already present (basic duplicate check)
                    if (!result.contains(trip)) {
                        result.add(trip);
                    }
                }
            }
        }
    }

    // --- Three-hop connections ---
    for (Connection conn1 : connections) {
        String dep1 = conn1.getParameters().getDepartureCity();
        String arr1 = conn1.getParameters().getArrivalCity();

        if (dep1.equalsIgnoreCase(searchParams.getDepartureCity())) {
            for (Connection conn2 : connections) {
                String dep2 = conn2.getParameters().getDepartureCity();
                String arr2 = conn2.getParameters().getArrivalCity();

                if (dep2.equalsIgnoreCase(arr1) &&
                    !arr2.equalsIgnoreCase(dep1) &&   // no return to start
                    !dep2.equalsIgnoreCase(dep1)) {   // avoid same city twice
                    for (Connection conn3 : connections) {
                        String dep3 = conn3.getParameters().getDepartureCity();
                        String arr3 = conn3.getParameters().getArrivalCity();

                        if (dep3.equalsIgnoreCase(arr2) &&
                            arr3.equalsIgnoreCase(searchParams.getArrivalCity()) &&
                            !arr3.equalsIgnoreCase(dep1) &&  // avoid loop back to start
                            !arr3.equalsIgnoreCase(arr1) &&  // avoid revisiting arr1
                            !dep3.equalsIgnoreCase(dep1)) {  // avoid same city twice

                            List<Connection> connList = new ArrayList<>();
                            connList.add(conn1);
                            connList.add(conn2);
                            connList.add(conn3);

                            Time totalTime = calculateTotalTime(connList);
                            double totalFCRate = calculateTotalFCRate(connList);
                            double totalSCRate = calculateTotalSCRate(connList);
                            Trip trip = new Trip(totalTime, totalFCRate, totalSCRate, connList);

                            if (!result.contains(trip)) {
                                result.add(trip);
                            }
                        }
                    }
                }
            }
        }
    }

    return result;
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
