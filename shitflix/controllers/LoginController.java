package com.shitflix.controllers;

import com.shitflix.App;
import com.shitflix.models.dao.UserDAO;
import com.shitflix.models.dto.User;
import com.shitflix.views.LoginPanel;

import java.util.Optional;

public class LoginController {
    private final UserDAO userDao;
    private LoginPanel loginPanel;

    public LoginController(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void setPanel(LoginPanel loginPanel) {
        this.loginPanel = loginPanel;
    }

    public boolean login(String email, String password) {
        Optional<User> optionalUser = userDao.getByEmailAndPassword(email, password);
        if (optionalUser.isPresent()) {
            System.out.println("Login Successful");
            App.currentUser = optionalUser.get();
            return true;
        } else {
            // Todo: Display Error Message
            //view.displayError("Invalid email or password");
            System.out.println("Invalid email or password");
            return false;
        }
    }
}
