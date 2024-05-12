package com.shitflix.models.dao.converters;

import com.shitflix.models.dto.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieConverter implements RowConverter<Movie> {

    @Override
    public Movie convert(ResultSet rs) throws SQLException {
        Movie movie = new Movie();
        movie.setId(rs.getInt("id"));
        movie.setTitle(rs.getString("title"));
        movie.setReleaseYear(rs.getInt("release_year"));
        movie.setRuntime(rs.getInt("runtime_minutes"));
        movie.setDirector(rs.getString("director"));
        return movie;
    }
}
