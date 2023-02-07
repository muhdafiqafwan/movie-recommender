package com.aa.microrecomsystem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private int movieId;
    private String title;
    private String genre;
    private int tmdbId;
    private String poster;
    private String description;
}

