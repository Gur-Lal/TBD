package com.soen342.service;

import java.util.ArrayList;
import java.util.List;
import domain.Connection;

public class ConnectionCatalog {

    private List<Connection> connections;

    public ConnectionCatalog() {
        this.connections = new ArrayList<>();
    }

    public void loadFromFile(String filePath) {
        // To implement: Load connections from a file
    }

    public List<Connection> getAllConnections() {
        return connections;
    }
}
