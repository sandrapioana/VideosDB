package entertainment;

import java.util.Comparator;
import java.util.Map;

public final class VideoComparators {

    /**
     * Class that implements Comparator, which helps to sort Video Titles in
     * alphabetical order
     */
    public static class SortVideoByTitle implements Comparator<Video> {
        /**
         * Override compare method
         */
        @Override
        public int compare(final Video firstVideo, final Video secondVideo) {
            return firstVideo.getTitle().compareTo(secondVideo.getTitle());
        }
    }
    /**
     * Class that implements Comparator, which helps to sort Videos by
     * how many users have the Video in their favorite list (ascending)
     */
    public static class SortVideoByFavorites implements Comparator<Video> {
        /**
         * Override compare method
         */
        @Override
        public int compare(final Video firstVideo, final Video secondVideo) {
            return firstVideo.getFavorites() - secondVideo.getFavorites();
        }
    }
    /**
     * Class that implements Comparator, which helps to sort Videos by
     * their rating (ascending)
     */
    public static class SortVideoByRating implements Comparator<Video> {
        /**
         * Override compare method
         */
        @Override
        public int compare(final Video firstVideo, final Video secondVideo) {
            if (firstVideo.getVideoRating() > secondVideo.getVideoRating()) {
                return 1;
            } else if (firstVideo.getVideoRating() < secondVideo.getVideoRating()) {
                return -1;
            } else {
                return 0;
            }
        }
    }
    /**
     * Class that implements Comparator, which helps to sort Videos by
     * how many users have seen the Video (ascending)
     */
    public static class SortVideoByViews implements Comparator<Video> {
        /**
         * Override compare method
         */
        @Override
        public int compare(final Video firstVideo, final Video secondVideo) {
            return firstVideo.getViews() - secondVideo.getViews();
        }
    }
    /**
     * Class that implements Comparator, which helps to sort TvShows by
     * their Duration (ascending)
     */
    public static class SortVideoByDurationS implements Comparator<TvShow> {
        /**
         * Override compare method
         */
        @Override
        public int compare(final TvShow firstTvShow, final TvShow secondTvShow) {
            return firstTvShow.getDuration() - secondTvShow.getDuration();
        }
    }
    /**
     * Class that implements Comparator, which helps to sort Movies by
     * their Duration (ascending)
     */
    public static class SortVideoByDurationM implements Comparator<Movie> {
        /**
         * Override compare method
         */
        @Override
        public int compare(final Movie firstMovie, final Movie secondMovie) {
            return firstMovie.getDuration() - secondMovie.getDuration();
        }
    }
    /**
     * Class that implements Comparator, which helps to sort Genres
     * in alphabetical order
     */
    public static class SortGenreByTitle implements Comparator<Map.Entry<String, Integer>> {
        /**
         * Override compare method
         */
        @Override
        public int compare(final Map.Entry<String, Integer> firstGenre,
                            final Map.Entry<String, Integer> secondGenre) {
            return firstGenre.getKey().compareTo(secondGenre.getKey());
        }
    }
    /**
     * Class that implements Comparator, which helps to sort Genres by
     * how many views the genre has (ascending)
     */
    public static class SortGenreByViews implements Comparator<Map.Entry<String, Integer>> {
        /**
         * Override compare method
         */
        @Override
        public int compare(final Map.Entry<String, Integer> firstGenre,
                            final Map.Entry<String, Integer> secondGenre) {
            return firstGenre.getValue() - secondGenre.getValue();
        }
    }
}
