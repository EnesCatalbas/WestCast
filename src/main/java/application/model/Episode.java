package application.model;

public class Episode extends application.model.Production {

    int epNumber;

    public Episode(String name, int time, int epNumber, int rating) {
        super(name, time);
        this.epNumber = epNumber;
    }
}
