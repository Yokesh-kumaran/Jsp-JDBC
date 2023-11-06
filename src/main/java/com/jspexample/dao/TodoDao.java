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

    private  String INSERT_QUERY ="INSERT INTO todo (userId,item) VALUES (?,?);" ;
    private  String SELECT_ALL_QUERY = "SELECT id,item FROM todo WHERE userId=? AND deleteflag=false;";
    private  String DELETE_QUERY = "UPDATE todo SET deleteflag=true WHERE id=?;";
    private Connection connection;
    public TodoDao(){
        connection= DatabaseConnection.getConnection();
    }

    public void addTodo(String item, int userId){
        try {
            PreparedStatement statement=connection.prepareStatement(INSERT_QUERY);
            statement.setString(2,item);
            statement.setInt(1,userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Todo> getAllValues(int userId){
        List<Todo> todos = new ArrayList<>();
        try {
            PreparedStatement statement=connection.prepareStatement(SELECT_ALL_QUERY);
            statement.setInt(1,userId);
            ResultSet rs=statement.executeQuery();
            while (rs.next()){
                Todo todo = new Todo();
                todo.setId(rs.getInt("id"));
                todo.setItem(rs.getString("item"));
                todos.add(todo);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return todos;
    }

    public void deleteTodo(String id){
        try {
            PreparedStatement statement=connection.prepareStatement(DELETE_QUERY);
            statement.setString(1,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
