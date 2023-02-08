package com.aa.microrecomsystem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Controller
public class MicroRecomSystemController {
    // Rest template to perform HTTP request
    @Autowired
    private RestTemplate restTemplate;

    /* AUTH */
    // Endpoint for index page
    @GetMapping("/")
    public String index(@ModelAttribute("user") User user)
    {
        return "index";
    }

    // Endpoint for error page
    @GetMapping("/error")
    public String error()
    {
        return "error";
    }

    // Endpoint for registration page
    @GetMapping("/register")
    public String register(@ModelAttribute("user") User user)
    {
        return "register";
    }

    // Endpoint for logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate session
        session.invalidate();
        return "index";
    }

    // Endpoint for login
    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model,HttpSession session)
    {
        try {
            HttpEntity<User> entity = new HttpEntity<User>(user);
            // Call api to post user data for login
            User getUser = restTemplate.exchange("http://localhost:8081/user/login", HttpMethod.POST, entity, User.class).getBody();
            // Add session
            session.setAttribute("username", getUser.getUsername());
            session.setAttribute("userId", getUser.getUserId());
            // Check user roles to redirect to specific homepage
            if (getUser.getRole().equals("ADMIN")) {
                return "redirect:/dashboard";
            } else {
                return "redirect:/home";
            }
        } catch (HttpStatusCodeException e) {
            // Get the exception body as string
            String errorpayload = e.getResponseBodyAsString();
            // Add model attribute to display error message on front-end
            model.addAttribute("msg", errorpayload);
            return "index";
        }
    }

    // Endpoint for registration   
    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model)
    {
        try {
            HttpEntity<User> entity = new HttpEntity<User>(user);
            // Call api to post data to register
            restTemplate.exchange("http://localhost:8081/user/register", HttpMethod.POST, entity, User.class).getBody();
            return "redirect:/";
        } catch (HttpStatusCodeException e) {
            String errorpayload = e.getResponseBodyAsString();
            // Add model attribute to display error message on front-end
            model.addAttribute("msg", errorpayload);
            return "register";
        }
    }
    /* END AUTH */


    /* USER */
    // Endpoint for user's homepage
    @GetMapping("/home")
    public String home(Model model) 
    {
        // Call movies pagination method to be displayed on homepage
        return findPaginated(1, "", model);
    }

    // Endpoint for movies pagination
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam(value = "search", required=false) String search, Model model) {
        HttpEntity<Integer> entity = new HttpEntity<Integer>(pageNo);
        // Call api to fetch movies
        RestPage<Movie> page = restTemplate.exchange("http://localhost:8083/movie/page/" + pageNo + "?search=" + search, HttpMethod.GET, entity, RestPage.class).getBody();
        List<Movie> listMovies = page.getContent();
        // Add model attribute to be use on the front-end
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("search", search);
        model.addAttribute("listMovies", listMovies);
        return "/user/home";
    }
    /* END USER */


    /* ADMIN */
    // Endpoint for dashboard page
    @GetMapping("/dashboard")
    public String dashboard() 
    {
        return "/admin/dashboard";
    }

    // Endpoint for add user page
    @GetMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user) 
    {
        return "/admin/addUser";
    }

    // Endpoint for add movie page
    @GetMapping("/addMovie")
    public String addMovie(@ModelAttribute("movie") Movie movie) 
    {
        return "/admin/addMovie";
    }

    // Endpoint for getting list of movies page
    @GetMapping("/getMovies")
    public String getMovies(@ModelAttribute("movie") Movie movie, Model model) 
    {
        // Call movies pagination method
        return findPaginatedMovies(1, model);
    }

    // Endpoint for paginating list of movies
    @GetMapping("/getMovies/{pageNo}")
    public String findPaginatedMovies(@PathVariable(value = "pageNo") int pageNo, Model model) {
        HttpEntity<Integer> entity = new HttpEntity<Integer>(pageNo);
        // Call api to fetch list of movies
        RestPage<Movie> page = restTemplate.exchange("http://localhost:8083/movie/page/" + pageNo, HttpMethod.GET, entity, RestPage.class).getBody();
        List<Movie> listMovies = page.getContent();
        // Add model attribute to be use on the front-end
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listMovies", listMovies);
        return "/admin/getMovies";
    }

    // Endpoint for getting list of users 
    @GetMapping("/getUsers")
    public String getUsers(@ModelAttribute("user") User user, Model model) 
    {
        // Call users pagination method
        return findPaginatedUsers(1, model);
    }

    // Endpoint for paginating list of users
    @GetMapping("/getUsers/{pageNo}")
    public String findPaginatedUsers(@PathVariable(value = "pageNo") int pageNo, Model model) {
        HttpEntity<Integer> entity = new HttpEntity<Integer>(pageNo);
        // Call api to fetch list of users
        RestPage<Movie> page = restTemplate.exchange("http://localhost:8081/user/page/" + pageNo, HttpMethod.GET, entity, RestPage.class).getBody();
        List<Movie> listUsers = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listUsers", listUsers);
        return "/admin/getUsers";
    }

    // Endpoint for adding new user
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user, Model model)
    {
        try {
            HttpEntity<User> entity = new HttpEntity<User>(user);
            // Call api to post data for adding new user
            restTemplate.exchange("http://localhost:8081/user/addUser", HttpMethod.POST, entity, User.class).getBody();
            return "redirect:/getUsers";
        } catch (HttpStatusCodeException e) {
            String errorpayload = e.getResponseBodyAsString();
            model.addAttribute("msg", errorpayload);
            return "/admin/addUser";
        }
    }

    // Endpoint for adding new movie
    @PostMapping("/addMovie")
    public String addMovie(@ModelAttribute("movie") Movie movie, Model model)
    {
        HttpEntity<Movie> entity = new HttpEntity<Movie>(movie);
        // Call api to post data for adding new movie
        restTemplate.exchange("http://localhost:8083/movie/addMovie", HttpMethod.POST, entity, Movie.class).getBody();
        return "redirect:/getMovies";
    }
    /* END ADMIN */


    /* MOVIE */
    // Endpoint for getting specific movie details
    @GetMapping("/movieDetails")
    public String getMovieDetails(@ModelAttribute("movie") Movie movie, @ModelAttribute("favorite") Favorite favorite, @ModelAttribute("rating") Rating rating, @RequestParam int id, Model model, HttpSession session) {
        try {
            
            HttpEntity<Integer> entity = new HttpEntity<Integer>(id);
            // Declare object mapper to deserialize JSON object into Java object
            ObjectMapper mapper = new ObjectMapper();
            // Get recommended movie IDS
            List<Matrix> getRecommendedIDs = mapper.convertValue(restTemplate.exchange("http://localhost:8086/recommended/movies/" + id, HttpMethod.GET, entity, List.class).getBody(), new TypeReference<List<Matrix>>() {});

            // Find movie details based on ID to store in a List
            List<Movie> getRecommendedMovies = new ArrayList<Movie>();
            for (Matrix data : getRecommendedIDs) {
                // Call api to get recommended movies
                Movie getRecommendedMovie = restTemplate.exchange("http://localhost:8083/movie/movies/" + data.getMovieId(), HttpMethod.GET, entity, Movie.class).getBody();
                getRecommendedMovies.add(getRecommendedMovie);
            }
            // Add model attribute to be used in the front-end
            model.addAttribute("recommendedMovies", getRecommendedMovies);
        } catch (HttpStatusCodeException e) {
            String errorpayload = e.getResponseBodyAsString();
            model.addAttribute("msg", errorpayload);
        } finally {
            // Get the rest of specific movie details
            HttpEntity<Integer> entity = new HttpEntity<Integer>(id);
            // Call api to get specific movie
            Movie getMovie = restTemplate.exchange("http://localhost:8083/movie/movies/" + id, HttpMethod.GET, entity, Movie.class).getBody();
            model.addAttribute("movie", getMovie);
            // Get user's favorite movie
            Favorite getFavorite = restTemplate.exchange("http://localhost:8085/favorite/favorites/" + id + "/" + session.getAttribute("userId"), HttpMethod.GET, entity, Favorite.class).getBody();
            model.addAttribute("favorite", getFavorite);
            
            Rating getRating = restTemplate.exchange("http://localhost:8087/rating/ratings/" + id + "/" + session.getAttribute("userId"), HttpMethod.GET, entity, Rating.class).getBody();
            model.addAttribute("rating", getRating);
        }
        return "/user/movieDetails";
    }
    /* END MOVIE */

    /* FAVORITE */
    // Endpoint for adding favorite movie
    @PostMapping("/addFavorite")
    public String addFavorite(@ModelAttribute("favorite") Favorite favorite, Model model)
    {
        HttpEntity<Favorite> entity = new HttpEntity<Favorite>(favorite);
        // Call api to post data for adding new favorite movie
        restTemplate.exchange("http://localhost:8085/favorite/addFavorite", HttpMethod.POST, entity, Favorite.class).getBody();
        return "redirect:/movieDetails?id=" + favorite.getMovieId();
    }
    
    // Endpoint for getting all favorite movies
    @GetMapping("/favorites")
    public String getFavorites(@ModelAttribute("favorite") Favorite favorite, Model model, HttpSession session)
    {
        HttpEntity<Favorite> entity = new HttpEntity<Favorite>(favorite);
        // Declare object mapper to deserialize JSON object into Java object
        ObjectMapper mapper = new ObjectMapper();
        // Get favorite movies IDs
        List<Favorite> getFavoriteIDs = mapper.convertValue(restTemplate.exchange("http://localhost:8085/favorite/favorites/" + session.getAttribute("userId"), HttpMethod.GET, entity, List.class).getBody(), new TypeReference<List<Favorite>>() {});
        
        // Find favorite movie details based on ID to store in a List
        List<Movie> getFavoriteMovies = new ArrayList<Movie>();
        for (Favorite data : getFavoriteIDs) {
            // Call api to get specific favorite movie
            Movie getFavoriteMovie = restTemplate.exchange("http://localhost:8083/movie/movies/" + data.getMovieId(), HttpMethod.GET, entity, Movie.class).getBody();
            getFavoriteMovies.add(getFavoriteMovie);
        }
        model.addAttribute("favorites", getFavoriteMovies);
        return "/user/favorites";
    }
    
    // Endpoint for deleting favorite movie
    @PostMapping("/deleteFavorite/{id}")
    public String deleteFavorite(@ModelAttribute("favorite") Favorite favorite, @PathVariable(value = "id") int id, Model model)
    {
        HttpEntity<Integer> entity = new HttpEntity<Integer>(id);
        // Call api to delete specific movie
        restTemplate.exchange("http://localhost:8085/favorite/deleteFavorite/" + id, HttpMethod.POST, entity, String.class).getBody();
        return "redirect:/movieDetails?id=" + favorite.getMovieId();
    }
    /* END FAVORITE */

    /* RATING */
    // Endpoint for adding rating
    @PostMapping("/addRating")
    public String addRating(@ModelAttribute("rating") Rating rating, Model model)
    {
        System.out.println(rating);
        HttpEntity<Rating> entity = new HttpEntity<Rating>(rating);
        // Call api to post data for adding new rating
        restTemplate.exchange("http://localhost:8087/rating/addRating", HttpMethod.POST, entity, Rating.class).getBody();
        return "redirect:/movieDetails?id=" + rating.getMovieId();
    }
    /* END RATING */
}
