package com.soen342;

import com.soen342.service.ConnectionCatalog;
import com.soen342.domain.Parameters;
import com.soen342.domain.Search;
import com.soen342.service.SearchService;
import com.soen342.service.SearchResult;
import com.soen342.domain.Trip;
import java.util.List;
import java.sql.Time;
import com.soen342.domain.Connection;

public class App 
{
    public static void main( String[] args ) {

    ConnectionCatalog catalog = new ConnectionCatalog();
    catalog.loadFromFile("C:\\Users\\Simon\\Desktop\\Ecole\\342\\iteration1\\src\\main\\java\\com\\soen342\\resources\\eu_rail_network.csv");
    //System.out.println(catalog.toString());

    Parameters searchParams = new Parameters("Amiens", "Rouen", null, null, null, "Mon", 0, 0);
    
    Search search = new Search(searchParams);

    SearchService service = new SearchService(catalog);
    SearchResult result = service.searchTrips(search);

    result.sortPriceAsc();
    System.out.println(result.toString());

    }
}
