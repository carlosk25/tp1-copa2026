package br.unb.cic0197.copa2026.view;

import javax.swing.*;
import java.awt.*;

public class TelaGestaoDeUsuarios extends JFrame {

    private JTextField txtNome = new JTextField(20);
    private JTextField txtEmail = new JTextField(20);
    private JButton btnCadastrar = new JButton("Cadastrar");
    private JButton btnRemover = new JButton("Remover");

    public TelaGestaoDeUsuarios() {
        setTitle("Gestão de Usuários");
        setSize(400, 250);
        setLayout(new GridLayout(4, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Nome:"));
        add(txtNome);

        add(new JLabel("Email:"));
        add(txtEmail);

        add(btnCadastrar);
        add(btnRemover);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaGestaoDeUsuarios();
    }
}
