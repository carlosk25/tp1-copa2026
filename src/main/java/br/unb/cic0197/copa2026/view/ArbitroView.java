package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.model.Arbitro;

import javax.swing.*;
import java.awt.*;

public class ArbitroView extends JPanel {
    private final JTextField txtNome = new JTextField(20);
    private final JTextField txtNacionalidade = new JTextField(20);
    private final JTextField txtExperiencia = new JTextField(10);

    public ArbitroView(CopaApp app) {
        setLayout(new BorderLayout(16, 16));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        setBackground(new Color(248, 249, 250));

        JLabel title = new JLabel("Cadastro de Árbitros");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(34, 49, 63));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 12, 12));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Dados do árbitro"));

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(txtNome);
        formPanel.add(new JLabel("Nacionalidade:"));
        formPanel.add(txtNacionalidade);
        formPanel.add(new JLabel("Experiência:"));
        formPanel.add(txtExperiencia);

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        btnPanel.setOpaque(false);
        JButton btnSalvar = styleButton("Salvar Árbitro");
        JButton btnListar = styleButton("Listar Árbitros");
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
            String nacionalidade = txtNacionalidade.getText().trim();
            String experiencia = txtExperiencia.getText().trim();

            if (nome.isBlank() || nacionalidade.isBlank() || experiencia.isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Arbitro arbitro = new Arbitro(nome, nacionalidade, experiencia);
            app.adicionarArbitro(arbitro);
            JOptionPane.showMessageDialog(this, "Árbitro salvo com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            txtNome.setText("");
            txtNacionalidade.setText("");
            txtExperiencia.setText("");
        });

        btnListar.addActionListener(e -> {
            StringBuilder builder = new StringBuilder();
            for (Arbitro arbitro : app.getArbitros()) {
                builder.append(arbitro.getNome())
                        .append(" - ").append(arbitro.getNacionalidade())
                        .append(" - ").append(arbitro.getExperiencia())
                        .append("\n");
            }
            output.setText(builder.length() == 0 ? "Nenhum árbitro cadastrado." : builder.toString());
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
