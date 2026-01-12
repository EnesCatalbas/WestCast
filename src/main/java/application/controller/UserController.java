package application.controller;

import application.model.Movie;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/rate", produces = "text/plain;charset=UTF-8")
    public String rateMovie(@RequestParam String movieName, @RequestParam int score) {
        userService.rate(movieName, score);
        return "You gave " + userService.rate(movieName, score) + " to " + movieName + " named movie.";
    }

    @GetMapping(path = "/search", produces = "application/json;charset=UTF-8")
    public Movie searchMovie(@RequestParam String movieName) {
        return userService.search(movieName);
    }

    @PostMapping(path = "/signUp", produces = "text/plain;charset=UTF-8")
    public String signUp(@RequestParam String userName, @RequestParam String password) {
        boolean result = userService.signup(userName, password);
        return result ? "Signup successful" : "User already exists";
    }

    @PostMapping(path = "/login", produces = "text/plain;charset=UTF-8")
    public String login(@RequestParam String userName, @RequestParam String password) {
        String token = userService.login(userName, password);
        return "login is successful! your token is: " + token;
    }

    @PostMapping(path = "/watchlist/add", produces = "text/plain;charset=UTF-8")
    public String addToWatchList(@RequestParam String userName, @RequestParam String movieName) {
        try {
            userService.addToWatchList(userName, movieName);
            return movieName + " listenize eklendi.";
        } catch (Exception e) {
            return "Hata: " + e.getMessage();
        }
    }

    @GetMapping(path = "/watchlist", produces = "application/json;charset=UTF-8")
    public java.util.Set<String> getWatchList(@RequestParam String userName) {
        return userService.getWatchList(userName);
    }
}
