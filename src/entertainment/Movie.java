package entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Movie extends Video {
    /**
     * Duration of the Movie
     */
    private final int duration;
    /**
     * Map which contains the username and its rating
     */
    private final Map<String, Double> ratings;

    /**
     * Class constructor
     */
    public Movie(final String title, final int year, final ArrayList<String> genres,
                 final ArrayList<String> actors, final int duration) {
        super(title, year, genres, actors);
        this.duration = duration;
        this.ratings = new HashMap<>();
    }

    /**
     * Getters for the fields of the class
     */
    public int getDuration() {
        return duration;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    /**
     * Method that sets the rating score of the TvShow
     */
    public void ratingScore() {
        double ratingScore = 0;
        int nr = 0;
        for (Map.Entry<String, Double> entry : ratings.entrySet()) {
            ratingScore = ratingScore + entry.getValue();
            nr++;
        }
        if (nr != 0) {
            setVideoRating(ratingScore / nr);
        } else {
            setVideoRating(0);
        }
    }

}
