package br.com.sistema.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexaoBanco {
    private static final String URL = "jdbc:mysql://localhost:3306/db_sistema?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String USUARIO = System.getenv().getOrDefault("DB_USER", "root");
    private static final String SENHA = System.getenv().getOrDefault("DB_PASSWORD", "");

    private ConexaoBanco() { }

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}

