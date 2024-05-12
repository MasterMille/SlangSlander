package com.shitflix.models.dao.converters;

import com.shitflix.models.dto.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserConverter implements RowConverter<User> {
    @Override
    public User convert(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("pass"));
        user.setUsername(rs.getString("username"));
        return user;
    }
}
