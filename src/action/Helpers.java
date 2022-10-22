package action;

import actor.Actor;
import actor.ActorsAwards;
import entertainment.Movie;
import entertainment.TvShow;
import entertainment.VideoComparators;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Helpers {

    private Helpers() {
    }
    /**
     * Method that sets the number of views of each tvshow
     */
    public static void viewSetS(final ArrayList<TvShow> tvShows, final ArrayList<User> users) {
        for (int i = 0; i < tvShows.size(); ++i) {
            tvShows.get(i).setViews(0);
            for (int j = 0; j < users.size(); ++j) {
                if (users.get(j).getHistory().containsKey(tvShows.get(i).getTitle())) {
                    tvShows.get(i).setViews(tvShows.get(i).getViews()
                            + users.get(j).getHistory().get(tvShows.get(i).getTitle()));
                }
            }
        }
    }

    /**
     * Method that sets the number of views of each movie
     */
    public static void viewSetM(final ArrayList<Movie> movies, final ArrayList<User> users) {
        for (int i = 0; i < movies.size(); ++i) {
            movies.get(i).setViews(0);
            for (int j = 0; j < users.size(); ++j) {
                if (users.get(j).getHistory().containsKey(movies.get(i).getTitle())) {
                    movies.get(i).setViews(movies.get(i).getViews()
                            + users.get(j).getHistory().get(movies.get(i).getTitle()));
                }
            }
        }
    }

    /**
     * Method that sets the number of times a movie is in favorites
     * list of the users
     */
    public static void setFavoritesM(final ArrayList<Movie> movie, final ArrayList<User> users) {
        for (Movie value : movie) {
            for (User user : users) {
                if (user.getFavorites().contains(value.getTitle())) {
                    value.setFavorites(value.getFavorites() + 1);
                }
            }
        }
    }

    /**
     * Method that sets the number of times a tvshow is in favorites
     * list of the users
     */
    public static void setFavoritesS(final ArrayList<TvShow> tvShow, final ArrayList<User> users) {
        for (TvShow show : tvShow) {
            for (User user : users) {
                if (user.getFavorites().contains(show.getTitle())) {
                    show.setFavorites(show.getFavorites() + 1);
                }
            }
        }
    }

    /**
     * Method that sets the duration of all the tvshows
     */
    public static void setDurationS(final ArrayList<TvShow> tvShows) {
        for (int i = 0; i < tvShows.size(); ++i) {
            tvShows.get(i).durationShow();
        }
    }

    /**
     * Method that sets the average rating of all actors
     */
    public static void setRatingScoreActors(final ArrayList<Actor> actors,
                                            final ArrayList<TvShow> tvShows,
                                            final ArrayList<Movie> movies) {
        double ratingScore;
        int number;

        for (int i = 0; i < movies.size(); ++i) {
            movies.get(i).ratingScore();
        }
        for (int i = 0; i < tvShows.size(); ++i) {
            tvShows.get(i).ratingScore();
        }

        for (int i = 0; i < actors.size(); ++i) {
            ratingScore = 0;
            number = 0;
            for (int j = 0; j < movies.size(); ++j) {
                if (movies.get(j).getActors().contains(actors.get(i).getName())) {
                    if (movies.get(j).getVideoRating() != 0) {
                        number++;
                        ratingScore = ratingScore + movies.get(j).getVideoRating();
                    }
                }
            }
            for (int j = 0; j < tvShows.size(); ++j) {
                if (tvShows.get(j).getActors().contains(actors.get(i).getName())) {
                    if (tvShows.get(j).getVideoRating() != 0) {
                        number++;
                        ratingScore = ratingScore + tvShows.get(j).getVideoRating();
                    }
                }
            }
            if (number != 0) {
                actors.get(i).setAverageRating(ratingScore / number);
            } else {
                actors.get(i).setAverageRating(0);
            }
        }
    }

    /**
     * Method that sets the number of awards the actors have
     */
    public static void numberOfAwardsActor(final ArrayList<Actor> actors) {
        int numberOfAwards = 0;
        for (Actor actor : actors) {
            for (Map.Entry<ActorsAwards, Integer> award : actor.getAwards().entrySet()) {
                numberOfAwards = numberOfAwards + award.getValue();
            }
            actor.setAwardNumber(numberOfAwards);
            numberOfAwards = 0;
        }
    }

    /**
     * Method that sorts the genres based on the views of each genre
     */
    public static ArrayList<Map.Entry<String, Integer>> sortFavoritesGenres(
            final ArrayList<Movie> movies, final ArrayList<TvShow> tvShows,
            final ArrayList<User> users) {
        Map<String, Integer> genre = new HashMap<>();
        viewSetM(movies, users);
        viewSetS(tvShows, users);
        for (Movie movie : movies) {
            for (int j = 0; j < movie.getGenres().size(); ++j) {
                if (genre.containsKey(movie.getGenres().get(j))) {
                    genre.put(movie.getGenres().get(j), genre.get(movie.getGenres().get(j))
                            + movie.getViews());
                } else {
                    genre.put(movie.getGenres().get(j), movie.getViews());
                }
            }
        }
        for (TvShow tvshow : tvShows) {
            for (int j = 0; j < tvshow.getGenres().size(); ++j) {
                if (genre.containsKey(tvshow.getGenres().get(j))) {
                    genre.put(tvshow.getGenres().get(j), genre.get(tvshow.getGenres().get(j))
                            + tvshow.getViews());
                } else {
                    genre.put(tvshow.getGenres().get(j), tvshow.getViews());
                }
            }
        }
        ArrayList<Map.Entry<String, Integer>> genresRank = new ArrayList<>(genre.entrySet());
        VideoComparators.SortGenreByViews sortGenre = new VideoComparators.SortGenreByViews();
        VideoComparators.SortGenreByTitle sortTitle = new VideoComparators.SortGenreByTitle();
        genresRank.sort(sortGenre);
        genresRank.sort(sortTitle);
        return genresRank;
    }

    /**
     * Method that filter the movies based on the input requests.
     * They will only remain the movies that fulfil the requests
     */
    public static void filterMovies(final ArrayList<Movie> movies,
                                    final List<List<String>> filters) {
        if (filters.get(0).get(0) != null) {
            int year = Integer.parseInt(filters.get(0).get(0));
            movies.removeIf((movie) -> movie.getYear() != year);
        }
        if (filters.get(1).get(0) != null) {
            for (int i = 0; i < filters.get(1).size(); ++i) {
                int finalI = i;
                movies.removeIf((movie) -> !movie.getGenres().contains(filters.get(1).get(finalI)));
            }
        }
    }

    /**
     * Method that filter the tvshows based on the input requests.
     * They will only remain the tvshows that fulfil the requests
     */
    public static void filterTvShows(final ArrayList<TvShow> tvShows,
                                     final List<List<String>> filters) {
        if (filters.get(0).get(0) != null) {
            int year = Integer.parseInt(filters.get(0).get(0));
            tvShows.removeIf((tvShow) -> tvShow.getYear() != year);
        }
        if (filters.get(1).get(0) != null) {
            for (int i = 0; i < filters.get(1).size(); ++i) {
                int finalI = i;
                tvShows.removeIf((tvShow) -> !tvShow.getGenres().contains(
                                                                      filters.get(1).get(finalI)));
            }
        }
    }

}
