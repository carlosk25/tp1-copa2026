package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JPanel {

    private JTextField txtUsuario = new JTextField(20);
    private JPasswordField txtSenha = new JPasswordField(20);
    private JButton btnLogin = new JButton("Entrar");

    public TelaLogin(CopaApp app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Usuário:"), gbc);
        gbc.gridx = 1;
        add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        add(txtSenha, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;

        btnLogin.addActionListener(e -> app.mostrarTela("menu"));
        add(btnLogin, gbc);
    }
}