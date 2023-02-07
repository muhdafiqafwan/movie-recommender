package com.aa.microrecomsystem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {
    private int favoriteId;
    private int movieId;
    private String userId;
}
