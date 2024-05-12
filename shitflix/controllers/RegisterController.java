package com.shitflix.controllers;

import com.shitflix.App;
import com.shitflix.models.dao.UserDAO;
import com.shitflix.views.RegisterPanel;

public class RegisterController {
    private final UserDAO userDao;
    private RegisterPanel registerPanel;

    public RegisterController(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void setPanel(RegisterPanel registerPanel) {
        this.registerPanel = registerPanel;
    }

    public boolean register(String email, String password, String username) {
        if (!email.isEmpty() && !password.isEmpty() && !username.isEmpty()) {
            App.currentUser = userDao.register(email, password, username);
            return true;
        } else {
            return false;
        }
    }
}
