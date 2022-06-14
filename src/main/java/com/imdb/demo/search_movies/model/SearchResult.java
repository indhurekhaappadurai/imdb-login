package com.imdb.demo.search_movies.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class SearchResult {
    private String searchType;
    private String expression;
    private List<SearchResult> results = new ArrayList<SearchResult>();
}
