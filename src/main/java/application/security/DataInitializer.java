package application.security;
import application.service.MovieService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DataInitializer {

    private final MovieService movieService;

    public DataInitializer(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostConstruct
    public void loadMovies() {
        movieService.addMovie("interstaller", 210);
        movieService.addMovie("matrix", 136);
        movieService.addMovie("inception", 148);
        movieService.addMovie("avatar", 162);
    }
}
