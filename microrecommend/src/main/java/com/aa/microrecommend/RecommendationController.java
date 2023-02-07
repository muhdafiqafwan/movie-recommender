package com.aa.microrecommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommended")
public class RecommendationController {
    @Autowired
    private RecommendationService service;

    // Endpoint for finding recommended movies
    @GetMapping("/movies/{id}")
    public ResponseEntity<?> findRecommended(@PathVariable int id)
    {
        return service.findRecommended(id);
    }
}
