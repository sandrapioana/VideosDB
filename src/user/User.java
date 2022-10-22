package user;

import java.util.ArrayList;
import java.util.Map;

public final class User {
    /**
     * Name of the user
     */
    private final String username;
    /**
     * Type of subscription
     */
    private final String subscription;
    /**
     * History of the user which stocks the name of the video
     * and the number of times the user watched it
     */
    private final Map<String, Integer> history;
    /**
     * List of favorite videos of the user
     */
    private final ArrayList<String> favorites;
    /**
     * Number of ratings made by the user
     */
    private int ratingNumber;
    /**
     * List of TvShows user rated
     * Each String will contain name and season of the TvShow
     */
    private final ArrayList<String> rated;

    /**
     * Class constructor
     */
    public User(final String username, final String subscription,
                final Map<String, Integer> history, final ArrayList<String> favorites) {
        this.username = username;
        this.subscription = subscription;
        this.history = history;
        this.favorites = favorites;
        this.rated = new ArrayList<>();
    }

    /**
     * Getters and Setters for the fields of the class
     */
    public String getUsername() {
        return username;
    }

    public String getSubscription() {
        return subscription;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    public int getRatingNumber() {
        return ratingNumber;
    }

    public void setRatingNumber(final int ratingNumber) {
        this.ratingNumber = ratingNumber;
    }

    public ArrayList<String> getRated() {
        return rated;
    }
}
