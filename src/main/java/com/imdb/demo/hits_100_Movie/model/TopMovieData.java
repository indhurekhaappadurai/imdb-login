package com.imdb.demo.hits_100_Movie.model;

import lombok.Data;

@Data
public class TopMovieData {
    private String id;
    private String rank;
    private String title;
    private String fullTitle;
    private String year;
    private String image;
    private String crew;
    private String imDbRating;
    private String imDbRatingCount;
}
