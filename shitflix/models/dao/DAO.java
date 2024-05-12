package com.shitflix.models.dao;

import com.shitflix.models.dao.converters.RowConverter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAO {
    private final String url;
    private final String user;
    private final String pass;

    public DAO(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public int execute(String sql, Object... parameters) {
        System.out.println(sql);
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (int i = 0; i < parameters.length; ++i) {
                stmt.setObject(i + 1, parameters[i]);
            }
            int rowsAffected = stmt.executeUpdate();
            conn.close();
            System.out.println("Rows Affected: " + rowsAffected);
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public <T> List<T> query(String sql, RowConverter<T> rowConverter, Object... parameters) {
        System.out.println(sql);
        List<T> results = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (int i = 0; i < parameters.length; ++i) {
                stmt.setObject(i + 1, parameters[i]);
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                T t = rowConverter.convert(rs);
                results.add(t);
            }

            conn.close();
            System.out.println("Rows queried: " + results.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public <T> Optional<T> queryOne(String sql, RowConverter<T> rowConverter, Object... parameters) {
        List<T> results = query(sql, rowConverter, parameters);
        if (results.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(results.get(0));
    }

}
