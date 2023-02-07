package com.aa.microrecommend;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class RecommendationService {
    @Autowired
    private RecommendationRepo repo;

    public ResponseEntity<?> findRecommended(int id)
    {
        try
        {
            List<Matrix> recommendedMovies = repo.findAll(id);
            return new ResponseEntity<List<Matrix>>(recommendedMovies, HttpStatus.OK);
        }
        catch (DataAccessException e)
        {
            return new ResponseEntity<>("No recommended movies", HttpStatus.BAD_REQUEST);
        }
    }
}
