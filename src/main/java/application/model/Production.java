package application.model;

import org.springframework.stereotype.Component;

public class Production {

    private String name;
    private int time;
    private int rating;


    public Production(String name, int time){
        this.name = name;
        this.time = time;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}