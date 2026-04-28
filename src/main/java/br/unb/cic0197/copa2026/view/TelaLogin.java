package br.unb.cic0197.copa2026.view;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    private JTextField txtUsuario = new JTextField(20);
    private JPasswordField txtSenha = new JPasswordField(20);
    private JButton btnLogin = new JButton("Entrar");

    public TelaLogin() {
        setTitle("Login - Copa 2026");
        setSize(350, 200);
        setLayout(new GridLayout(3, 2, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Usuário:"));
        add(txtUsuario);

        add(new JLabel("Senha:"));
        add(txtSenha);

        add(new JLabel(""));
        add(btnLogin);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaLogin();
    }
}
