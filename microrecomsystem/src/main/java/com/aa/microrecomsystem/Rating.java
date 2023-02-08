package com.aa.microrecomsystem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    private int ratingId;
    private int userId;
    private int movieId;
    private int rating;
}

