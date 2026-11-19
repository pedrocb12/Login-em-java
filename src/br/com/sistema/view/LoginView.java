package br.com.sistema.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JFrame {
    private final JTextField txtLogin = new JTextField(18);
    private final JPasswordField txtSenha = new JPasswordField(18);
    private final JButton btnEntrar = new JButton("Entrar");
    private final JButton btnCadastrar = new JButton("Novo Usuário");
    private final JLabel lblStatus = new JLabel(" ");

    public LoginView() {
        setTitle("Acesso ao Sistema");
        setSize(380, 230);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel campos = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(7, 7, 7, 7);
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 0; c.gridy = 0; campos.add(new JLabel("Login:"), c);
        c.gridx = 1; c.anchor = GridBagConstraints.LINE_START; campos.add(txtLogin, c);
        c.gridx = 0; c.gridy = 1; c.anchor = GridBagConstraints.LINE_END; campos.add(new JLabel("Senha:"), c);
        c.gridx = 1; c.anchor = GridBagConstraints.LINE_START; campos.add(txtSenha, c);

        JPanel botoes = new JPanel();
        botoes.add(btnEntrar);
        botoes.add(btnCadastrar);
        lblStatus.setForeground(Color.DARK_GRAY);
        add(campos, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
        add(lblStatus, BorderLayout.NORTH);
    }

    public String getLogin() { return txtLogin.getText(); }
    public char[] getSenha() { return txtSenha.getPassword(); }
    public void limparSenha() { txtSenha.setText(""); }
    public void exibirMensagem(String mensagem, Color cor) {
        lblStatus.setForeground(cor);
        lblStatus.setText(mensagem);
    }
    public void addEntrarListener(ActionListener listener) { btnEntrar.addActionListener(listener); }
    public void addCadastrarListener(ActionListener listener) { btnCadastrar.addActionListener(listener); }
}

