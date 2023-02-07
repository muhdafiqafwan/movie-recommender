package com.aa.micromovie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    // Endpoint for getting all Movies
    @GetMapping("/movies")
    public List<Movie> findAllMovies()
    {
        return movieService.getMovies();
    }

    // Endpoint for getting Movie by ID
    @GetMapping("/movies/{id}")
    public Movie findMovieById(@PathVariable int id)
    {
        return movieService.getMovieById(id);
    }

    // Endpoint for paginating list of movies
    @GetMapping("/page/{pageNo}")
    public Page<Movie> findPaginatedMovies(@PathVariable int pageNo, @RequestParam(value = "search", required = false) String search) {
        int pageSize = 15;
        Page<Movie> page = movieService.getPaginatedMovies(pageNo, pageSize, search);
        return page;
    }

    // Endpoint for adding new movie
    @PostMapping("/addMovie")
    public Movie addMovie(@RequestBody Movie movie)
    {
        return movieService.addMovie(movie);
    }
}
