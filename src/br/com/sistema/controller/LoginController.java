package br.com.sistema.controller;

import br.com.sistema.model.AutenticacaoModel;
import br.com.sistema.model.UsuarioModel;
import br.com.sistema.view.CadastroView;
import br.com.sistema.view.LoginView;
import java.awt.Color;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

public class LoginController {
    public LoginController(AutenticacaoModel authModel, LoginView loginView) {
        loginView.addEntrarListener(e -> {
            String login = loginView.getLogin().trim();
            char[] senha = loginView.getSenha();
            if (login.isEmpty() || senha.length == 0) {
                loginView.exibirMensagem("Informe o login e a senha.", Color.RED);
                return;
            }
            try {
                if (authModel.autenticar(login, senha)) {
                    abrirCadastro(loginView);
                } else {
                    loginView.exibirMensagem("Login ou senha incorretos.", Color.RED);
                }
            } catch (SQLException ex) {
                loginView.exibirMensagem("Não foi possível conectar ao banco. Confira a configuração.", Color.RED);
                System.err.println("Erro ao autenticar: " + ex.getMessage());
            } finally {
                java.util.Arrays.fill(senha, '\0');
                loginView.limparSenha();
            }
        });

        loginView.addCadastrarListener(e -> {
            String login = loginView.getLogin().trim();
            char[] senha = loginView.getSenha();
            if (login.isEmpty() || senha.length < 8) {
                loginView.exibirMensagem("Informe um login e uma senha com pelo menos 8 caracteres.", Color.RED);
                java.util.Arrays.fill(senha, '\0');
                return;
            }
            try {
                authModel.cadastrarNovoUsuario(login, senha);
                loginView.exibirMensagem("Login cadastrado. Agora você já pode entrar.", new Color(0, 128, 0));
                loginView.limparSenha();
            } catch (SQLException ex) {
                String mensagem = ex.getErrorCode() == 1062
                        ? "Esse login já está cadastrado."
                        : "Não foi possível cadastrar. Confira a conexão e tente novamente.";
                loginView.exibirMensagem(mensagem, Color.RED);
                System.err.println("Erro ao cadastrar login: " + ex.getMessage());
            } finally {
                java.util.Arrays.fill(senha, '\0');
            }
        });
    }

    private void abrirCadastro(LoginView loginView) {
        UsuarioModel model = new UsuarioModel();
        CadastroView view = new CadastroView();
        try {
            int id = 1;
            for (String nome : model.getUsuarios()) view.adicionarLinhaTabela(id++, nome);
            new CadastroController(model, view);
            loginView.dispose();
            view.setVisible(true);
        } catch (SQLException ex) {
            loginView.exibirMensagem("Não foi possível carregar os cadastros do banco.", Color.RED);
            System.err.println("Erro ao carregar usuários: " + ex.getMessage());
        }
    }
}

