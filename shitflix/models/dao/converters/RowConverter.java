package com.shitflix.models.dao.converters;


import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowConverter<T> {
    T convert(ResultSet rs) throws SQLException;
}
