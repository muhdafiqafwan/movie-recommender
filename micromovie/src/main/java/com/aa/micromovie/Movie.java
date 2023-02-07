package com.aa.micromovie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue
    private int movieId;

    private String title;
    private String genre;
    private int tmdbId;
    private String poster;
    private String description;
}

