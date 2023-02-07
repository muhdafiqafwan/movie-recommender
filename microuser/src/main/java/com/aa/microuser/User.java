package com.aa.microuser;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private int userId;

    private String firstName, lastName, username, email, password, role;

    @Transient
    private String confirmPassword;
}
