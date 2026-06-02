package br.unb.cic0197.copa2026.view
    
import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.controller.UsuarioGerenciador;
import br.unb.cic0197.copa2026.exception.UsuarioJaCadastradoException;
import br.unb.cic0197.copa2026.model.Administrador;
import br.unb.cic0197.copa2026.model.Arbitro;
import br.unb.cic0197.copa2026.model.SolicitacaoCadastro;
import br.unb.cic0197.copa2026.model.Usuario;
import br.unb.cic0197.copa2026.services.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TelaCadastro extends JPanel {
    private CopaApp app;
    private JTextField nomeField;
    private JTextField dataNascimentoField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JPasswordField confirmarSenhaField;
    private JComboBox<String> tipoAcessoCombo;

    public TelaCadastro (CopaApp app) {
        this.app = app;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        // Painel central com formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);

        // Título
        JLabel titleLabel = new JLabel("Copa 2026 - Cadastro de Usuário");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 100, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(titleLabel, gbc);

        // Espaço entre título e campos
        gbc.gridy = 1;
        formPanel.add(Box.createVerticalStrut(10), gbc);

        // Nome Completo
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel nomeLabel = new JLabel("Nome Completo:");
        nomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(nomeLabel, gbc);

        nomeField = new JTextField(25);
        nomeField.setFont(new Font("Arial", Font.PLAIN, 14));
        nomeField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nomeField, gbc);

        // Data de Nascimento
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel dataLabel = new JLabel("Data de Nascimento:");
        dataLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(dataLabel, gbc);

        dataNascimentoField = new JTextField(25);
        dataNascimentoField.setFont(new Font("Arial", Font.PLAIN, 14));
        dataNascimentoField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(dataNascimentoField, gbc);

        // E-mail
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel emailLabel = new JLabel("E-mail:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField(25);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(emailField, gbc);

        // Senha - Cadastro com a senha gerada pelo admin
        /*
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(senhaLabel, gbc);

        senhaField = new JPasswordField(25);
        senhaField.setFont(new Font("Arial", Font.PLAIN, 14));
        senhaField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(senhaField, gbc);

        // Confirmar Senha
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel confirmarLabel = new JLabel("Confirmar Senha:");
        confirmarLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(confirmarLabel, gbc);

        confirmarSenhaField = new JPasswordField(25);
        confirmarSenhaField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmarSenhaField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(confirmarSenhaField, gbc);
        */
        // Tipo de Acesso
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel tipoLabel = new JLabel("Tipo de Acesso:");
        tipoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(tipoLabel, gbc);

        String[] tipos = {"Administrador", "Árbitro", "Organizador"};
        tipoAcessoCombo = new JComboBox<>(tipos);
        tipoAcessoCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        tipoAcessoCombo.setBackground(Color.WHITE);
        tipoAcessoCombo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(tipoAcessoCombo, gbc);

        // Botões
        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton voltarBtn = createStyledButton("← Voltar", new Color(100, 100, 100));
        JButton cadastrarBtn = createStyledButton("Solicitar Cadastro", new Color(0, 150, 0));

        voltarBtn.addActionListener(e -> app.mostrarTela("login"));
        cadastrarBtn.addActionListener(e -> finalizarCadastro());

        buttonPanel.add(voltarBtn);
        buttonPanel.add(cadastrarBtn);

        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        JLabel footerLabel = new JLabel("© 2026 Copa do Mundo - Todos os direitos reservados", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        footerLabel.setForeground(Color.GRAY);
        add(footerLabel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }


    private void finalizarCadastro() {
      
        String nome = nomeField.getText();                  
        String email = emailField.getText();                
        String dataNasc = dataNascimentoField.getText();
        String perfil = tipoAcessoCombo.getSelectedItem().toString();

        SolicitacaoCadastro novaSolicitacao = new SolicitacaoCadastro(nome, email, dataNasc, perfil);
        UsuarioService usuarioService = new UsuarioService();

        try {
            usuarioService.cadastrarSolicitacao(novaSolicitacao);
            JOptionPane.showMessageDialog(this, "Solicitação de cadastro enviada aos administradores com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            app.mostrarTela("login");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        dataNascimentoField.setText("");
        emailField.setText("");
        senhaField.setText("");
        confirmarSenhaField.setText("");
        tipoAcessoCombo.setSelectedIndex(0);
    }
}
