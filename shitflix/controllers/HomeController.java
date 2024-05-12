package com.shitflix.controllers;

import com.shitflix.models.dao.MovieDAO;
import com.shitflix.models.dao.UserDAO;
import com.shitflix.models.dao.WatchedMovieDAO;
import com.shitflix.models.dto.Movie;
import com.shitflix.models.dto.WatchedMovie;
import com.shitflix.views.HomePanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HomeController {
    private final UserDAO userDao;
    private final MovieDAO movieDao;
    private final WatchedMovieDAO watchedMovieDao;
    private HomePanel homePanel;

    public HomeController(UserDAO userDao, MovieDAO movieDao, WatchedMovieDAO watchedMovieDAO) {
        this.userDao = userDao;
        this.movieDao = movieDao;
        this.watchedMovieDao = watchedMovieDAO;
    }

    public void setPanel(HomePanel homePanel) {
        this.homePanel = homePanel;
    }

    public static Integer tryParseInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            return null;
        }
    }

    public List<WatchedMovie> search(String title, String director, String runtimeText, String releaseYearText, Boolean watched) {
        return watchedMovieDao.filterAllMovies(title, director, tryParseInt(runtimeText), tryParseInt(releaseYearText), watched);
    }

    public boolean insertMovie(String title, String director, String runtime, String releaseYear) {
        if (title.isEmpty() || director.isEmpty() || runtime.isEmpty() || releaseYear.isEmpty()) {
            return false;
        } else {
            List<String> titles = splitCsv(title);
            List<String> directors = splitCsv(director);
            List<Integer> runtimes = toIntegers(splitCsv(runtime));
            List<Integer> releaseYears = toIntegers(splitCsv(releaseYear));
            int targetSize = titles.size();
            if (targetSize == directors.size() && targetSize == runtimes.size() && targetSize == releaseYears.size()) {
                for (int i = 0; i < targetSize; ++i) {
                    if (validateRuntimes(runtimes)) {
                        movieDao.insert(titles.get(i), directors.get(i), runtimes.get(i), releaseYears.get(i));
                    } else {
                        System.out.println("Runtime too short!");
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private static boolean validateRuntimes(List<Integer> runtimes) {
        for (Integer runtime : runtimes) {
            if (runtime < 30 || runtime > 300) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> toIntegers(List<String> inputs) {
        List<Integer> output = new ArrayList<>();
        for (String s : inputs) {
            Integer i = tryParseInt(s);
            if (i != null) {
                output.add(i);
            }
        }
        return output;
    }

    private static List<String> splitCsv(String input) {
        List<String> output = new ArrayList<>();
        for (String s : input.split(",")) {
            String temp = s.trim();
            if (!temp.isEmpty()) {
                output.add(temp);
            }
        }
        return output;
    }

    public void save(WatchedMovie movie) {
        watchedMovieDao.save(movie);
        homePanel.updateTable();
    }
}
