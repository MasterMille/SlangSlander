package com.shitflix.models;

import com.shitflix.controllers.HomeController;
import com.shitflix.models.dto.Movie;
import com.shitflix.models.dto.WatchedMovie;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class MovieTableModel extends AbstractTableModel {

    private HomeController controller;
    private List<WatchedMovie> watchedMovies;
    private List<String> columns;

    public MovieTableModel(HomeController controller) {
        this.controller = controller;
        this.columns = new ArrayList<>();
        columns.add("Title");
        columns.add("Director");
        columns.add("Runtime");
        columns.add("Year");
        columns.add("Watched");
    }

    public void setMovies(List<WatchedMovie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public List<WatchedMovie> getWatchedMovies() {
        return watchedMovies;
    }


    @Override
    public int getRowCount() {
        return watchedMovies.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(int column) {
        return columns.get(column);
    }

    @Override
    public Class<?> getColumnClass(int column) {
        // Checkbox in column 4 (is watched)
        return column == 4 ? Boolean.class : Object.class;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        WatchedMovie movie = this.watchedMovies.get(row);
        if (column == 4) { // If column == 4 edit watchColumn
            movie.toggleWatched();
            controller.save(movie);
        }
        fireTableCellUpdated(row, column);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        WatchedMovie watchedMovie = watchedMovies.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return watchedMovie.getTitle();
            case 1:
                return watchedMovie.getDirector();
            case 2:
                return watchedMovie.getRuntime();
            case 3:
                return watchedMovie.getReleaseYear();
            case 4:
                return watchedMovie.isWatched();
            default:
                return null;
        }
    }
}
