package br.com.sistema.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioModel {
    public int adicionarUsuario(String nome) throws SQLException {
        String sql = "INSERT INTO usuarios (nome) VALUES (?)";
        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nome.trim());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
                throw new SQLException("O banco não retornou o ID criado.");
            }
        }
    }

    public List<String> getUsuarios() throws SQLException {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT nome FROM usuarios ORDER BY id ASC";
        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(rs.getString("nome"));
        }
        return lista;
    }
}

