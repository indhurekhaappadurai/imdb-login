package com.imdb.demo.hits_100_Movie.service;

import com.imdb.demo.hits_100_Movie.model.MovieData;
import com.imdb.demo.hits_100_Movie.model.TopMovieData;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
@Service
@Log4j2
@EnableCaching
public class Top100MovieService {
    Logger logger = LoggerFactory.getLogger(Top100MovieService.class);

    @Value("${api.key}")
    private String apiKey;

    /**
     * Method to get top movies.
     * @return list of top 100 movies
     */
    @Cacheable(cacheNames="topmovies")
    public List<TopMovieData> getTopHundredMovies(){
        String url = "https://imdb-api.com/en/API/Top250Movies/"+apiKey;
        RestTemplate hit = new RestTemplate();
        MovieData movie = hit.getForObject(url, MovieData.class);
        List<MovieData> list = Arrays.asList(movie);
        List<TopMovieData> newList = list.get(0).getItems().subList(0,100);
        System.out.println("Getting from web service");
        logger.info ("MovieService || getTopHundredMovies || Top100Movies data has been successfully displayed");
        return newList;
    }
}
