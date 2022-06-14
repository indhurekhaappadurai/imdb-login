package com.imdb.demo.search_movies.controller;

import com.imdb.demo.search_movies.model.SearchEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@EnableCaching

public class SearchMovie_Controller {
    @Value("${api.key}")
    String apiKey;

    /**
     * Get method to search movie.
     * @param movie_name
     * @return List of Movies based on search.
     */
    @Cacheable(value = "movies")
    @GetMapping("/searchmovies/{movie_name}")
    public List<SearchEntity> getData(@PathVariable String movie_name) {
        String url = "https://imdb-api.com/en/API/SearchMovie/" + apiKey + "/" + movie_name;
        RestTemplate rst = new RestTemplate();
        SearchEntity srch = rst.getForObject(url, SearchEntity.class);
        System.out.println("Getting from web service");
        return Arrays.asList(srch);
    }
}
