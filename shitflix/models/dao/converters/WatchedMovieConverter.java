package com.shitflix.models.dao.converters;

import com.mysql.cj.result.Row;
import com.shitflix.models.dto.Movie;
import com.shitflix.models.dto.WatchedMovie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WatchedMovieConverter implements RowConverter<WatchedMovie> {
    @Override
    public WatchedMovie convert(ResultSet rs) throws SQLException {
        WatchedMovie watchedMovie = new WatchedMovie();
        watchedMovie.setMovieId(rs.getInt("id"));                   // MovieID
        watchedMovie.setTitle(rs.getString("title"));               // Title of movie
        watchedMovie.setReleaseYear(rs.getInt("release_year"));     // Release year
        watchedMovie.setRuntime(rs.getInt("runtime_minutes"));      // Runtime
        watchedMovie.setDirector(rs.getString("director"));         // Director
        watchedMovie.setWatched(rs.getObject("user_id") != null);   // UserID, Either int (UserID) or null (Unwatched)
        watchedMovie.setWatchedId(rs.getInt("watched_id"));         // Watched Primary Key
        if (!watchedMovie.isWatched()) {
            // Converts null back to null (getInt doesn't return null)
            watchedMovie.setWatchedId(null);
        }
        return watchedMovie;
    }
}
