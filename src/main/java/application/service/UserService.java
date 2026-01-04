package application.service;


import application.model.IUser;
import application.model.Movie;
import application.model.User;
import application.repository.UserRepository;
import application.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements IUser {

    ArrayList<User>  userList = new ArrayList<User>();

    @Autowired
    application.service.MovieService movieService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public int rate(String movieName, int score) {

        try{
            Movie foundmovie = search(movieName);
            foundmovie.setRating(score);
            return foundmovie.getRating();
        } catch (Exception e) {
            throw new RuntimeException("Movie can not be found!");
        }
    }

    @Override
    public Movie search(String movieName) {

        for (Movie movie : movieService.getMovieList()) {
            if (movie.getName().equalsIgnoreCase(movieName)) {
                return movie;
            }
        }
        return null;
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String login(String username, String password) {
        User currentUser = userRepository.findByUserName(username);

        if (currentUser != null && currentUser.getPassword().equals(password)) {
            return jwtUtil.generateToken(username);
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }


    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean signup(String username, String password) {
        if (userRepository.findByUserName(username) != null) {
            return false;
        }
        userRepository.save(new User(username, password));
        return true;
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}