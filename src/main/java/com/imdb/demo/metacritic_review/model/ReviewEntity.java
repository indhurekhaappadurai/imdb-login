package com.imdb.demo.metacritic_review.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewEntity {
    private String imDbId;
    private String title;
    private String fullTitle;
    private String type;
    private String year;
    private List<ReviewItems> items = new ArrayList<ReviewItems>();
    private String errorMessage;
}
