package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.controller.UsuarioGerenciador;
import br.unb.cic0197.copa2026.controller.SessaoSistema;
import br.unb.cic0197.copa2026.exception.UsuarioInvalidoException;
import br.unb.cic0197.copa2026.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JPanel {
    private CopaApp app;
    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JLabel imageLabel;

    public TelaLogin(CopaApp app) {
        this.app = app;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 160, 20, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titleLabel = new JLabel("Copa 2026 - Acesso");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 100, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        leftPanel.add(titleLabel, gbc);

        // Usuário (E-mail)
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        leftPanel.add(new JLabel("E-mail (Usuário):"), gbc);

        usuarioField = new JTextField(15);
        gbc.gridx = 1;
        leftPanel.add(usuarioField, gbc);

        // Senha
        gbc.gridy = 2;
        gbc.gridx = 0;
        leftPanel.add(new JLabel("Senha:"), gbc);

        senhaField = new JPasswordField(15);
        gbc.gridx = 1;
        leftPanel.add(senhaField, gbc);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton loginButton = createStyledButton("Entrar", new Color(0, 150, 0));
        JButton cadastroButton = createStyledButton("Cadastrar", new Color(0, 100, 200));

        loginButton.addActionListener(e -> fazerLogin());
        cadastroButton.addActionListener(e -> app.mostrarTela("cadastro"));

        buttonPanel.add(loginButton);
        buttonPanel.add(cadastroButton);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        leftPanel.add(buttonPanel, gbc);

        
        imageLabel = new JLabel("", JLabel.CENTER);

        try {
            java.net.URL imgURL = getClass().getResource("/imagens/copa-do-mundo-2026-logo2.jpg");
            if (imgURL != null) {
                ImageIcon iconOriginal = new ImageIcon(imgURL);
                Image imgEscalada = iconOriginal.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(imgEscalada));
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
        }

        add(leftPanel, BorderLayout.WEST);
        add(imageLabel, BorderLayout.CENTER);

        
        JLabel footerLabel = new JLabel("© 2026 Copa do Mundo - Todos os direitos reservados", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        footerLabel.setForeground(Color.GRAY);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(footerLabel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return button;
    }

    private void fazerLogin() {
        String emailInput = usuarioField.getText();
        String senhaInput = new String(senhaField.getPassword());

        if (emailInput.isEmpty() || senhaInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario usuarioLogado = UsuarioGerenciador.autenticar(emailInput, senhaInput);

            // alteração de senha
            if (usuarioLogado.isPrimeiroAcesso()) {
                String novaSenha = JOptionPane.showInputDialog(this,
                        "Este é o seu primeiro acesso com a senha gerada pelo Administrador.\nDigite sua nova senha definitiva:",
                        "Alteração de Senha Obrigatória", JOptionPane.QUESTION_MESSAGE);

                if (novaSenha == null || novaSenha.trim().length() < 3) {
                    JOptionPane.showMessageDialog(this, "Senha inválida! Login cancelado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }


            }

            SessaoSistema.iniciarSessao(usuarioLogado);
            JOptionPane.showMessageDialog(this, "Bem-vindo, " + usuarioLogado.getNome());
            if (app.getTelaMenu() != null) {
                app.getTelaMenu().renderizarMenu();
            }
            app.mostrarTela("menu");

        } catch (UsuarioInvalidoException ex) { // O seu 'ex' do erro de acesso continua aqui normal!
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Acesso", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) { // O seu 'ex' do erro interno continua aqui normal!
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro Interno", JOptionPane.ERROR_MESSAGE);
        }
    }
}
