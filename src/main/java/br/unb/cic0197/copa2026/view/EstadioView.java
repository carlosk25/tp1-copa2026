package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.model.Estadio;

import javax.swing.*;
import java.awt.*;

public class EstadioView extends JPanel {
    private final JTextField txtNome = new JTextField(20);
    private final JTextField txtLocal = new JTextField(20);
    private final JTextField txtCapacidade = new JTextField(10);

    public EstadioView(CopaApp app) {
        setLayout(new BorderLayout(16, 16));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        setBackground(new Color(248, 249, 250));

        JLabel title = new JLabel("Cadastro de Estádios");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(34, 49, 63));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 12, 12));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Dados do estádio"));

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(txtNome);
        formPanel.add(new JLabel("Localização:"));
        formPanel.add(txtLocal);
        formPanel.add(new JLabel("Capacidade:"));
        formPanel.add(txtCapacidade);

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        btnPanel.setOpaque(false);
        JButton btnSalvar = styleButton("Salvar Estádio");
        JButton btnListar = styleButton("Listar Estádios");
        JButton btnVoltar = styleButton("Voltar");

        btnPanel.add(btnSalvar);
        btnPanel.add(btnListar);
        btnPanel.add(btnVoltar);
        add(btnPanel, BorderLayout.SOUTH);

        JTextArea output = new JTextArea(10, 1);
        output.setEditable(false);
        output.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        output.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(new JScrollPane(output), BorderLayout.EAST);

        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String local = txtLocal.getText().trim();
            String capacidadeTexto = txtCapacidade.getText().trim();

            if (nome.isBlank() || local.isBlank() || capacidadeTexto.isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int capacidade = Integer.parseInt(capacidadeTexto);
                Estadio estadio = new Estadio(nome, local, capacidade);
                // app.adicionarEstadio(estadio);
                JOptionPane.showMessageDialog(this, "Estádio salvo com sucesso.", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                txtNome.setText("");
                txtLocal.setText("");
                txtCapacidade.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Capacidade deve ser um número.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnListar.addActionListener(e -> {
            StringBuilder builder = new StringBuilder();
            for (Estadio estadio : app.getEstadios()) {
                builder.append(estadio.getNome())
                        .append(" - ").append(estadio.getLocalizacao())
                        .append(" - Capacidade: ").append(estadio.getCapacidade())
                        .append("\n");
            }
            output.setText(builder.length() == 0 ? "Nenhum estádio cadastrado." : builder.toString());
        });

        btnVoltar.addActionListener(e -> app.mostrarTela("menu"));
    }

    private JButton styleButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(37, 99, 235));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        return button;
    }
}
