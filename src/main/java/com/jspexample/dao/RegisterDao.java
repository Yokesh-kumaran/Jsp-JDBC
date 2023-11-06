package com.jspexample.dao;

import com.jspexample.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterDao {
    private static final String INSERT_NEW_USER = "INSERT INTO auth (username,password) VALUES (?,?);";
    private Connection connection;

    public RegisterDao(){
        connection = DatabaseConnection.getConnection();
    }

    public void addUser(String username, String pass){

        try {
            PreparedStatement statement=connection.prepareStatement(INSERT_NEW_USER);
            statement.setString(1,username);
            statement.setString(2,pass);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
