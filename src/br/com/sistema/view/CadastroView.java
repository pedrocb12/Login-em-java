package br.com.sistema.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CadastroView extends JFrame {
    private final JTextField txtNome = new JTextField(15);
    private final JButton btnCadastrar = new JButton("Cadastrar");
    private final JLabel lblStatus = new JLabel(" ");
    private final DefaultTableModel modeloTabela = new DefaultTableModel(
            new Object[]{"Nº", "Nome Cadastrado"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };

    public CadastroView() {
        setTitle("Sistema de Cadastro - Java Swing + MySQL");
        setSize(480, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelEntrada = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelEntrada.add(new JLabel("Nome:"));
        painelEntrada.add(txtNome);
        painelEntrada.add(btnCadastrar);

        add(painelEntrada, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(modeloTabela)), BorderLayout.CENTER);
        add(lblStatus, BorderLayout.SOUTH);
    }

    public String getNomeDigitado() { return txtNome.getText(); }
    public void limparCampoNome() { txtNome.setText(""); txtNome.requestFocus(); }
    public void adicionarLinhaTabela(int id, String nome) { modeloTabela.addRow(new Object[]{id, nome}); }
    public void exibirMensagemSucesso(String mensagem) { exibirMensagem(mensagem, new Color(0, 128, 0)); }
    public void exibirMensagemErro(String mensagem) { exibirMensagem(mensagem, Color.RED); }
    private void exibirMensagem(String mensagem, Color cor) { lblStatus.setForeground(cor); lblStatus.setText(mensagem); }
    public void addCadastrarListener(ActionListener listener) { btnCadastrar.addActionListener(listener); }
}

