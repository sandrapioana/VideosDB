package entertainment;

import java.util.ArrayList;

public abstract class Video {
    /**
     * Title of the Video
     */
    private final String title;
    /**
     * Launch year
     */
    private final int year;
    /**
     * Genres of the video
     */
    private final ArrayList<String> genres;
    /**
     * Actors of the video
     */
    private final ArrayList<String> actors;
    /**
     * Number of views of the video
     */
    private int views;
    /**
     * How many times the video was added to favorites
     */
    private int favorites;
    /**
     * The rating of the video
     */
    private double videoRating;

    /**
     * Class constructor
     */
    public Video(final String title, final int year,
                 final ArrayList<String> genres, final ArrayList<String> actors) {
        this.title =  title;
        this.year = year;
        this.genres = genres;
        this.actors = actors;
    }

    /**
     * Getter for the title of the video
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the debut year of the video
     */
    public int getYear() {
        return year;
    }

    /**
     * Getter for the genres of the video
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * Getter for the actors of the video
     */
    public ArrayList<String> getActors() {
        return actors;
    }

    /**
     * Getter for the number of views of the video
     */
    public int getViews() {
        return views;
    }

    /**
     * Getter for how many times the video is in favorites lists
     */
    public int getFavorites() {
        return favorites;
    }

    /**
     * Getter for the rating of the video
     */
    public double getVideoRating() {
        return videoRating;
    }

    /**
     * Setter for the number of views of the video
     */
    public void setViews(final int views) {
        this.views = views;
    }

    /**
     * Setter for how many times the video is in favorites lists
     */
    public void setFavorites(final int favorites) {
        this.favorites = favorites;
    }

    /**
     * Setter for the rating of the video
     */
    public void setVideoRating(final double videoRating) {
        this.videoRating = videoRating;
    }

    /**
     * Abstract method that set the rating score in each class that
     * extends Video class
     */
    public abstract void ratingScore();
}
