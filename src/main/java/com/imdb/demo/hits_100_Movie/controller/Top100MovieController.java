package com.imdb.demo.hits_100_Movie.controller;

import com.imdb.demo.hits_100_Movie.model.TopMovieData;
import com.imdb.demo.hits_100_Movie.service.Top100MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class Top100MovieController {
    @Autowired
    Top100MovieService movieService;

    @GetMapping("/hit_100_movies")
    public List<TopMovieData> getData() {
        return movieService.getTopHundredMovies();
    }
}
