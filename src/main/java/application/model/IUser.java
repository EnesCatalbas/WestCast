package application.model;

public interface IUser {

    public int rate(String movieName, int score);
    public application.model.Movie search(String movieName);
    public String login(String username, String password);
    boolean signup(String username, String password);
}
