package com.soen342.service;

import java.util.List;


import com.soen342.domain.Trip;
import com.soen342.domain.Search;
import com.soen342.domain.Parameters;
import com.soen342.domain.Connection;

public class SearchService {

    private ConnectionCatalog connectionCatalog;

    public SearchService(ConnectionCatalog connectionCatalog) {
        this.connectionCatalog = connectionCatalog;
    }

}
