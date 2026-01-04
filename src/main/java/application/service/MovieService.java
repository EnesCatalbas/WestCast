package application.service;


import application.model.IAdmin;
import application.model.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService implements IAdmin {

    private ArrayList<Movie> movieList = new ArrayList();

    @Override
    public void addMovie(String movieName, int movieTime) {
        Movie movie = new Movie();
        movie.setName(movieName);
        movie.setTime(movieTime);
        movieList.add(movie);
    }

    public List<Movie> searchMovie(String keyword) {
        return movieList.stream()
                .filter(m -> m.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }


    public ArrayList<Movie> getMovieList(){
        return movieList;
    }
}
