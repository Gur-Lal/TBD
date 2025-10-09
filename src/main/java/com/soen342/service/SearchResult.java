package com.soen342.service;

import java.util.Comparator;
import java.util.List;

import com.soen342.domain.Trip;

public class SearchResult {
    
    private List<Trip> searchResult;

    public SearchResult(List<Trip> trips) {
        this.searchResult = trips;
    }

    public List<Trip> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<Trip> searchResult) {
        this.searchResult = searchResult;
    }

    public void sortDurationAsc() {
        searchResult.sort(Comparator.comparing(Trip::getTotalTime));
    }

    public void sortDurationDesc() {
        searchResult.sort(Comparator.comparing(Trip::getTotalTime).reversed());
    }

    public void sortPriceAsc() {
        searchResult.sort(Comparator.comparing(Trip::getTotalSCRate));
    }

    public void sortPriceDesc() {
        searchResult.sort(Comparator.comparing(Trip::getTotalFCRate).reversed());
    }


}
