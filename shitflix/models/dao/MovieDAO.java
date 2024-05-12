package com.shitflix.models.dao;

import com.shitflix.models.dao.converters.MovieConverter;
import com.shitflix.models.dao.converters.WatchedMovieConverter;
import com.shitflix.models.dto.Movie;
import com.shitflix.models.dto.User;
import com.shitflix.models.dto.WatchedMovie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class MovieDAO {
    private DAO dao;
    private MovieConverter movieConverter = new MovieConverter();

    public MovieDAO(DAO dao) {
        this.dao = dao;
    }

    private List<Movie> query(String sql, Object... parameters) { // Object... instead of method for each size
        return dao.query(sql, movieConverter, parameters);
    } // If you get multiple

    private Optional<Movie> queryOne(String sql, Object... parameters) {
        return dao.queryOne(sql, movieConverter, parameters);
    } // If you're expecting one movie or none

    public List<Movie> getAll() {
        return query("SELECT * FROM movie");
    }

    public Optional<Movie> getById(Integer id) {
        return queryOne("SELECT * FROM movie WHERE id = ?", id);
    }

    public void insert(String title, String director, Integer runtime, Integer releaseYear) {
        dao.execute("INSERT INTO movie (title, director, runtime_minutes, release_year) VALUES (?, ?, ?, ?)", title, director, runtime, releaseYear);
    }
}
