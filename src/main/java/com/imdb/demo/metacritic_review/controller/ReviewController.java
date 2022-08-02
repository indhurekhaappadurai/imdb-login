package com.imdb.demo.metacritic_review.controller;

import com.imdb.demo.metacritic_review.model.ReviewEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
@RestController
@EnableCaching
public class ReviewController {
    @Value("${api.key}")
    private String apiKey;

    /**
     * Method to get review of the movie.
     * @return review of the movie based on id.
     */

    @Cacheable(cacheNames = "reviewCache")
    @GetMapping("/reviewMovie")

    public List<ReviewEntity> getMovieReview() {

        String url = "https://imdb-api.com/en/API/MetacriticReviews/" + apiKey + "/tt1375666";
        RestTemplate rst = new RestTemplate();
        ReviewEntity review = rst.getForObject(url, ReviewEntity.class);
        System.out.println("Getting from web service");
        return Arrays.asList(review);

    }

}