package com.shitflix.models.dto;

public class WatchedMovie {
    private Integer movieId;
    private Integer watchedId;
    private String title;
    private int releaseYear;
    private int runtime;
    private String director;
    private boolean watched;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getWatchedId() {
        return watchedId;
    }

    public void setWatchedId(Integer watchedId) {
        this.watchedId = watchedId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public void toggleWatched() {
        this.watched = !this.watched;
    }
}
