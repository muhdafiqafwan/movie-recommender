package com.aa.microrating;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RatingService {
    @Autowired
    private RatingRepo repo;

    public Rating addRating(Rating rating)
    {
        return repo.save(rating);
    }

    public Rating getRatingByMovieIdAndUserId(int movieId, String userId)
    {
        return repo.findByMovieIdAndUserId(movieId,userId);
    }
}
