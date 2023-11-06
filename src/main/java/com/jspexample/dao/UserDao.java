package com.jspexample.dao;

import com.jspexample.db.DatabaseConnection;
import com.jspexample.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    Connection connection;

    public UserDao() {
        connection = DatabaseConnection.getConnection();
    }

    private String selectSql = "SELECT id, username, password FROM auth WHERE username=? and password=?";

    public User loginInUser(String username, String password) {
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(selectSql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }
}
