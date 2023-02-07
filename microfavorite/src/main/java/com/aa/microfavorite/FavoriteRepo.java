package com.aa.microfavorite;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepo extends JpaRepository<Favorite, Integer> {
    Favorite findByMovieIdAndUserId(int movieId, String userId);
    List<Favorite> findAllByUserId(String id);
}
