package com.sachin.dao;

import com.sachin.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/user_db";
    private String jdbcUsername = "root"; // we can name mysqlUsername but to keep it generic, it is named as jdbcUsername, not coupling it to specific db
    private String jdbcPassword = "password";

    // SELECT ALL
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sqlQuery = "SELECT * FROM users";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // INSERT
    public void insertUser(User user) {
        String sqlQuery = "INSERT INTO users (name, email) VALUES (?, ?)";
        try(Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public boolean updateUser(User user) {
        String sqlQuery = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        boolean rowUpdated = false;
        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    // DELETE
    public boolean removeUser(int id) {
        String sqlQuery = "DELETE from users where id = ?";
        boolean rowDeleted = false;
        try(Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, id);
            rowDeleted = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
