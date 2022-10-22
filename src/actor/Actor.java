package actor;

import java.util.ArrayList;
import java.util.Map;

public final class Actor {
    /**
     * Name of the actor
     */
    private final String name;
    /**
     * The career description of the actor
     */
    private final String careerDescription;
    /**
     * List of videos the actor plays in
     */
    private ArrayList<String> filmography;
    /**
     * Awards of the actor which stocks the name of the award
     * and the number of times the actor won it
     */
    private final Map<ActorsAwards, Integer> awards;
    /**
     * Actor's average rating
     */
    private double averageRating;
    /**
     * Number of actor's awards
     */
    private int awardNumber;

    /**
     * Class constructor
     */
    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography, final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.averageRating = 0;
        this.awardNumber = 0;
    }

    /**
     * Getters and Setters for the fields of the class
     */
    public String getName() {
        return name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public int getAwardNumber() {
        return awardNumber;
    }

    public void setAwardNumber(final int awardNumber) {
        this.awardNumber = awardNumber;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(final double averageRating) {
        this.averageRating = averageRating;
    }

}
