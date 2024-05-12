package com.shitflix.models.dao;

import com.shitflix.models.dao.converters.UserConverter;
import com.shitflix.models.dto.User;

import java.util.List;
import java.util.Optional;

public class UserDAO {
    private DAO dao;
    private UserConverter userConverter = new UserConverter();

    public UserDAO(DAO dao) {
        this.dao = dao;
    }

    private List<User> query(String sql, Object... parameters) { // Object... instead of method for each size
        return dao.query(sql, userConverter, parameters);
    }

    private Optional<User> queryOne(String sql, Object... parameters) {
        return dao.queryOne(sql, userConverter, parameters);
    }

    public List<User> getAll() {
        return query("SELECT * FROM user");
    }

    public Optional<User> getById(Integer id) {
        return queryOne("SELECT * FROM user WHERE id = ?", id);
    }

    public Optional<User> getByEmailAndPassword(String email, String password) {
        return queryOne("SELECT * FROM user WHERE email = ? AND pass COLLATE utf8mb4_bin = ?", email, password); // Collate utf8... makes caps sensitive for password to login
    }

    public User register(String email, String password, String username) {
        dao.execute("INSERT INTO user (email, pass, username) VALUES (?, ?, ?)", email, password, username);
        return this.getByEmailAndPassword(email, password).get();
    }
}
