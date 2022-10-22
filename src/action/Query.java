package action;

import actor.Actor;
import actor.ActorsAwards;
import actor.ActorsComparators;
import common.Constants;
import entertainment.Movie;
import entertainment.TvShow;
import entertainment.VideoComparators;
import fileio.ActionInputData;
import user.User;
import user.UsersComparators;

import java.util.ArrayList;
import java.util.Arrays;

public class Query {
    private final ArrayList<User> users;
    private final ArrayList<Actor> actors;
    private final ArrayList<Movie> movies;
    private final ArrayList<TvShow> tvShows;

    /**
     * Method that adds the lists of all users, all movies, all tvshows
     * and all actors to work with in queries
     */
    public Query(final ArrayList<User> allUsers, final ArrayList<Actor> allActors,
                            final ArrayList<Movie> allMovies, final ArrayList<TvShow> allTvShows) {
        this.users = allUsers;
        this.actors = allActors;
        this.movies = allMovies;
        this.tvShows = allTvShows;
    }

    // ACTORS QUERIES

    /**
     * Method that returns the first "n" names of actors with the lowest/
     * highest average based on videos ratings in which they played
     */
    public String averageActors(final ActionInputData action) {
        ActorsComparators.SortActorsByName sortName = new ActorsComparators.SortActorsByName();
        ActorsComparators.SortActorsByRating sortRating =
                                                    new ActorsComparators.SortActorsByRating();
        StringBuilder output = new StringBuilder();
        ArrayList<Actor> copyOfActors = new ArrayList<>(actors);
        Helpers.setRatingScoreActors(copyOfActors, tvShows, movies);
        copyOfActors.removeIf((actor) -> actor.getAverageRating() == 0);
        if (action.getSortType().equals(Constants.ASCENDING)) {
            copyOfActors.sort(sortName);
            copyOfActors.sort(sortRating);
        } else {
            copyOfActors.sort(sortName.reversed());
            copyOfActors.sort(sortRating.reversed());
        }
        if (copyOfActors.size() != 0) {
            output.append(copyOfActors.get(0).getName());
            for (int i = 1; i < copyOfActors.size() && i < action.getNumber(); ++i) {
                output.append(", ");
                output.append(copyOfActors.get(i).getName());
            }
        }
        return output.toString();
    }

    /**
     * Method that returns the first "n" names of actors with the lowest/
     * highest number of awards given in input
     */
    public String awardsActors(final ActionInputData action) {
        ActorsComparators.SortActorsByName sortName =
                                                    new ActorsComparators.SortActorsByName();
        ActorsComparators.SortActorsByNrAwards sortNrAwards =
                                                    new ActorsComparators.SortActorsByNrAwards();
        ArrayList<Actor> copyOfActors = new ArrayList<>(actors);
        StringBuilder output = new StringBuilder();
        int index = action.getFilters().size() - 1;
        for (int i = 0; i < action.getFilters().get(index).size(); ++i) {
            ActorsAwards award = ActorsAwards.valueOf(action.getFilters().get(index).get(i));
            copyOfActors.removeIf((actor) -> !actor.getAwards().containsKey(award));
        }
        Helpers.numberOfAwardsActor(copyOfActors);
        if (action.getSortType().equals(Constants.ASCENDING)) {
            copyOfActors.sort(sortName);
            copyOfActors.sort(sortNrAwards);
        } else {
            copyOfActors.sort(sortName.reversed());
            copyOfActors.sort(sortNrAwards.reversed());
        }
        if (copyOfActors.size() != 0) {
            output.append(copyOfActors.get(0).getName());
            for (int i = 1; i < copyOfActors.size() && i < action.getNumber(); ++i) {
                output.append(", ");
                output.append(copyOfActors.get(i).getName());
            }
        }
        return output.toString();

    }

