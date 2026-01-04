package application.controller;

import application.model.Movie;
import application.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/search")
    public List<Movie> search(@RequestParam String q) {
        return movieService.searchMovie(q);
    }
}
