package action;

import entertainment.Movie;
import entertainment.Season;
import entertainment.TvShow;
import user.User;


public final class Commands {
    private Commands() {

    }

    /**
     * Method that add a video to favorites list of a user
     */
    public static String addToFavorites(final User user, final String title) {
        if (user != null) {
            if (user.getHistory().containsKey(title)) {
                if (user.getFavorites().contains(title)) {
                    return "error -> " + title + " is already in favourite list";
                } else {
                    user.getFavorites().add(title);
                    return "success -> " + title + " was added as favourite";
                }
            } else {
                return "error -> " + title + " is not seen";
            }
        }
        return "error";
    }

    /**
     * Method that add a video to user's history if it wasn't seen
     * or increment the times that the user saw the video
     */
    public static String addToView(final User user, final String title) {
        if (user != null) {
            if (user.getHistory().containsKey(title)) {
                int watched = user.getHistory().get(title);
                user.getHistory().put(title, watched + 1);
            } else {
                user.getHistory().put(title, 1);
            }
            return "success -> " + title
                    + " was viewed with total views of " + user.getHistory().get(title);
        }
        return "error";
    }

    /**
     * Method that rate a tvshow if the user saw it
     */
    public static String rateTvShow(final User user, final double rating,
                                    final int numberSeason, final TvShow tvShow) {
        if (user != null) {
            if (user.getHistory().containsKey(tvShow.getTitle())) {
                if (user.getRated().contains(tvShow.getTitle() + numberSeason)) {
                    return "error -> " + tvShow.getTitle() + " has been already rated";
                } else {
                    int count = 1;
                    Season rateSeason = null;
                    for (int i = 0; i < tvShow.getSeasons().size(); ++i) {
                        if (count == numberSeason) {
                            rateSeason = tvShow.getSeasons().get(i);
                        }
                        count++;
                    }
                    if (rateSeason != null) {
                        rateSeason.getRatings().add(rating);
                        if (!tvShow.getUsersRated().contains(user.getUsername())) {
                            tvShow.getUsersRated().add(user.getUsername());
                        }
                        user.setRatingNumber(user.getRatingNumber() + 1);
                        user.getRated().add(tvShow.getTitle() + numberSeason);
                        return "success -> " + tvShow.getTitle()
                                + " was rated with " + rating + " by " + user.getUsername();
                    } else {
                        return "error";
                    }
                }
            } else {
                return "error -> " + tvShow.getTitle() + " is not seen";
            }
        }
        return "error -> " + tvShow.getTitle() + " is not seen";
    }

    /**
     * Method that rate a movie if the user saw it
     */
    public static String rateMovie(final User user, final double rating, final Movie movie) {
        if (user != null) {
            if (user.getHistory().containsKey(movie.getTitle())) {
                if (movie.getRatings().containsKey(user.getUsername())) {
                    return "error -> " + movie.getTitle() + " has been already rated";
                } else {
                    movie.getRatings().put(user.getUsername(), rating);
                    user.setRatingNumber(user.getRatingNumber() + 1);
                    return "success -> " + movie.getTitle()
                            + " was rated with " + rating + " by " + user.getUsername();
                }
            }
        }
        return "error -> " + movie.getTitle() + " is not seen";
    }

}