    /**
     * Method that returns the first "n" names of actors which have in
     * their description the words given in input
     */
    public String descriptionActors(final ActionInputData action) {
        ActorsComparators.SortActorsByName sortName = new ActorsComparators.SortActorsByName();
        ArrayList<Actor> copyOfActors = new ArrayList<>(actors);
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < actors.size(); ++i) {
            String[] split = actors.get(i).getCareerDescription().split("\\W+");
            ArrayList<String> splittedDescription = new ArrayList<>(Arrays.asList(split));
            splittedDescription.replaceAll(String::toLowerCase);
            int index = -1;
            for (int j = 0; j < action.getFilters().get(2).size(); ++j) {
                if (!splittedDescription.contains(
                                            action.getFilters().get(2).get(j).toLowerCase())) {
                    for (int k = 0; k < copyOfActors.size(); ++k) {
                        if (copyOfActors.get(k).getName().equals(actors.get(i).getName())) {
                            index = k;
                        }
                    }
                }
            }
            if (index != -1) {
                copyOfActors.remove(index);
            }
        }

        if (action.getSortType().equals(Constants.ASCENDING)) {
            copyOfActors.sort(sortName);
        } else {
            copyOfActors.sort(sortName.reversed());
        }
        if (copyOfActors.size() != 0) {
            output.append(copyOfActors.get(0).getName());
            for (int i = 1; i < copyOfActors.size() && i < action.getNumber(); ++i) {
                output.append(", ");
                output.append(copyOfActors.get(i).getName());
            }
        }
        return output.toString();
    }

    // VIDEOS QUERIES

    /**
     * Method that returns the first "n" titles of videos with the lowest/
     * highest rating
     */
    public String ratingVideo(final ActionInputData action) {
        VideoComparators.SortVideoByTitle sortTitle = new VideoComparators.SortVideoByTitle();
        VideoComparators.SortVideoByRating sortRating = new VideoComparators.SortVideoByRating();
        StringBuilder output = new StringBuilder();

        if (action.getObjectType().equals(Constants.MOVIES)) {
            ArrayList<Movie> copyOfMovies = new ArrayList<>(movies);
            Helpers.filterMovies(copyOfMovies, action.getFilters());
            for (int i = 0; i < copyOfMovies.size(); ++i) {
                copyOfMovies.get(i).ratingScore();
            }
            copyOfMovies.removeIf((movie) -> movie.getVideoRating() == 0);
            if (action.getSortType().equals(Constants.ASCENDING)) {
                copyOfMovies.sort(sortTitle);
                copyOfMovies.sort(sortRating);
            } else {
                copyOfMovies.sort(sortTitle.reversed());
                copyOfMovies.sort(sortRating.reversed());
            }
            if (copyOfMovies.size() != 0) {
                output.append(copyOfMovies.get(0).getTitle());
                for (int i = 1; i < copyOfMovies.size() && i < action.getNumber(); ++i) {
                    output.append(", ");
                    output.append(copyOfMovies.get(i).getTitle());
                }
            }
            return output.toString();
        } else if (action.getObjectType().equals(Constants.SHOWS)) {
            ArrayList<TvShow> copyOfTvShows = new ArrayList<>(tvShows);
            Helpers.filterTvShows(copyOfTvShows, action.getFilters());
            for (int i = 0; i < copyOfTvShows.size(); ++i) {
                copyOfTvShows.get(i).ratingScore();
            }
            copyOfTvShows.removeIf((movie) -> movie.getVideoRating() == 0);
            if (action.getSortType().equals(Constants.ASCENDING)) {
                copyOfTvShows.sort(sortTitle);
                copyOfTvShows.sort(sortRating);
            } else {
                copyOfTvShows.sort(sortTitle.reversed());
                copyOfTvShows.sort(sortRating.reversed());
            }
            if (copyOfTvShows.size() != 0) {
                output.append(copyOfTvShows.get(0).getTitle());
                for (int i = 1; i < copyOfTvShows.size() && i < action.getNumber(); ++i) {
                    output.append(", ");
                    output.append(copyOfTvShows.get(i).getTitle());
                }
            }
            return output.toString();
        }
        return  output.toString();
    }

    /**
     * Method that returns the first "n" titles of videos with the lowest/
     * highest number of appearances in favorites lists of the users
     */
    public String favoriteVideo(final ActionInputData action) {
        VideoComparators.SortVideoByTitle sortTitle =
                                        new VideoComparators.SortVideoByTitle();
        VideoComparators.SortVideoByFavorites sortFavorites =
                                        new VideoComparators.SortVideoByFavorites();
        StringBuilder output = new StringBuilder();

        if (action.getObjectType().equals(Constants.MOVIES)) {
            ArrayList<Movie> copyOfMovies = new ArrayList<>(movies);
            Helpers.filterMovies(copyOfMovies, action.getFilters());
            Helpers.setFavoritesM(copyOfMovies, users);
            copyOfMovies.removeIf((movie) -> movie.getFavorites() == 0);
            if (action.getSortType().equals(Constants.ASCENDING)) {
                copyOfMovies.sort(sortTitle);
                copyOfMovies.sort(sortFavorites);
            } else {
                copyOfMovies.sort(sortTitle.reversed());
                copyOfMovies.sort(sortFavorites.reversed());
            }
            if (copyOfMovies.size() != 0) {
                output.append(copyOfMovies.get(0).getTitle());
                for (int i = 1; i < copyOfMovies.size() && i < action.getNumber(); ++i) {
                    output.append(", ");
                    output.append(copyOfMovies.get(i).getTitle());
                }
            }
            return output.toString();
        } else if (action.getObjectType().equals(Constants.SHOWS)) {
            ArrayList<TvShow> copyOfTvShows = new ArrayList<>(tvShows);
            Helpers.filterTvShows(copyOfTvShows, action.getFilters());
            Helpers.setFavoritesS(copyOfTvShows, users);
            copyOfTvShows.removeIf((tvShow) -> tvShow.getFavorites() == 0);
            if (action.getSortType().equals(Constants.ASCENDING)) {
                copyOfTvShows.sort(sortTitle);
                copyOfTvShows.sort(sortFavorites);
            } else {
                copyOfTvShows.sort(sortTitle.reversed());
                copyOfTvShows.sort(sortFavorites.reversed());
            }
            if (copyOfTvShows.size() != 0) {
                output.append(copyOfTvShows.get(0).getTitle());
                for (int i = 1; i < copyOfTvShows.size() && i < action.getNumber(); ++i) {
                    output.append(", ");
                    output.append(copyOfTvShows.get(i).getTitle());
                }
            }
            return output.toString();
        }
        return output.toString();
    }

    /**
     * Method that returns the first "n" titles of videos with the lowest/
     * highest duration
     */
    public String longestVideo(final ActionInputData action) {
        VideoComparators.SortVideoByTitle sortTitle =
                                            new VideoComparators.SortVideoByTitle();
        StringBuilder output = new StringBuilder();
        if (action.getObjectType().equals(Constants.MOVIES)) {
            VideoComparators.SortVideoByDurationM sortDurationM =
                                        new VideoComparators.SortVideoByDurationM();
            ArrayList<Movie> copyOfMovies = new ArrayList<>(movies);
            Helpers.filterMovies(copyOfMovies, action.getFilters());
            if (action.getSortType().equals(Constants.ASCENDING)) {
                copyOfMovies.sort(sortTitle);
                copyOfMovies.sort(sortDurationM);
            } else {
                copyOfMovies.sort(sortTitle.reversed());
                copyOfMovies.sort(sortDurationM.reversed());
            }
            if (copyOfMovies.size() != 0) {
                output.append(copyOfMovies.get(0).getTitle());
                for (int i = 1; i < copyOfMovies.size() && i < action.getNumber(); ++i) {
                    output.append(", ");
                    output.append(copyOfMovies.get(i).getTitle());
                }
            }
            return output.toString();
        } else if (action.getObjectType().equals(Constants.SHOWS)) {
            VideoComparators.SortVideoByDurationS sortDurationS =
                                                    new VideoComparators.SortVideoByDurationS();
            ArrayList<TvShow> copyOfTvShows = new ArrayList<>(tvShows);
            Helpers.filterTvShows(copyOfTvShows, action.getFilters());
            Helpers.setDurationS(tvShows);
            if (action.getSortType().equals(Constants.ASCENDING)) {
                copyOfTvShows.sort(sortTitle);
                copyOfTvShows.sort(sortDurationS);
            } else {
                copyOfTvShows.sort(sortTitle.reversed());
                copyOfTvShows.sort(sortDurationS.reversed());
            }
            if (copyOfTvShows.size() != 0) {
                output.append(copyOfTvShows.get(0).getTitle());
                for (int i = 1; i < copyOfTvShows.size() && i < action.getNumber(); ++i) {
                    output.append(", ");
                    output.append(copyOfTvShows.get(i).getTitle());
                }
            }
            return output.toString();
        }
        return  output.toString();
    }

    /**
     * Method that returns the first "n" titles of videos with the lowest/
     * highest number of views
     */
    public String mostViewedVideo(final ActionInputData action) {
        VideoComparators.SortVideoByTitle sortTitle = new VideoComparators.SortVideoByTitle();
        VideoComparators.SortVideoByViews sortViews = new VideoComparators.SortVideoByViews();
        StringBuilder output = new StringBuilder();
        if (action.getObjectType().equals(Constants.MOVIES)) {
            ArrayList<Movie> copyOfMovies = new ArrayList<>(movies);
            Helpers.filterMovies(copyOfMovies, action.getFilters());
            Helpers.viewSetM(copyOfMovies, users);
            copyOfMovies.removeIf((movie) -> movie.getViews() == 0);
            if (action.getSortType().equals(Constants.ASCENDING)) {
                copyOfMovies.sort(sortTitle);
                copyOfMovies.sort(sortViews);
            } else {
                copyOfMovies.sort(sortTitle.reversed());
                copyOfMovies.sort(sortViews.reversed());
            }
            if (copyOfMovies.size() != 0) {
                output.append(copyOfMovies.get(0).getTitle());
                for (int i = 1; i < copyOfMovies.size() && i < action.getNumber(); ++i) {
                    output.append(", ");
                    output.append(copyOfMovies.get(i).getTitle());
                }
            }
            return output.toString();
        } else if (action.getObjectType().equals(Constants.SHOWS)) {
            ArrayList<TvShow> copyOfTvShows = new ArrayList<>(tvShows);
            Helpers.filterTvShows(copyOfTvShows, action.getFilters());
            Helpers.viewSetS(copyOfTvShows, users);
            copyOfTvShows.removeIf((tvShow) -> tvShow.getViews() == 0);
            if (action.getSortType().equals(Constants.ASCENDING)) {
                copyOfTvShows.sort(sortTitle);
                copyOfTvShows.sort(sortViews);
            } else {
                copyOfTvShows.sort(sortTitle.reversed());
                copyOfTvShows.sort(sortViews.reversed());
            }
            if (copyOfTvShows.size() != 0) {
                output.append(copyOfTvShows.get(0).getTitle());
                for (int i = 1; i < copyOfTvShows.size() && i < action.getNumber(); ++i) {
                    output.append(", ");
                    output.append(copyOfTvShows.get(i).getTitle());
                }
            }
            return output.toString();
        }
        return  output.toString();
    }

    // USERS QUERY

    /**
     * Method that returns the first "n" names of users with the lowest/
     * highest number ratings given
     */
    public String numberOfRatingsUser(final ActionInputData action) {
        UsersComparators.SortUsersByRating sortName = new UsersComparators.SortUsersByRating();
        UsersComparators.SortUsersByRating sortRating = new UsersComparators.SortUsersByRating();
        ArrayList<User> copyOfUsers = new ArrayList<>(users);
        StringBuilder output = new StringBuilder();
        copyOfUsers.removeIf((user) -> user.getRatingNumber() == 0);
        copyOfUsers.sort(sortName);
        copyOfUsers.sort(sortRating);
        if (action.getSortType().equals(Constants.ASCENDING)) {
            copyOfUsers.sort(sortName);
            copyOfUsers.sort(sortRating);
        } else {
            copyOfUsers.sort(sortName.reversed());
            copyOfUsers.sort(sortRating.reversed());
        }
        if (copyOfUsers.size() != 0) {
            output.append(copyOfUsers.get(0).getUsername());
            for (int i = 1; i < copyOfUsers.size() && i < action.getNumber(); ++i) {
                output.append(", ");
                output.append(copyOfUsers.get(i).getUsername());
            }
        }
        return output.toString();
    }
}
