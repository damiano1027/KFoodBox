package kfoodbox.search.service;

import kfoodbox.search.dto.response.IntegratedSearchResponse;

public interface SearchService {
    IntegratedSearchResponse searchIntegratedInformation(String query);
}
