package main;

import action.Commands;
import action.Query;
import action.Recommendation;
import actor.Actor;
import checker.Checker;
import checker.Checkstyle;
import common.Constants;
import entertainment.Movie;
import entertainment.TvShow;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation

        /**
         * Initialize classes for all 4 types of entities
         */
        ArrayList<User> allUsers = new ArrayList<>();
        ArrayList<Actor> allActors = new ArrayList<>();
        ArrayList<Movie> allMovies = new ArrayList<>();
        ArrayList<TvShow> allTvShows = new ArrayList<>();

        /**
         * Get all the users given in input
         */
        for (int i = 0; i < input.getUsers().size(); ++i) {
            User user = new User(input.getUsers().get(i).getUsername(),
                    input.getUsers().get(i).getSubscriptionType(),
                    input.getUsers().get(i).getHistory(),
                    input.getUsers().get(i).getFavoriteMovies());
            allUsers.add(user);
        }

        /**
         * Get all the actors given in input
         */
        for (int i = 0; i < input.getActors().size(); ++i) {
            Actor actor = new Actor(input.getActors().get(i).getName(),
                    input.getActors().get(i).getCareerDescription(),
                    input.getActors().get(i).getFilmography(),
                    input.getActors().get(i).getAwards());
            allActors.add(actor);
        }

        /**
         * Get all the movies given in input
         */
        for (int i = 0; i < input.getMovies().size(); ++i) {
            Movie movie = new Movie(input.getMovies().get(i).getTitle(),
                    input.getMovies().get(i).getYear(),
                    input.getMovies().get(i).getGenres(),
                    input.getMovies().get(i).getCast(),
                    input.getMovies().get(i).getDuration());
            allMovies.add(movie);
        }

        /**
         * Get all the tvshows given in input
         */
        for (int i = 0; i < input.getSerials().size(); ++i) {
            TvShow tvShow = new TvShow(input.getSerials().get(i).getTitle(),
                    input.getSerials().get(i).getYear(),
                    input.getSerials().get(i).getGenres(),
                    input.getSerials().get(i).getCast(),
                    input.getSerials().get(i).getSeasons(),
                    input.getSerials().get(i).getNumberSeason());
            allTvShows.add(tvShow);
        }
        /**
         * Initialize classes for all 3 types of actions and
         * add lists of entities where needed for the actions
         */
        Query query = new Query(allUsers, allActors, allMovies, allTvShows);
        Recommendation recommendation = new Recommendation(allUsers, allMovies, allTvShows);

        /**
         * For every action in input, check which type it is
         * Does the action and add the output in the JSON using the
         * fileWriter class
         */
        for (int i = 0; i < input.getCommands().size(); ++i) {
            if (input.getCommands().get(i).getActionType().equals(Constants.COMMAND)) {
                if (input.getCommands().get(i).getType() != null) {
                    String output = doCommand(allUsers, allMovies,
                                            allTvShows, input.getCommands().get(i));
                    JSONObject json = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                            "", output);
                    arrayResult.add(json);
                }
            } else if (input.getCommands().get(i).getActionType().equals(Constants.QUERY)) {
                if (input.getCommands().get(i).getCriteria() != null) {
                    String output = "Query result: [" + doQuery(query,
                                                                input.getCommands().get(i)) + "]";
                    JSONObject json = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                            "", output);
                    arrayResult.add(json);
                }
            } else if (input.getCommands().get(i).getActionType().equals(
                                                                Constants.RECOMMENDATION)) {
                if (input.getCommands().get(i).getType() != null) {
                    String output = doRecommendation(recommendation,
                                                    allUsers, input.getCommands().get(i));
                    JSONObject json = fileWriter.writeFile(input.getCommands().get(i).getActionId(),
                            "", output);
                    arrayResult.add(json);
                }
            }
        }
        fileWriter.closeJSON(arrayResult);
    }

    /**
     * Method that get the "command" action done
     */
    public static String doCommand(final ArrayList<User> allUsers,
                                   final ArrayList<Movie> allMovies,
                                   final ArrayList<TvShow> allTvShows,
                                   final ActionInputData action) {
        String output = null;
        User commandUser = null;
        for (int i = 0; i < allUsers.size(); ++i) {
            if (allUsers.get(i).getUsername().equals(action.getUsername())) {
                commandUser = allUsers.get(i);
            }
        }
        if (action.getType() != null) {
            if (action.getType().equals(Constants.FAVORITE)) {
                output = Commands.addToFavorites(commandUser, action.getTitle());
            } else if (action.getType().equals(Constants.VIEW)) {
                output = Commands.addToView(commandUser, action.getTitle());
            } else if (action.getType().equals(Constants.RATING)) {
                if (action.getSeasonNumber() != 0) {
                    TvShow commandTvShow = null;
                    for (int i = 0; i < allTvShows.size(); ++i) {
                        if (allTvShows.get(i).getTitle().equals(action.getTitle())) {
                            commandTvShow = allTvShows.get(i);
                        }
                    }
                    if (commandTvShow != null) {
                        output = Commands.rateTvShow(commandUser, action.getGrade(),
                                action.getSeasonNumber(), commandTvShow);

                    }
                } else {
                    Movie commandMovie = null;
                    for (int i = 0; i < allMovies.size(); ++i) {
                        if (allMovies.get(i).getTitle().equals(action.getTitle())) {
                            commandMovie = allMovies.get(i);
                        }
                    }
                    if (commandMovie != null) {
                        output = Commands.rateMovie(commandUser, action.getGrade(), commandMovie);
                    }
                }
            }
        }
        return output;
    }

    /**
     * Method that get the "query" action done
     */
    public static String doQuery(final Query query, final ActionInputData action) {
        if (action.getCriteria().equals(Constants.AVERAGE)) {
            return query.averageActors(action);
        } else if (action.getCriteria().equals(Constants.AWARDS)) {
            return query.awardsActors(action);
        } else if (action.getCriteria().equals(Constants.FILTER_DESCRIPTIONS)) {
            return query.descriptionActors(action);
        } else if (action.getCriteria().equals(Constants.RATINGS)) {
            return query.ratingVideo(action);
        } else if (action.getCriteria().equals(Constants.FAVORITE)) {
            return query.favoriteVideo(action);
        } else if (action.getCriteria().equals(Constants.LONGEST)) {
            return query.longestVideo(action);
        } else if (action.getCriteria().equals(Constants.MOST_VIEWED)) {
            return query.mostViewedVideo(action);
        } else if (action.getCriteria().equals(Constants.NUM_RATINGS)) {
            return query.numberOfRatingsUser(action);
        }
        return "";
    }

    /**
     * Method that get the "recommendation" action done
     */
    public static String doRecommendation(final Recommendation recommendation,
                                          final ArrayList<User> allUsers,
                                          final ActionInputData action) {
        String output = null;
        User recommendationUser = null;
        for (int i = 0; i < allUsers.size(); ++i) {
            if (allUsers.get(i).getUsername().equals(action.getUsername())) {
                recommendationUser = allUsers.get(i);
            }
        }
        if (recommendationUser != null) {
            if (action.getType().equals(Constants.STANDARD)) {
                output = recommendation.standard(recommendationUser);
            } else if (action.getType().equals(Constants.BEST_UNSEEN)) {
                output = recommendation.bestUnseen(recommendationUser);
            } else if (action.getType().equals(Constants.POPULAR)) {
                output = recommendation.popular(recommendationUser);
            } else if (action.getType().equals(Constants.FAVORITE)) {
                output = recommendation.favorite(recommendationUser);
            } else if (action.getType().equals(Constants.SEARCH)) {
                output = recommendation.search(recommendationUser, action.getGenre());
            }
        }
        return output;
    }
}
