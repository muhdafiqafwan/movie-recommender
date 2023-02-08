package com.aa.microrating;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepo extends JpaRepository<Rating, Integer> {

    Rating findByMovieIdAndUserId(int movieId, String userId);
}
