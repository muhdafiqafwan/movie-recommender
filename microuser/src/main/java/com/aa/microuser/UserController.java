package com.aa.microuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // Endpoint for registration
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user)
    {
        return userService.registerUser(user);
    }

    // Endpoint for admin to add user 
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody User user)
    {
        return userService.addUser(user);
    }

    // Endpoint for login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user)
    {
        return userService.loginUser(user);
    }

    // Endpoint to paginate list of users
    @GetMapping("/page/{pageNo}")
    public Page<User> findPaginatedUsers(@PathVariable int pageNo) {
        int pageSize = 15;
        Page<User> page = userService.getPaginatedUsers(pageNo, pageSize);
        return page;
    }
}
