package br.com.sistema.controller;

import br.com.sistema.model.UsuarioModel;
import br.com.sistema.view.CadastroView;
import java.awt.Color;
import java.sql.SQLException;

public class CadastroController {
    public CadastroController(UsuarioModel model, CadastroView view) {
        view.addCadastrarListener(e -> {
            String nome = view.getNomeDigitado();
            if (nome == null || nome.trim().isEmpty()) {
                view.exibirMensagemErro("Digite um nome.");
                return;
            }
            try {
                int id = model.adicionarUsuario(nome);
                view.adicionarLinhaTabela(id, nome.trim());
                view.exibirMensagemSucesso("Cadastro realizado com sucesso.");
                view.limparCampoNome();
            } catch (SQLException ex) {
                view.exibirMensagemErro("Não foi possível salvar o cadastro no banco.");
                System.err.println("Erro ao inserir usuário: " + ex.getMessage());
            }
        });
    }
}

