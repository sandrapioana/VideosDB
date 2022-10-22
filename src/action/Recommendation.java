package action;

import common.Constants;
import entertainment.Movie;
import entertainment.TvShow;
import entertainment.Video;
import entertainment.VideoComparators;
import user.User;

import java.util.ArrayList;
import java.util.Map;

public class Recommendation {
    private final ArrayList<User> users;
    private final ArrayList<Movie> movies;
    private final ArrayList<TvShow> tvShows;

    /**
     * Method that adds the lists of all users, all movies and all tvshows
     * to work with in recommendation
     */
    public Recommendation(final ArrayList<User> allUsers, final ArrayList<Movie> allMovies,
                            final ArrayList<TvShow> allTvShows) {
        this.users = allUsers;
        this.movies = allMovies;
        this.tvShows = allTvShows;
    }

    /**
     * Method that returns the title of the first video the user
     * hasn't seen
     */
    public String standard(final User user) {
        for (int i = 0; i < movies.size(); ++i) {
            if (!user.getHistory().containsKey(movies.get(i).getTitle())) {
                return "StandardRecommendation result: " + movies.get(i).getTitle();
            }
        }
        for (int i = 0; i < tvShows.size(); ++i) {
            if (!user.getHistory().containsKey(tvShows.get(i).getTitle())) {
                return "StandardRecommendation result: " + tvShows.get(i).getTitle();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    /**
     * Method that returns the title of the best unseen video the user
     * hasn't seen based on the ratings
     */
    public String bestUnseen(final User user) {
        ArrayList<Movie> copyOfMovies = new ArrayList<>(movies);
        copyOfMovies.removeIf((movie) -> user.getHistory().containsKey(movie.getTitle()));
        for (int i = 0; i < copyOfMovies.size(); ++i) {
            copyOfMovies.get(i).ratingScore();
        }

        ArrayList<TvShow> copyOfTvShows = new ArrayList<>(tvShows);
        copyOfTvShows.removeIf((tvShow) -> user.getHistory().containsKey(tvShow.getTitle()));
        for (int i = 0; i < copyOfTvShows.size(); ++i) {
            copyOfTvShows.get(i).ratingScore();
        }

        ArrayList<Video> unseenVideos = new ArrayList<>();
        unseenVideos.addAll(copyOfMovies);
        unseenVideos.addAll(copyOfTvShows);
        VideoComparators.SortVideoByRating sortVideos = new VideoComparators.SortVideoByRating();
        unseenVideos.sort(sortVideos.reversed());

        if (unseenVideos.size() == 0) {
            return "BestRatedUnseenRecommendation cannot be applied!";
        } else {
            return "BestRatedUnseenRecommendation result: " + unseenVideos.get(0).getTitle();
        }

    }

    /**
     * Method that returns the title of the most popular video the user
     * hasn't seen from the most popular genre.
     * Just for premium users
     */
    public String popular(final User user) {
        StringBuilder output = new StringBuilder();
        if (user.getSubscription().equals(Constants.PREMIUM)) {
            ArrayList<Map.Entry<String, Integer>> genres =
                    Helpers.sortFavoritesGenres(movies, tvShows, users);
            for (int i = 0; i < genres.size(); ++i) {
                ArrayList<Video> unseenVideos = new ArrayList<>();
                unseenVideos.addAll(movies);
                unseenVideos.addAll(tvShows);
                int finalI = i;
                unseenVideos.removeIf((video) -> user.getHistory().containsKey(video.getTitle()));
                unseenVideos.removeIf((video) -> video.getGenres().contains(
                                                        genres.get(finalI).getKey()));
                if (unseenVideos.size() != 0) {
                    output.append("PopularRecommendation result: ");
                    output.append(unseenVideos.get(0).getTitle());
                    return output.toString();
                }
            }
            output.append("PopularRecommendation cannot be applied!"); //pe acest gen, trecem la urm
        } else {
            output.append("PopularRecommendation cannot be applied!");
        }
        return output.toString();
    }

    /**
     * Method that returns the title of the video which is the most
     * placed in favorites list of the users
     * Just for premium users
     */
    public String favorite(final User user) {
        if (user.getSubscription().equals(Constants.PREMIUM)) {
            int maxFav = 0;
            ArrayList<Video> unseenVideos = new ArrayList<>();

            ArrayList<Movie> copyOfMovies = new ArrayList<>(movies);
            copyOfMovies.removeIf((movie) -> user.getHistory().containsKey(movie.getTitle()));
            Helpers.setFavoritesM(movies, users);
            Helpers.setFavoritesM(copyOfMovies, users);
            copyOfMovies.removeIf((movie) -> movie.getFavorites() == 0);

            ArrayList<TvShow> copyOfTvShow = new ArrayList<>(tvShows);
            copyOfTvShow.removeIf((tvShow) -> user.getHistory().containsKey(tvShow.getTitle()));
            Helpers.setFavoritesS(tvShows, users);
            Helpers.setFavoritesS(copyOfTvShow, users);
            copyOfTvShow.removeIf((tvShow) -> tvShow.getFavorites() == 0);

            unseenVideos.addAll(copyOfMovies);
            unseenVideos.addAll(copyOfTvShow);
            VideoComparators.SortVideoByFavorites sortVideo =
                                            new VideoComparators.SortVideoByFavorites();
            unseenVideos.sort(sortVideo);

            if (unseenVideos.size() != 0) {
                maxFav = unseenVideos.get(unseenVideos.size() - 1).getFavorites();
                for (Movie movie : copyOfMovies) {
                    if (movie.getFavorites() == maxFav) {
                        return "FavoriteRecommendation result: " + movie.getTitle();
                    }
                }
                for (TvShow tvShow : copyOfTvShow) {
                    if (tvShow.getFavorites() == maxFav) {
                        return "FavoriteRecommendation result: " + tvShow.getTitle();
                    }
                }
            } else {
                return "FavoriteRecommendation cannot be applied!";
            }
        }
        return "FavoriteRecommendation cannot be applied!";
    }

    /**
     * Method that returns the titles of the videos the user hasn't seen
     * of a certain genre
     * Just for premium users
     */
    public String search(final User user, final String inputGenre) {
        StringBuilder output = new StringBuilder();
        if (user.getSubscription().equals(Constants.PREMIUM)) {
            ArrayList<Video> unseenVideos = new ArrayList<>();
            VideoComparators.SortVideoByRating sortVideoRating =
                                                new VideoComparators.SortVideoByRating();
            VideoComparators.SortVideoByTitle sortVideoTitle =
                                                new VideoComparators.SortVideoByTitle();

            ArrayList<Movie> copyOfMovies = new ArrayList<>(movies);
            copyOfMovies.removeIf((movie) -> user.getHistory().containsKey(movie.getTitle()));
            copyOfMovies.removeIf((movie) -> !movie.getGenres().contains(inputGenre));

            ArrayList<TvShow> copyOfTvShow = new ArrayList<>(tvShows);
            copyOfTvShow.removeIf((tvShow) -> user.getHistory().containsKey(tvShow.getTitle()));
            copyOfTvShow.removeIf((tvShow) -> !tvShow.getGenres().contains(inputGenre));

            unseenVideos.addAll(copyOfMovies);
            unseenVideos.addAll(copyOfTvShow);
            unseenVideos.sort(sortVideoTitle);
            unseenVideos.sort(sortVideoRating);

            if (unseenVideos.size() != 0) {
                output.append("SearchRecommendation result: [");
                output.append(unseenVideos.get(0).getTitle());
                for (int i = 1; i < unseenVideos.size(); ++i) {
                    output.append(", ");
                    output.append(unseenVideos.get(i).getTitle());
                }
                output.append("]");
            } else {
                output.append("SearchRecommendation cannot be applied!");
            }

        } else {
            output.append("SearchRecommendation cannot be applied!");
        }
        return output.toString();
    }

}
