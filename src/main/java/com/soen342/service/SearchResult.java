package com.soen342.service;

import java.util.List;
import java.util.ArrayList;

import com.soen342.domain.Trip;

public class SearchResult {
    
    private List<Trip> searchResult;

    public SearchResult(List<Trip> trips) {
        this.trips = trips;
    }

    public List<Trip> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<Trip> searchResult) {
        this.searchResult = searchResult;
    }

    public void sortDurationAsc() {
        // To implement: Sort trips by duration in ascending order
    }

    public void sortDurationDesc() {
        // To implement: Sort trips by duration in descending order
    }

    public void sortPriceAsc() {
        // To implement: Sort trips by price in ascending order
    }

    public void sortPriceDesc() {
        // To implement: Sort trips by price in descending order
    }
}
