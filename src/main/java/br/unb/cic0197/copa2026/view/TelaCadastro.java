package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.model.SolicitacaoCadastro;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

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
        setBackground(new Color(245, 245, 245));

        // cabeçalho
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 118, 210));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel lblTitulo = new JLabel("Solicitação de Novo Cadastro");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Preencha as informações. Sua conta passará pela triagem de um administrador.");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(220, 230, 242));

        headerPanel.add(lblTitulo);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        headerPanel.add(lblSubtitulo);
        add(headerPanel, BorderLayout.NORTH);


        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(new Color(245, 245, 245));
        mainContentPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel containerBranco = new JPanel(new GridBagLayout());
        containerBranco.setBackground(Color.WHITE);
        containerBranco.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(225, 225, 225), 1),
                new EmptyBorder(20, 35, 20, 35)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        LineBorder fieldBorder = new LineBorder(new Color(210, 210, 210));
        EmptyBorder paddingBorder = new EmptyBorder(5, 8, 5, 8);

        // nome
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nomeLabel = new JLabel("Nome Completo:");
        nomeLabel.setFont(labelFont);
        containerBranco.add(nomeLabel, gbc);

        gbc.gridx = 1;
        nomeField = new JTextField(22);
        nomeField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        nomeField.setBorder(BorderFactory.createCompoundBorder(fieldBorder, paddingBorder));
        containerBranco.add(nomeField, gbc);

        // data de nascimento
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel dataLabel = new JLabel("Data de Nascimento (DD/MM/AAAA):");
        dataLabel.setFont(labelFont);
        containerBranco.add(dataLabel, gbc);

        gbc.gridx = 1;
        dataNascimentoField = new JTextField(22);
        dataNascimentoField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dataNascimentoField.setBorder(BorderFactory.createCompoundBorder(fieldBorder, paddingBorder));
        containerBranco.add(dataNascimentoField, gbc);

        // email
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel emailLabel = new JLabel("E-mail Válido:");
        emailLabel.setFont(labelFont);
        containerBranco.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = new JTextField(22);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        emailField.setBorder(BorderFactory.createCompoundBorder(fieldBorder, paddingBorder));
        containerBranco.add(emailField, gbc);

        // senha
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setFont(labelFont);
        containerBranco.add(senhaLabel, gbc);

        gbc.gridx = 1;
        senhaField = new JPasswordField(22);
        senhaField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        senhaField.setBorder(BorderFactory.createCompoundBorder(fieldBorder, paddingBorder));
        containerBranco.add(senhaField, gbc);

        // confirmar senha
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel confirmarLabel = new JLabel("Confirmar Senha:");
        confirmarLabel.setFont(labelFont);
        containerBranco.add(confirmarLabel, gbc);

        gbc.gridx = 1;
        confirmarSenhaField = new JPasswordField(22);

        confirmarSenhaField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        confirmarSenhaField.setBorder(BorderFactory.createCompoundBorder(fieldBorder, paddingBorder));
        containerBranco.add(confirmarSenhaField, gbc);

        // tipo de acesso
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel tipoLabel = new JLabel("Perfil Solicitado:");
        tipoLabel.setFont(labelFont);
        containerBranco.add(tipoLabel, gbc);

        gbc.gridx = 1;
        String[] tipos = {"Administrador", "Árbitro", "Organizador"};
        tipoAcessoCombo = new JComboBox<>(tipos);
        tipoAcessoCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tipoAcessoCombo.setBackground(Color.WHITE);
        tipoAcessoCombo.setBorder(BorderFactory.createCompoundBorder(fieldBorder, new EmptyBorder(2, 2, 2, 2)));
        containerBranco.add(tipoAcessoCombo, gbc);

        // botao de ações
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton voltarBtn = createStyledButton("Voltar ao Login", new Color(52, 152, 219));
        JButton cadastrarBtn = createStyledButton("Enviar Solicitação", new Color(46, 204, 113));

        voltarBtn.addActionListener(e -> { limparCampos(); app.mostrarTela("login"); });
        cadastrarBtn.addActionListener(e -> finalizarCadastro());

        buttonPanel.add(voltarBtn);
        buttonPanel.add(cadastrarBtn);
        containerBranco.add(buttonPanel, gbc);

        mainContentPanel.add(containerBranco, BorderLayout.CENTER);
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

        if (!validarCamposCadastro(email, dataNasc)) {
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
            adicionarSolicitacao(novaSolicitacao);

            JOptionPane.showMessageDialog(this, "Solicitação de cadastro enviada! Aguarde a aprovação de um Administrador.");
            limparCampos();
            app.mostrarTela("login");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao processar solicitação: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCamposCadastro (String email, String dataNascimento) {
        String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!Pattern.matches(regexEmail, email)) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um e-mail com estrutura válida!\nExemplo: usuario@email.com", "E-mail Inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNasc;
        try {
            dataNasc = LocalDate.parse(dataNascimento, formatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "A data de nascimento deve estar no formato correto: DD/MM/AAAA", "Data Inválida", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        LocalDate dataAtual = LocalDate.now();
        if (dataNasc.isAfter(dataAtual)) {
            JOptionPane.showMessageDialog(this, "A data de nascimento não pode ser uma data futura!", "Data Inválida", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        int idade = Period.between(dataNasc, dataAtual).getYears();
        if (idade < 18) {
            JOptionPane.showMessageDialog(this, "Cadastro não permitido: O usuário deve ter pelo menos 18 anos de idade.", "Menor de Idade", JOptionPane.WARNING_MESSAGE);
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
