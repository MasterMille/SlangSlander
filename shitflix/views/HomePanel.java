package com.shitflix.views;

import com.shitflix.App;
import com.shitflix.controllers.HomeController;
import com.shitflix.controllers.LoginController;
import com.shitflix.models.MovieTableModel;
import com.shitflix.models.dto.Movie;
import com.shitflix.models.dto.WatchedMovie;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {

    private final HomeController controller;
    private JTextField addTitleField = new JTextField();
    private JTextField addDirectorField = new JTextField();
    private JTextField addRuntimeField = new JTextField();
    private JTextField addReleaseYearField = new JTextField();

    private JTextField searchTitleField = new JTextField();
    private JTextField searchDirectorField = new JTextField();
    private JTextField searchRuntimeField = new JTextField();
    private JTextField searchReleaseYearField = new JTextField();
    private JComboBox searchWatchedField;
    private JScrollPane scrollableTable;

    private JLabel errorAddMovieTextLabel = new JLabel();
    private JLabel errorSearchTextLabel = new JLabel();

    public HomePanel(HomeController controller) {
        this.controller = controller;
        init();
    }


    public void resetAddMovieForm() {
        addTitleField.setText("");
        addDirectorField.setText("");
        addRuntimeField.setText("");
        addReleaseYearField.setText("");
        errorAddMovieTextLabel.setText("");
    }

    public void resetSearchForm() {
        searchTitleField.setText("");
        searchDirectorField.setText("");
        searchRuntimeField.setText("");
        searchReleaseYearField.setText("");
        searchWatchedField.setSelectedIndex(0);
    }
    public void resetForm() {
        resetAddMovieForm();
        resetSearchForm();
        updateTable();
    }

    public void init() {

        this.setLayout(new BorderLayout());
        JPanel leftSidebar = new JPanel(new BorderLayout());
        leftSidebar.setPreferredSize(new Dimension(250, 0));
        JPanel dataPanel = new JPanel(new BorderLayout());

        // Font
        Font font = new Font("Verdana", Font.ITALIC, 12);
        errorAddMovieTextLabel.setForeground(Color.RED);
        errorSearchTextLabel.setForeground(Color.RED);

        // Left sidebar:
        JPanel newMovieForm = new JPanel(new GridLayout(11, 1));
        JButton addNewMovieButton = new JButton("Add New Movie");
        JButton logoutButton = new JButton("Logout");

        newMovieForm.add(new JLabel("-- Create new movie -- "));
        newMovieForm.add(new JLabel("Title:"));
        newMovieForm.add(addTitleField);
        newMovieForm.add(new JLabel("Director:"));
        newMovieForm.add(addDirectorField);
        newMovieForm.add(new JLabel("Runtime:"));
        newMovieForm.add(addRuntimeField);
        newMovieForm.add(new JLabel("Year:"));
        newMovieForm.add(addReleaseYearField);
        newMovieForm.add(errorAddMovieTextLabel);
        newMovieForm.add(addNewMovieButton);

        leftSidebar.add(newMovieForm, BorderLayout.NORTH);
        leftSidebar.add(logoutButton, BorderLayout.SOUTH);

        // Main form:
        JPanel filterForm = new JPanel(new GridLayout(2, 6));

        filterForm.add(new JLabel("Title"));
        filterForm.add(new JLabel("Director"));
        filterForm.add(new JLabel("Runtime"));
        filterForm.add(new JLabel("Year"));
        filterForm.add(new JLabel("Watched"));
        filterForm.add(errorSearchTextLabel);
        this.searchWatchedField = new JComboBox<>(new String[] {"any", "yes", "no"});
        JButton searchButton = new JButton("Search");

        filterForm.add(searchTitleField);
        filterForm.add(searchDirectorField);
        filterForm.add(searchRuntimeField);
        filterForm.add(searchReleaseYearField);
        filterForm.add(searchWatchedField);
        filterForm.add(searchButton);

        scrollableTable = new JScrollPane(getNewTable());

        // Add Movie Button
        addNewMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = controller.insertMovie(
                        addTitleField.getText(),
                        addDirectorField.getText(),
                        addRuntimeField.getText(),
                        addReleaseYearField.getText()
                );
                if (isValid) {
                    resetAddMovieForm();
                    updateTable();
                } else {
                    errorAddMovieTextLabel.setText("Invalid entry");
                }
            }
        });

        // Logout Button
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.show("login");
            }
        });

        // Search Button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });

        dataPanel.add(filterForm, BorderLayout.NORTH);
        dataPanel.add(scrollableTable, BorderLayout.CENTER);

        add(leftSidebar, BorderLayout.WEST);
        add(dataPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void updateTable() {
        scrollableTable.setViewportView(getNewTable());
    }

    private JTable getNewTable() {
        //String[] columnNames = {"Title", "Director", "Runtime", "Year", "Watched"};
        MovieTableModel model = new MovieTableModel(controller);
        model.setMovies(getMovies());
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }

    private List<WatchedMovie> getMovies() {
        String watchedInput = (String) searchWatchedField.getSelectedItem();
        Boolean watched = null;
        if (watchedInput.equals("yes")) {
            watched = true;
        } else if (watchedInput.equals("no")) {
            watched = false;
        } else {
            watched = null;
        }
        return controller.search(
                searchTitleField.getText(),
                searchDirectorField.getText(),
                searchRuntimeField.getText(),
                searchReleaseYearField.getText(),
                watched
        );
    }
}
