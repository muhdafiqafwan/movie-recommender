package com.aa.micromovie;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepo repo;

    public List<Movie> getMovies()
    {
        return repo.findAll();
    }

    public Movie getMovieById(int id)
    {
        return repo.findById(id).orElse(null);
    }

    public Page<Movie> getPaginatedMovies(int pageNo, int pageSize, String search) {
        // Check for searching
        if (search == null || search == "") 
        {
            Sort sort = Sort.by("tmdbId").ascending();
            Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
            // Return all movies
            return this.repo.findAll(pageable); 
        } 
        else 
        {
            Sort sort = Sort.by("tmdbId").ascending();
            Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
            // Return movies based on search field
            return this.repo.findByTitleContainingIgnoreCase(search, pageable); 
        }
    }

    public Movie addMovie(Movie movie)
    {
        return repo.save(movie);
    }
}
