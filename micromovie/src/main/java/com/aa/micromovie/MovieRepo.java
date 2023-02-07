package com.aa.micromovie;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends JpaRepository<Movie, Integer> {
    public List<Movie> findByMovieId(Integer id);
    public Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable); 
}
