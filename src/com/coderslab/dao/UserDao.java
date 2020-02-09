package com.coderslab.dao;

import com.coderslab.domain.User;

import java.sql.*;
import java.util.Arrays;

import static com.coderslab.dao.MyPass.DB_PASS;

public class UserDao {
    private final static String DB_URL = "jdbc:mysql://localhost:3306/";
    private final static String DB_USER = "lareth51";
    private final static String DB_NAME = "school";

    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users where id = ?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username = ?, email = ?, password = ? where id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM users";

    public static Connection connect(String dbName) throws SQLException {
        return DriverManager
                .getConnection(DB_URL + dbName + "?useSSL=false&characterEncoding=utf8",
                        DB_USER, DB_PASS);
    }

    public static User create(User user) {
        try (Connection conn = connect(DB_NAME)) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User read(int id) {
        try (Connection conn = connect(DB_NAME)) {
            //SELECT * FROM users where id = ?
            PreparedStatement preparedStatement =
                    conn.prepareStatement(READ_USER_QUERY);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setHashPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User update(User user) {
        try (Connection conn = connect(DB_NAME)) {
            //UPDATE users SET username = ?, email = ?, password = ? where id = ?
            PreparedStatement preparedStatement =
                    conn.prepareStatement(UPDATE_USER_QUERY);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void delete(int id) {
        //DELETE FROM users WHERE id = ?
        try(Connection conn = connect(DB_NAME)) {
            PreparedStatement preparedStatement =
                    conn.prepareStatement(DELETE_USER_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static User[] findAllUsers() {
        try(Connection conn = connect(DB_NAME)) {
            //SELECT * FROM users
            User[] users = new User[0];
            PreparedStatement preparedStatement = conn.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setHashPassword(resultSet.getString("password"));
                users = addToUsers(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static User[] addToUsers(User user, User[] users) {
        User[] newUsers = Arrays.copyOf(users, users.length + 1);
        newUsers[users.length] = user;
        return newUsers;
    }

}