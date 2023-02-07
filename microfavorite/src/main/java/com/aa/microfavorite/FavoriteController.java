package com.aa.microfavorite;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    // Endpoint for finding User's Favorites Movies
    @GetMapping("/favorites/{id}")
    public List<Favorite> findAllFavoritesByUserId(@PathVariable String id)
    {
        return favoriteService.getFavoritesByUserId(id);
    }

    // Endpoint for finding Favorite Movie by Movie ID and User ID
    @GetMapping("/favorites/{movieId}/{userId}")
    public Favorite findFavoriteByMovieIdAndUserId(@PathVariable("movieId") int movieId, @PathVariable("userId") String userId)
    {
        return favoriteService.getFavoriteByMovieIdAndUserId(movieId, userId);
    }

    // Endpoint for adidng Movie to Favorite
    @PostMapping("/addFavorite")
    public Favorite addFavorite(@RequestBody Favorite favorite)
    {
        return favoriteService.addFavorite(favorite);
    }

    // Endpoint for deleting Movie from Favorite
    @PostMapping("/deleteFavorite/{id}")
    public String deleteFavorite(@RequestBody int id)
    {
        return favoriteService.deleteFavorite(id);
    }
}
