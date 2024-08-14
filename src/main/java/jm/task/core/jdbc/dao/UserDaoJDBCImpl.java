package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.util.Util;

import static java.sql.DriverManager.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) NOT NULL, " +
                "lastName VARCHAR(100) NOT NULL, " +
                "age TINYINT NOT NULL" +
                ");";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось создать таблицу");
        }
    }


    public void dropUsersTable() {
        String sql ="DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось удалить таблицу");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
            try (Connection connection = Util.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                 preparedStatement.setString(1, name);
                 preparedStatement.setString(2, lastName);
                 preparedStatement.setByte(3, age);

                 preparedStatement.executeUpdate();
        } catch (SQLException e) {
               e.printStackTrace();
    }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Пользователь с id " + id + " удален.");
            } else {
                System.out.println("Пользователь с id " + id + " не найден.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось удалить пользователя с id " + id);
        }
    }

    public List<User> getAllUsers() {
        List<User> userslist = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                userslist.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userslist;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(sql);

            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось очистить таблицу");
        }

    }
}
