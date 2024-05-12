package com.shitflix;

import com.shitflix.controllers.HomeController;
import com.shitflix.controllers.RegisterController;
import com.shitflix.models.dao.MovieDAO;
import com.shitflix.models.dao.UserDAO;
import com.shitflix.controllers.LoginController;
import com.shitflix.models.dao.DAO;
import com.shitflix.models.dao.WatchedMovieDAO;
import com.shitflix.models.dto.User;
import com.shitflix.models.dto.WatchedMovie;
import com.shitflix.views.HomePanel;
import com.shitflix.views.LoginPanel;
import com.shitflix.views.RegisterPanel;

import javax.swing.*;
import java.awt.*;

public class App {
    public static final JFrame FRAME = new JFrame("Main");
    public static final CardLayout CARD_LAYOUT = new CardLayout();
    public static final JPanel CARD_LAYOUT_PANEL = new JPanel(CARD_LAYOUT);
    public static User currentUser;

    private static HomePanel homePanel;

    public static void show(String panel) {
        if (panel.equals("home")) {
            homePanel.resetForm();
        }
        CARD_LAYOUT.show(CARD_LAYOUT_PANEL, panel);
    }

    public static void main(String[] args) {
        DAO dao = new DAO("jdbc:mysql://localhost:3306/shitflix", "root", "123456"); // Stores connection info
        UserDAO userDao = new UserDAO(dao);
        MovieDAO movieDao = new MovieDAO(dao);
        WatchedMovieDAO watchedMovieDao = new WatchedMovieDAO(dao);

        FRAME.setSize(850,550);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LoginController loginController = new LoginController(userDao);
        LoginPanel loginPanel = new LoginPanel(loginController);
        loginController.setPanel(loginPanel);
        CARD_LAYOUT_PANEL.add("login", loginPanel);

        RegisterController registerController = new RegisterController(userDao);
        RegisterPanel registerPanel = new RegisterPanel(registerController);
        registerController.setPanel(registerPanel);
        CARD_LAYOUT_PANEL.add("register", registerPanel);

        HomeController homeController = new HomeController(userDao, movieDao, watchedMovieDao);
        homePanel = new HomePanel(homeController);
        homeController.setPanel(homePanel);
        CARD_LAYOUT_PANEL.add("home", homePanel);

        FRAME.setContentPane(CARD_LAYOUT_PANEL);
        //FRAME.setContentPane(registerPanel);

        show("login");

        FRAME.setVisible(true);
    }

    // E-level
    // Done Logout Button
    // Done Refresh homepage after login
    // Done Search movies by year.
    // Done Add a movie, needs title, director, runtime, release year.
    // Todo: All errors caught.

    // C-level
    // Done Search director, get title, year.
    // Done Add list of new movies, runtime between 30-300 min.
    // Done Close resources that is no longer needed after "communicating" with the database.

    // A-level
    // Done Register new users.
    // Done Search movies and mark movies which user have watched.
    // Todo: Ask for a list of watched movies, sorted by year with oldest first.
}
