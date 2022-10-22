package actor;

import java.util.Comparator;

public final class ActorsComparators {
    /**
     * Class that implements Comparator, which helps to sort Actors by
     * their name in alphabetical order
     */
    public static final class SortActorsByName implements Comparator<Actor> {
        /**
         * Override compare method
         */
        @Override
        public int compare(final Actor firstActor, final Actor secondActor) {
            return firstActor.getName().compareTo(secondActor.getName());
        }
    }

    /**
     * Class that implements Comparator, which helps to sort Actors by
     * their number of awards (ascending)
     */
    public static final class SortActorsByNrAwards implements Comparator<Actor> {
        /**
         * Override compare method
         */
        @Override
        public int compare(final Actor firstActor, final Actor secondActor) {
            return firstActor.getAwardNumber() - secondActor.getAwardNumber();
        }
    }

    /**
     * Class that implements Comparator, which helps to sort Actors by
     * their total rating (ascending)
     */
    public static final class SortActorsByRating implements Comparator<Actor> {
        /**
         * Override compare method
         */
        @Override
        public int compare(final Actor firstActor, final Actor secondActor) {
            if (firstActor.getAverageRating() > secondActor.getAverageRating()) {
                return 1;
            } else if (firstActor.getAverageRating() < secondActor.getAverageRating()) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
