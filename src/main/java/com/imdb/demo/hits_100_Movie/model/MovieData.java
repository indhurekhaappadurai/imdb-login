package com.imdb.demo.hits_100_Movie.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class MovieData {
    private List<TopMovieData> items = new ArrayList<TopMovieData>();
    private String errorMessage;
}
