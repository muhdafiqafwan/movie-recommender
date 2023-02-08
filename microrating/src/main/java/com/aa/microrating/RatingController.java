package com.aa.microrating;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {
    
    @Autowired
    private RatingService ratingService;

    // Endpoint for adding new movie
    @PostMapping("/addRating")
    public Rating addRating(@RequestBody Rating rating)
    {
        return ratingService.addRating(rating);
    }

    // Endpoint for finding Rating by Movie ID and User ID
    @GetMapping("/ratings/{movieId}/{userId}")
    public Rating findRatingByMovieIdAndUserId(@PathVariable("movieId") int movieId, @PathVariable("userId") String userId)
    {
        return ratingService.getRatingByMovieIdAndUserId(movieId, userId);
    }
}
