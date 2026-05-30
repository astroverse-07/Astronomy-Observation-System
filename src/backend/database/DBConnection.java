package backend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/astronomy_system?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Astro2007"; //
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("[DB ERROR] Could not find MySQL JDBC Driver JAR: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[DB ERROR] Connection failed! Check Credentials: " + e.getMessage());
        }
        return conn;
    }
}