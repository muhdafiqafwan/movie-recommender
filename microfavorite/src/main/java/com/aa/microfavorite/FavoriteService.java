package com.aa.microfavorite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepo repo;

    public List<Favorite> getFavoritesByUserId(String id)
    {
        return repo.findAllByUserId(id);
    }

    public Favorite getFavoriteByMovieIdAndUserId(int movieId, String userId)
    {
        return repo.findByMovieIdAndUserId(movieId,userId);
    }

    public Favorite addFavorite(Favorite favorite)
    {
        return repo.save(favorite);
    }

    public String deleteFavorite(int id)
    {
        repo.deleteById(id);
        return "Movie deleted from favorites";
    }
}