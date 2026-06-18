package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.controller.UsuarioGerenciador;
import br.unb.cic0197.copa2026.controller.SessaoSistema;
import br.unb.cic0197.copa2026.exception.UsuarioInvalidoException;
import br.unb.cic0197.copa2026.model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

// primeira tela do sistema, responsável pela autenticação.
public class TelaLogin extends JPanel {
    private CopaApp app;
    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JLabel imageLabel;

    public TelaLogin(CopaApp app) {
        this.app = app;
        initComponents();
    }

    // monta os campos de email, senha e os botões de login/cadastro.
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // cabeçalho
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 118, 210)); // Azul Padrão
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel lblTitulo = new JLabel("Copa 2026 - Login ");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Entre com suas credenciais para gerenciar o sistema");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(220, 230, 242));

        headerPanel.add(lblTitulo);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        headerPanel.add(lblSubtitulo);
        add(headerPanel, BorderLayout.NORTH);


        JPanel mainContentPanel = new JPanel(new GridBagLayout());
        mainContentPanel.setBackground(new Color(245, 245, 245));
        mainContentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));


        JPanel containerBranco = new JPanel(new GridBagLayout());
        containerBranco.setBackground(Color.WHITE);
        containerBranco.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(225, 225, 225), 1),
                new EmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // usuário
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblUser = new JLabel("E-mail:");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        containerBranco.add(lblUser, gbc);

        // usuário
        gbc.gridx = 1;
        usuarioField = new JTextField(20);
        usuarioField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        usuarioField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 210, 210)),
                new EmptyBorder(6, 8, 6, 8)
        ));
        containerBranco.add(usuarioField, gbc);

        // senha
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblSenha = new JLabel("Senha de acesso:");
        lblSenha.setFont(new Font("Segoe UI", Font.BOLD, 13));
        containerBranco.add(lblSenha, gbc);

        // senha
        gbc.gridx = 1;
        senhaField = new JPasswordField(20);
        senhaField.putClientProperty("flatlaf.passwordShowRevealButton", true);
        senhaField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        senhaField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 210, 210)),
                new EmptyBorder(6, 8, 6, 8)
        ));
        containerBranco.add(senhaField, gbc);

        // painel de botões integrado ao fluxo
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton loginButton = createStyledButton("Entrar no Sistema", new Color(46, 204, 113));
        JButton cadastroButton = createStyledButton("Solicitar Acesso", new Color(52, 152, 219));

        loginButton.addActionListener(e -> fazerLogin());
        cadastroButton.addActionListener(e -> app.mostrarTela("cadastro"));

        buttonPanel.add(cadastroButton);
        buttonPanel.add(loginButton);
        containerBranco.add(buttonPanel, gbc);

        // painel  no centro
        mainContentPanel.add(containerBranco);
        add(mainContentPanel, BorderLayout.CENTER);

    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // tenta autenticar o usuário e iniciar a sessão.
    private void fazerLogin() {
        String emailInput = usuarioField.getText().trim();
        String senhaInput = new String(senhaField.getPassword());

        if (emailInput.isEmpty() || senhaInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario usuarioLogado = UsuarioGerenciador.autenticar(emailInput, senhaInput);

            SessaoSistema.iniciarSessao(usuarioLogado);
            JOptionPane.showMessageDialog(this, "Bem-vindo, " + usuarioLogado.getNome());
            if (app.getTelaMenu() != null) {
                app.getTelaMenu().renderizarMenu();
            }
            app.mostrarTela("menu");

        } catch (UsuarioInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Acesso", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro Interno", JOptionPane.ERROR_MESSAGE);
        }
    }
}
