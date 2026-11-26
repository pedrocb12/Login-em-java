package br.com.sistema.model;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class AutenticacaoModel {
    private static final int ITERACOES = 120_000;
    private static final int TAMANHO_SALT = 16;
    private static final int TAMANHO_HASH = 256;
    private static final SecureRandom RANDOM = new SecureRandom();

    public boolean autenticar(String login, char[] senha) throws SQLException {
        String sql = "SELECT senha FROM autenticacao WHERE login = ?";
        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && verificarSenha(senha, rs.getString("senha"));
            }
        }
    }

    public boolean cadastrarNovoUsuario(String login, char[] senha) throws SQLException {
        byte[] salt = new byte[TAMANHO_SALT];
        RANDOM.nextBytes(salt);
        String senhaArmazenada = "pbkdf2$" + ITERACOES + "$"
                + Base64.getEncoder().encodeToString(salt) + "$"
                + Base64.getEncoder().encodeToString(derivarHash(senha, salt, ITERACOES));
        String sql = "INSERT INTO autenticacao (login, senha) VALUES (?, ?)";
        try (Connection conn = ConexaoBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login.trim());
            stmt.setString(2, senhaArmazenada);
            return stmt.executeUpdate() == 1;
        }
    }

    private boolean verificarSenha(char[] senha, String armazenada) {
        try {
            String[] partes = armazenada.split("\\$", -1);
            if (partes.length != 4 || !"pbkdf2".equals(partes[0])) return false;
            int iteracoes = Integer.parseInt(partes[1]);
            byte[] salt = Base64.getDecoder().decode(partes[2]);
            byte[] hashEsperado = Base64.getDecoder().decode(partes[3]);
            byte[] hashRecebido = derivarHash(senha, salt, iteracoes);
            return MessageDigest.isEqual(hashEsperado, hashRecebido);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private byte[] derivarHash(char[] senha, byte[] salt, int iteracoes) {
        PBEKeySpec spec = new PBEKeySpec(senha, salt, iteracoes, TAMANHO_HASH);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Não foi possível proteger a senha.", e);
        } finally {
            spec.clearPassword();
        }
    }
}

