package com.soen342;

import com.soen342.service.ConnectionCatalog;

public class App 
{
    public static void main( String[] args ) {

    ConnectionCatalog catalog = new ConnectionCatalog();
    catalog.loadFromFile("C:\\Users\\Simon\\Desktop\\Ecole\\342\\iteration1\\src\\main\\java\\com\\soen342\\resources\\eu_rail_network.csv");
    //System.out.println(catalog.toString());

    

    }
}
