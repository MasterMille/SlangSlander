package com.shitflix.models.dao;

import com.shitflix.App;
import com.shitflix.models.dao.converters.WatchedMovieConverter;
import com.shitflix.models.dto.Movie;
import com.shitflix.models.dto.User;
import com.shitflix.models.dto.WatchedMovie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class WatchedMovieDAO {
    private DAO dao;
    private WatchedMovieConverter watchedMovieConverter = new WatchedMovieConverter();
    public WatchedMovieDAO(DAO dao) {
        this.dao = dao;
    }

    private List<WatchedMovie> query(String sql, Object... parameters) { // Object... instead of method for each size
        return dao.query(sql, watchedMovieConverter, parameters);
    }

    private Optional<WatchedMovie> queryOne(String sql, Object... parameters) {
        return dao.queryOne(sql, watchedMovieConverter, parameters);
    }

    public List<WatchedMovie> getWatchedMovies(User user) {
        return query("SELECT movie.*, watched.user_id, watched.id watched_id FROM movie LEFT JOIN(SELECT * FROM watched_movies WHERE user_id = ?) watched ON movie.id = watched.movie_id", user.getId());
    }

    public List<WatchedMovie> filterAllMovies(String title, String director, Integer runtime, Integer releaseYear, Boolean watched) {
        String sql = "SELECT movie.*, watched.user_id, watched.id watched_id FROM movie LEFT JOIN(SELECT * FROM watched_movies WHERE user_id = ?) watched ON movie.id = watched.movie_id";
        StringJoiner stringJoiner = new StringJoiner(" AND ");
        List<Object> parameters = new ArrayList<>();
        parameters.add(App.currentUser == null ? 0 : App.currentUser.getId());
        if (!title.isEmpty()) {
            stringJoiner.add("title LIKE ?");
            parameters.add("%" + title + "%");
        }
        if (!director.isEmpty()) {
            stringJoiner.add("director LIKE ?");
            parameters.add("%" + director + "%");
        }
        if (runtime != null) {
            stringJoiner.add("runtime_minutes -10 <= ? AND ? <= runtime_minutes + 10");
            parameters.add(runtime);
            parameters.add(runtime);
        }
        if (releaseYear != null) {
            stringJoiner.add("release_year = ?");
            parameters.add(releaseYear);
        }
        if (watched == Boolean.TRUE) {
            stringJoiner.add("user_id IS NOT NULL");
        }
        if (watched == Boolean.FALSE) {
            stringJoiner.add("user_id IS NULL");
        }
        String where = stringJoiner.toString();
        if (!where.isEmpty()) {
            sql += " WHERE " + where;
        }
        sql += " ORDER BY release_year ";
        return query(sql, parameters.toArray());
    }

    public void save(WatchedMovie movie) {
        if (movie.isWatched() && movie.getWatchedId() == null) {
            // Movie is watched but not present in database
            dao.execute("INSERT INTO watched_movies (user_id, movie_id) VALUES (?, ?)", App.currentUser.getId(), movie.getMovieId());
        } else if (!movie.isWatched() && movie.getWatchedId() != null) {
            // Movie is "unwatched" but not deleted from database
            dao.execute("DELETE FROM watched_movies WHERE id = ?", movie.getWatchedId());
        } else {
            System.out.println("You should never reach this... What?");
        }
    }
}
