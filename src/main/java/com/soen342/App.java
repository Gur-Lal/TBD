package com.soen342;

import com.soen342.domain.Search;
import com.soen342.service.ConnectionCatalog;
import com.soen342.service.SearchService;
import com.soen342.domain.Parameters;
import com.soen342.domain.Search;
import com.soen342.service.SearchService;
import com.soen342.service.SearchResult;
import com.soen342.domain.Trip;
import java.util.List;
import java.sql.Time;
import com.soen342.domain.Connection;
import java.io.File;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== EU Rail Trip Planner ===");

        // Fixed path for CSV file so that it works on different machines
        String csvPath = "src/main/java/com/soen342/resources/eu_rail_network.csv";
        File csvFile = new File(csvPath);

        if (!csvFile.exists()) {
            System.out.println("Error: CSV file not found at " + csvFile.getAbsolutePath());
            System.exit(1);
        }

        // Load CSV file
        ConnectionCatalog catalog = new ConnectionCatalog();
        catalog.loadFromFile(csvPath);
        //For debugging 
       // System.out.println("CSV file loaded successfully.");


       //Parameters initialization
        Time departureTime= null;
        Time arrivalTime= null;
        String trainType =null;
        String daysOfOperation =null;
        double firstClassRate = 0.0;
        double secondClassRate = 0.0;


        //  Take user inputs for Parameters
        //All inputs are optional that's why we check if they are empty
       System.out.print("Enter departure city (press Enter to skip): ");
        String departureCity = scanner.nextLine().trim();
        if (departureCity.isEmpty()) departureCity = null;

        System.out.print("Enter arrival city (press Enter to skip): ");
        String arrivalCity = scanner.nextLine().trim();
        if (arrivalCity.isEmpty()) arrivalCity = null;

        System.out.print("Enter departure time (HH:MM:SS or press Enter to skip): ");
        String depTimeInput = scanner.nextLine().trim();
        if (!depTimeInput.isEmpty()) departureTime = Time.valueOf(depTimeInput);

        System.out.print("Enter arrival time (HH:MM:SS or press Enter to skip): ");
        String arrTimeInput = scanner.nextLine().trim();
        if (!arrTimeInput.isEmpty()) arrivalTime = Time.valueOf(arrTimeInput);

        System.out.print("Enter train type (press Enter to skip): ");
        trainType = scanner.nextLine().trim();
        if (trainType.isEmpty()) trainType = null;

        System.out.print("Enter days of operation (Mon, Tue, Wed, Thu, Fri, Sat, Sun) (press Enter to skip): ");
        daysOfOperation = scanner.nextLine().trim();
        if (daysOfOperation.isEmpty()) daysOfOperation = null;

        System.out.print("Enter first-class rate (press Enter to skip): ");
        String fcInput = scanner.nextLine().trim();
        if (!fcInput.isEmpty()) firstClassRate = Double.parseDouble(fcInput);

        System.out.print("Enter second-class rate (press Enter to skip): ");
        String scInput = scanner.nextLine().trim();
        if (!scInput.isEmpty()) secondClassRate = Double.parseDouble(scInput);

        // --- Create Parameters object ---
        Parameters parameters = new Parameters(
                departureCity,
                arrivalCity,
                departureTime,
                arrivalTime,
                trainType,
                daysOfOperation,
                firstClassRate,
                secondClassRate
        );

        //  Perform search 
        Search search = new Search(parameters);

        SearchService searchService = new SearchService(catalog);
        SearchResult result = searchService.searchTrips(search);

        //If no results 
        if (result.getSearchResultDirect().isEmpty() && result.getSearchResultIndirect().isEmpty()) {
            System.out.println("\nNo matching trips found.");
            scanner.close();
            return;
        }

        //Ask user for sorting options 
        System.out.println("\nHow would you like to sort the trips?");
        System.out.println("1. By Duration");
        System.out.println("2. By Price");
        System.out.print("Enter your choice (1 or 2): ");
        int sortFieldChoice = scanner.nextInt();

        System.out.println("\nChoose order:");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        System.out.print("Enter your choice (1 or 2): ");
        int sortOrderChoice = scanner.nextInt();

        // --- Apply chosen sorting method ---
        if (sortFieldChoice == 1 && sortOrderChoice == 1) {
            result.sortDurationAsc();
            System.out.println("\nTrips sorted by duration (ascending).");
        } else if (sortFieldChoice == 1 && sortOrderChoice == 2) {
            result.sortDurationDesc();
            System.out.println("\nTrips sorted by duration (descending).");
        } else if (sortFieldChoice == 2 && sortOrderChoice == 1) {
            result.sortPriceAsc();
            System.out.println("\nTrips sorted by price (ascending).");
        } else if (sortFieldChoice == 2 && sortOrderChoice == 2) {
            result.sortPriceDesc();
            System.out.println("\nTrips sorted by price (descending).");
        } else {
            System.out.println("\nInvalid choice. Showing unsorted results.");
        }

        // --- Display results ---
        System.out.println("\n=== Search Results ===");
        System.out.println(result);

        scanner.close();
    }
}
