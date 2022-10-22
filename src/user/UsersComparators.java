package user;

import java.util.Comparator;

public final class UsersComparators {
    /**
     * Class that implements Comparator, which helps to sort Users by
     * how many ratings they gave (ascending) and, if equal, in
     * alphabetical order
     */
    public static class SortUsersByRating implements Comparator<User> {
        /**
         * Override compare method
         */
        @Override
        public int compare(final User firstUser, final User secondUser) {
            if (firstUser.getRatingNumber() > secondUser.getRatingNumber()) {
                return 1;
            } else if (firstUser.getRatingNumber() < secondUser.getRatingNumber()) {
                return -1;
            } else {
                return firstUser.getUsername().compareTo(secondUser.getUsername());
            }
        }
    }
}
