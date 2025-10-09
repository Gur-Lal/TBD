package com.soen342.service;

import java.util.Comparator;
import java.util.List;

import com.soen342.domain.Trip;

public class SearchResult {
    
    private List<Trip> searchResultDirect;
    private List<Trip> searchResultIndirect;

    public SearchResult(List<Trip> searchResultDirect, List<Trip> searchResultIndirect) {
        this.searchResultDirect = searchResultDirect;
        this.searchResultIndirect = searchResultIndirect;
    }

    public List<Trip> getSearchResultDirect() {
        return searchResultDirect;
    }

    public List<Trip> getSearchResultIndirect() {
        return searchResultIndirect;
    }

    public void setSearchResultDirect(List<Trip> searchResultDirect) {
        this.searchResultDirect = searchResultDirect;
    }

    public void setSearchResultIndirect(List<Trip> searchResultIndirect) {
        this.searchResultIndirect = searchResultIndirect;
    }

    public void sortDurationAsc() {
        searchResultDirect.sort(Comparator.comparing(Trip::getTotalTime));
        searchResultIndirect.sort(Comparator.comparing(Trip::getTotalTime));
    }

    public void sortDurationDesc() {
        searchResultDirect.sort(Comparator.comparing(Trip::getTotalTime).reversed());
        searchResultIndirect.sort(Comparator.comparing(Trip::getTotalTime).reversed());
    }

    public void sortPriceAsc() {
        searchResultDirect.sort(Comparator.comparing(Trip::getTotalSCRate));
        searchResultIndirect.sort(Comparator.comparing(Trip::getTotalSCRate));
    }

    public void sortPriceDesc() {
        searchResultDirect.sort(Comparator.comparing(Trip::getTotalFCRate).reversed());
        searchResultIndirect.sort(Comparator.comparing(Trip::getTotalFCRate).reversed());
    }

@Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("------------------------------------------------------\n");
    sb.append("Direct Trips:\n");
    sb.append("------------------------------------------------------\n");
    if (searchResultDirect.isEmpty()) {
        sb.append("  No direct trips found.\n");
    } else {
        for (Trip trip : searchResultDirect) {
            sb.append(trip.toString()).append("\n");
        }
    }

    sb.append("------------------------------------------------------\n");
    sb.append("Indirect Trips:\n");
    sb.append("------------------------------------------------------\n");
    if (searchResultIndirect.isEmpty()) {
        sb.append("  No indirect trips found.\n");
    } else {
        for (Trip trip : searchResultIndirect) {
            sb.append(trip.toString()).append("\n");
        }
    }

    return sb.toString();
}


}
