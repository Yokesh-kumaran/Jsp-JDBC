package com.jspexample.dao;

import com.jspexample.db.DatabaseConnection;
import com.jspexample.model.Todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoDao {

    private final String INSERT_TODO = "INSERT INTO todo (todo, userId) VALUES (?, ?);";
    private final String SELECT_ALL = "SELECT id, todo, userId FROM todo WHERE userId=?";
    private final String DELETE_TODO = "DELETE todo WHERE id=?;";

    private final Connection connection;

    public TodoDao() {
        connection = DatabaseConnection.getConnection();
    }

    public void addTodo(String todo, int userId) {
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_TODO);
            ps.setString(1, todo);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Todo> selectAllTodos(int userId) {
        List<Todo> todos = new ArrayList<Todo>();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Todo todo = new Todo();
                todo.setId(Integer.parseInt(rs.getString("id")));
                todo.setTodo(rs.getString("todo"));
                todo.setUserId(rs.getInt("userId"));
                todos.add(todo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    public void deleteTodo(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_TODO);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
