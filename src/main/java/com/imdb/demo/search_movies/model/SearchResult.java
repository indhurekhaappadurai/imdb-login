package com.imdb.demo.search_movies.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class SearchResult {
    private String id;
    private String resultType;
    private String image;
    private String title;
    private String description;
}
