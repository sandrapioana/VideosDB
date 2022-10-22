package entertainment;

import java.util.ArrayList;

public final class TvShow extends Video {
    /**
     * Seasons of the TV Show
     */
    private final ArrayList<Season> seasons;
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Duration of the TvShow
     */
    private int duration;
    /**
     * The users who rated at least one season of the tvShow
     */
    private final ArrayList<String> usersRated;

    /**
     * Class constructor
     */
    public TvShow(final String title, final int year, final ArrayList<String> genres,
                  final ArrayList<String> actors, final ArrayList<Season> seasons,
                  final int numberOfSeasons) {
        super(title, year, genres, actors);
        this.seasons = seasons;
        this.numberOfSeasons = numberOfSeasons;
        this.usersRated = new ArrayList<>();
    }

    /**
     * Getters and Setters for the fields of the class
     */
    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getUsersRated() {
        return usersRated;
    }

    /**
     * Method that sets the rating score of the TvShow
     */
    public void ratingScore() {
        double ratingScore = 0;
        int numberOfRatings = 0;
        for (int i = 0; i < seasons.size(); ++i) {
            for (int j = 0; j < seasons.get(i).getRatings().size(); ++j) {
                ratingScore = ratingScore + seasons.get(i).getRatings().get(j);
                numberOfRatings++;
            }
        }
        if (numberOfRatings != 0) {
            setVideoRating(ratingScore / (seasons.size() * usersRated.size()));
        } else {
            setVideoRating(0);
        }
    }

    /**
     * Method that sets the duration of the TvShow
     */
    public void durationShow() {
        this.duration = 0;
        for (int i = 0; i < seasons.size(); ++i) {
            this.duration = this.duration + seasons.get(i).getDuration();
        }
    }

}
