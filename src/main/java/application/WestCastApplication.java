package application;


import application.model.Movie;
import application.service.MovieService;
import application.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;

@SpringBootApplication
public class WestCastApplication {

    public static void main(String[] args) {
        SpringApplication.run(WestCastApplication.class, args);
    }
}
