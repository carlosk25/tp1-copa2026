package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.model.Selecao;

import javax.swing.*;
import java.awt.*;

public class TelaSelecao extends JPanel {

    public TelaSelecao(CopaApp app) {
        setLayout(new BorderLayout(16, 16));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        setBackground(new Color(246, 248, 250));

        JLabel title = new JLabel("Gerenciamento de Seleções");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(34, 49, 63));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 12, 12));
        formPanel.setOpaque(false);
        formPanel.add(new JLabel("País:"));
        JTextField txtPais = new JTextField();
        formPanel.add(txtPais);

        formPanel.add(new JLabel("Grupo:"));
        String[] grupos = {"Grupo 1", "Grupo 2", "Grupo 3", "Grupo 4",
                "Grupo 5", "Grupo 6", "Grupo 7", "Grupo 8"};
        JComboBox<String> comboGrupo = new JComboBox<>(grupos);
        formPanel.add(comboGrupo);

        formPanel.add(new JLabel("Técnico:"));
        JTextField txtTecnico = new JTextField();
        formPanel.add(txtTecnico);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        buttonsPanel.setOpaque(false);
        JButton btnAdicionar = styleButton("Adicionar");
        JButton btnListar = styleButton("Carregar seleções");
        JButton btnExcluir = styleButton("Excluir");
        JButton btnVoltar = styleButton("Voltar");

        buttonsPanel.add(btnAdicionar);
        buttonsPanel.add(btnListar);
        buttonsPanel.add(btnExcluir);
        buttonsPanel.add(btnVoltar);

        add(buttonsPanel, BorderLayout.SOUTH);

        JTextArea outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        outputArea.setBackground(new Color(255, 255, 255));
        outputArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(new JScrollPane(outputArea), BorderLayout.EAST);

        btnAdicionar.addActionListener(e -> {
            String pais = txtPais.getText().trim();
            String grupo = (String) comboGrupo.getSelectedItem();
            String tecnico = txtTecnico.getText().trim();

            if (pais.isBlank() || tecnico.isBlank()) {
                JOptionPane.showMessageDialog(this,
                        "País e técnico são obrigatórios.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Selecao selecao = new Selecao(app.gerarId(), pais, grupo, tecnico);
            app.adicionarSelecao(selecao);
            JOptionPane.showMessageDialog(this,
                    "Seleção salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            txtPais.setText("");
            txtTecnico.setText("");
        });

        btnListar.addActionListener(e -> {
            StringBuilder builder = new StringBuilder();
            for (Selecao selecao : app.getSelecoes()) {
                builder.append(selecao.getPais())
                        .append(" - ").append(selecao.getGrupo())
                        .append(" - Técnico: ").append(selecao.getTecnico())
                        .append("\n");
            }
            outputArea.setText(builder.length() == 0 ? "Nenhuma seleção encontrada." : builder.toString());
        });

        btnExcluir.addActionListener(e -> {
            String pais = txtPais.getText().trim();
            if (pais.isBlank()) {
                JOptionPane.showMessageDialog(this,
                        "Informe o país para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            app.removerSelecaoPorPais(pais);
            JOptionPane.showMessageDialog(this,
                    "Seleção removida, se existente.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVoltar.addActionListener(e -> app.mostrarTela("menu"));
    }

    private JButton styleButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(45, 125, 255));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        return button;
    }
}
