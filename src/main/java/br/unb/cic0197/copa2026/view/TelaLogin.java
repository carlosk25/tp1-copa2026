package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.controller.LoginController;
import br.unb.cic0197.copa2026.model.Usuario;
import exception.AutenticacaoException; //depos

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JPanel {
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JLabel lblMensagem;
    private LoginController loginController;
    private CopaApp app;

    public TelaLogin(CopaApp app) {
        this.app = app;
        this.loginController = new LoginController();
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // titulo
        JLabel titulo = new JLabel("🔐 Sistema de Administração - Copa 2026");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titulo, gbc);

        gbc.gridy = 1;
        add(new JLabel(" "), gbc);

        // email
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Email:"), gbc);
        
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        add(txtEmail, gbc);

        // senhaa
        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new JLabel("Senha:"), gbc);
        
        txtSenha = new JPasswordField(20);
        gbc.gridx = 1;
        add(txtSenha, gbc);

        // botoes
        JPanel painelBotoes = new JPanel(new FlowLayout());
        
        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.addActionListener(e -> fazerLogin());
        
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        
        painelBotoes.add(btnEntrar);
        painelBotoes.add(btnLimpar);
        
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(painelBotoes, gbc);

        // mensagem
        lblMensagem = new JLabel(" ");
        lblMensagem.setForeground(Color.RED);
        gbc.gridy = 5;
        add(lblMensagem, gbc);

        // Infos do usuario padrao
        JLabel infoLabel = new JLabel("Usuário padrão: admin@sistema.com | senha: admin123");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        infoLabel.setForeground(Color.GRAY);
        gbc.gridy = 6;
        add(infoLabel, gbc);
    }

    private void fazerLogin() {
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());

        try {
            Usuario usuario = loginController.realizarLogin(email, senha);
            lblMensagem.setForeground(Color.GREEN);
            lblMensagem.setText("✅ Bem-vindo, " + usuario.getNome() + "!");
            
            Timer timer = new Timer(1000, e -> {
                app.mostrarTela("menu");
            });
            timer.setRepeats(false);
            timer.start();
            
        } catch (AutenticacaoException e) {
            lblMensagem.setForeground(Color.RED);
            lblMensagem.setText("❌ Erro: " + e.getMessage());
        }
    }

    private void limparCampos() {
        txtEmail.setText("");
        txtSenha.setText("");
        lblMensagem.setText(" ");
    }
}
