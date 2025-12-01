package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/hljeans";
    private static final String USER = "postgres";
    private static final String PASS = "xukudd24"; 

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar no banco PostgreSQL", e);
        }
    }
}