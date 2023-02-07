package com.aa.microrecomsystem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int userId;
    private String firstName, lastName, username, email, password, role;

    @Transient
    private String confirmPassword;
}
