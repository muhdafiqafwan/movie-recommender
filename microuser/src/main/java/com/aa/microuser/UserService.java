package com.aa.microuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passEncoder;

    // Register method
    public ResponseEntity<?> registerUser(User user)
    {
        // Check existing username if exist
        if (repo.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>("Username already exists!", HttpStatus.BAD_REQUEST);
        }
        // Check existing email if exist 
        else if (repo.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>("Email already exists!", HttpStatus.BAD_REQUEST);
        } else {
            // Check if password and confirm password match
            if (user.getPassword().equals(user.getConfirmPassword())) {
                // Set user's role
                user.setRole("USER");
                // hash password using bcrypt
                user.setPassword(passEncoder.encode(user.getPassword()));
                // Save transaction
                repo.save(user); 
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Password does not match!", HttpStatus.BAD_REQUEST);
            }
        }
    }

    // Add User method for admin
    public ResponseEntity<?> addUser(User user)
    {
        // Check existing username if exist
        if (repo.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>("Username already exists!", HttpStatus.BAD_REQUEST); 
        }
        // Check existing email if exist 
        else if (repo.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>("Email already exists!", HttpStatus.BAD_REQUEST); 
        } else {
            // Check if password and confirm password match
            if (user.getPassword().equals(user.getConfirmPassword())) {
                // hash password using bcrypt
                user.setPassword(passEncoder.encode(user.getPassword()));
                // Save transaction
                repo.save(user); 
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Password does not match!", HttpStatus.BAD_REQUEST); 
            }
        }
    }
    
    // Login method
    public ResponseEntity<?> loginUser(User user)
    {
        // Get user
        User userdata = repo.findByUsername(user.getUsername()); 

        // Check if user exists
        if (userdata != null) {
            // Check if password match 
            if (passEncoder.matches(user.getPassword(), userdata.getPassword())) {
                // Return user's data
                return new ResponseEntity<User>(userdata, HttpStatus.OK); 
            } else {
                return new ResponseEntity<>("Password is incorrect!", HttpStatus.BAD_REQUEST); 
            }
        } else {
            return new ResponseEntity<>("User does not exists!", HttpStatus.BAD_REQUEST); 
        }
    }
    
    // Pagination method for list of user
    public Page<User> getPaginatedUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.repo.findAll(pageable);
    }
}
