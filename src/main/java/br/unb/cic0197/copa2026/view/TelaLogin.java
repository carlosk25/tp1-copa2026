package br.unb.cic0197.copa2026.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage; 

public class TelaLogin extends JFrame {

    private JTextField txtUsuario = new JTextField(20);
    private JPasswordField txtSenha = new JPasswordField(20);
    private JButton btnLogin = new JButton("Entrar");

    public TelaLogin() {
        setTitle("Copa 2026");
        setSize(550, 450);
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
        add(btnLogin, gbc);

        Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        setIconImage(icon);

        setLocationRelativeTo(null);
    }


    public static void main(String[] args) {
        new TelaLogin();
    }
}
