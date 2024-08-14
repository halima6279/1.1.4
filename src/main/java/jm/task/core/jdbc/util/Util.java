package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Util {
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/mysql";
    public static final String DB_USERNAME = "Halima";
    public static final String DB_PASSWORD = "Halime6289.";
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connection OK");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Добавление классов сущностей
                configuration.addAnnotatedClass(User.class);


                configuration.setProperty(Environment.DRIVER, DB_DRIVER);
                configuration.setProperty(Environment.URL, DB_URL);
                configuration.setProperty(Environment.USER, DB_USERNAME);
                configuration.setProperty(Environment.PASS, DB_PASSWORD);
                configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                configuration.setProperty(Environment.SHOW_SQL, "true");
                configuration.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                configuration.setProperty(Environment.HBM2DDL_AUTO, "update");


                sessionFactory = (SessionFactory) configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Hibernate Configuration ERROR");
            }
        }
        return sessionFactory;
    }


}














