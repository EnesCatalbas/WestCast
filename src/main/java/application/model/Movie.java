package application.model;

import org.springframework.stereotype.Component;

@Component
public class Movie extends application.model.Production{


    public Movie() {
        super("Default Name", 120);
    }

}
