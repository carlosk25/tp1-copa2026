package br.unb.cic0197.copa2026.view;
    
import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.controller.UsuarioGerenciador;
import br.unb.cic0197.copa2026.exception.UsuarioJaCadastradoException;
import br.unb.cic0197.copa2026.model.Administrador;
import br.unb.cic0197.copa2026.model.Arbitro;
import br.unb.cic0197.copa2026.model.SolicitacaoCadastro;
import br.unb.cic0197.copa2026.model.Usuario;
import br.unb.cic0197.copa2026.service.UsuarioService;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static br.unb.cic0197.copa2026.controller.UsuarioGerenciador.adicionarSolicitacao;

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
        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();
        String dataNasc = dataNascimentoField.getText().trim();
        String tipoPerfil = (String) tipoAcessoCombo.getSelectedItem();

        String senha = new String(senhaField.getPassword());
        String confirmarSenha = new String(confirmarSenhaField.getPassword());

    
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty() || dataNasc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

       
        String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!java.util.regex.Pattern.matches(regexEmail, email)) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um e-mail com estrutura válida!\nExemplo: usuario@email.com", "E-mail Inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        java.time.LocalDate dataNascimentoValida;
        try {
            dataNascimentoValida = java.time.LocalDate.parse(dataNasc, formatter);
        } catch (java.time.format.DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "A data de nascimento deve estar no formato correto: DD/MM/AAAA", "Data Inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.time.LocalDate dataAtual = java.time.LocalDate.now();
        if (dataNascimentoValida.isAfter(dataAtual)) {
            JOptionPane.showMessageDialog(this, "A data de nascimento não pode ser uma data futura!", "Data Inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idade = java.time.Period.between(dataNascimentoValida, dataAtual).getYears();
        if (idade < 18) {
            JOptionPane.showMessageDialog(this, "Cadastro não permitido: O usuário deve ter pelo menos 18 anos de idade.", "Menor de Idade", JOptionPane.WARNING_MESSAGE);
            return;
        }

        
        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas digitadas não coincidem! Tente novamente.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            senhaField.setText("");
            confirmarSenhaField.setText("");
            return;
        }

        if (senha.length() < 4) {
            JOptionPane.showMessageDialog(this, "A senha deve conter pelo menos 4 caracteres por segurança.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            
            SolicitacaoCadastro novaSolicitacao = new SolicitacaoCadastro(nome, email, senha, dataNasc, tipoPerfil);

            // Envia para o gerenciador salvar no txt
            adicionarSolicitacao(novaSolicitacao);

            JOptionPane.showMessageDialog(this, "Solicitação de cadastro enviada! Aguarde a aprovação de um Administrador.");

            // Limpa os campos após o sucesso
            nomeField.setText("");
            emailField.setText("");
            dataNascimentoField.setText("");
            senhaField.setText("");
            confirmarSenhaField.setText("");

            // Volta para a tela de login
            app.mostrarTela("login");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao processar solicitação: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private boolean validarCamposCadastro (String email, String dataNascimento) {
        // validaçao email
        String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!Pattern.matches(regexEmail, email)) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, insira um e-mail com estrutura válida!\nExemplo: usuario@email.com",
                    "E-mail Inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // validação data (Espera dd/MM/yyyy)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNasc;
        try {
            dataNasc = LocalDate.parse(dataNascimento, formatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "A data de nascimento deve estar no formato correto: DD/MM/AAAA",
                    "Data Inválida", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // maioridade (Mínimo 18 anos em relação ao ano atual de 2026)
        LocalDate dataAtual = LocalDate.now(); 
        if (dataNasc.isAfter(dataAtual)) {
            JOptionPane.showMessageDialog(this, "A data de nascimento não pode ser uma data futura!", "Data Inválida", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        int idade = Period.between(dataNasc, dataAtual).getYears();
        if (idade < 18) {
            JOptionPane.showMessageDialog(this,
                    "Cadastro não permitido: O usuário deve ter pelo menos 18 anos de idade.",
                    "Menor de Idade", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
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
