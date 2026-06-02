package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.model.Jogador;
import br.unb.cic0197.copa2026.model.Selecao;

import javax.swing.*;
import java.awt.*;

public class TelaJogadores extends JPanel {

    public TelaJogadores(CopaApp app) {
        setLayout(new BorderLayout(16, 16));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        setBackground(new Color(248, 249, 250));

        JLabel title = new JLabel("Cadastro de Jogadores");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(34, 49, 63));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 12, 12));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Dados do jogador"));

        formPanel.add(new JLabel("Nome:"));
        JTextField txtNome = new JTextField();
        formPanel.add(txtNome);

        formPanel.add(new JLabel("Posição:"));
        String[] posicoes = {"Goleiro", "Lateral Esquerdo", "Lateral Direito", "Zagueiro", "Volante", "Meia-Central", "Meia-Direito",
                "Meia-Ofensivo", "Ponta Esquerda", "Ponta Direita", "Atacante", "Segundo Atacante"};
        JComboBox<String> comboPosicao = new JComboBox<>(posicoes);
        formPanel.add(comboPosicao);

        formPanel.add(new JLabel("Número:"));
        JTextField txtNumero = new JTextField();
        formPanel.add(txtNumero);

        formPanel.add(new JLabel("Idade:"));
        JTextField txtIdade = new JTextField();
        formPanel.add(txtIdade);

        formPanel.add(new JLabel("Seleção:"));
        JComboBox<String> comboSelecao = new JComboBox<>();
        updateSelecaoCombo(app, comboSelecao);
        formPanel.add(comboSelecao);

        add(formPanel, BorderLayout.WEST);

        JPanel filterPanel = new JPanel(new GridLayout(0, 2, 12, 12));
        filterPanel.setOpaque(false);
        filterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Filtro"));
        filterPanel.add(new JLabel("Posição:"));
        String[] posicoesComTodas = {"Todas", "Goleiro", "Lateral Direito", "Lateral Esquerdo",
                "Zagueiro", "Volante", "Meio-Campo", "Atacante", "Centroavante"};
        JComboBox<String> comboFiltroPosicao = new JComboBox<>(posicoesComTodas);
        filterPanel.add(comboFiltroPosicao);
        filterPanel.add(new JLabel("Seleção:"));
        JTextField txtFiltroSelecao = new JTextField();
        filterPanel.add(txtFiltroSelecao);
        JButton btnFiltrar = styleButton("Filtrar");
        filterPanel.add(btnFiltrar);
        JButton btnAtualizar = styleButton("Atualizar seleções");
        filterPanel.add(btnAtualizar);

        JPanel rightPanel = new JPanel(new BorderLayout(12, 12));
        rightPanel.setOpaque(false);
        rightPanel.add(filterPanel, BorderLayout.NORTH);

        JTextArea outputArea = new JTextArea(12, 1);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        outputArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        rightPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        add(rightPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        buttonsPanel.setOpaque(false);
        JButton btnAdicionar = styleButton("Adicionar");
        JButton btnListar = styleButton("Listar");
        JButton btnEditar = styleButton("Editar");
        JButton btnExcluir = styleButton("Excluir");
        JButton btnVoltar = styleButton("Voltar");

        buttonsPanel.add(btnAdicionar);
        buttonsPanel.add(btnListar);
        buttonsPanel.add(btnEditar);
        buttonsPanel.add(btnExcluir);
        buttonsPanel.add(btnVoltar);

        add(buttonsPanel, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String posicao = (String) comboPosicao.getSelectedItem();
            String numeroTexto = txtNumero.getText().trim();
            String idadeTexto = txtIdade.getText().trim();
            String selecaoPais = (String) comboSelecao.getSelectedItem();

            if (nome.isBlank() || numeroTexto.isBlank() || idadeTexto.isBlank() || selecaoPais == null || selecaoPais.isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Selecao selecao = app.findSelecaoPorPais(selecaoPais);
            if (selecao == null) {
                JOptionPane.showMessageDialog(this, "Seleção inválida. Atualize as seleções.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int numero = Integer.parseInt(numeroTexto);
                int idade = Integer.parseInt(idadeTexto);
                Jogador jogador = new Jogador(app.gerarId(), nome, posicao, numero, idade, Jogador.StatusJogador.ATIVO, selecao);
                app.adicionarJogador(jogador);
                JOptionPane.showMessageDialog(this, "Jogador salvo com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                txtNome.setText("");
                txtNumero.setText("");
                txtIdade.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Número e idade devem ser valores numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnListar.addActionListener(e -> {
            StringBuilder builder = new StringBuilder();
            for (Jogador jogador : app.getJogadores()) {
                builder.append(jogador.getNome())
                        .append(" - ").append(jogador.getPosicao())
                        .append(" - ").append(jogador.getNumero())
                        .append(" - ").append(jogador.getSelecao().getPais())
                        .append("\n");
            }
            outputArea.setText(builder.length() == 0 ? "Nenhum jogador cadastrado." : builder.toString());
        });

        btnExcluir.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            if (nome.isBlank()) {
                JOptionPane.showMessageDialog(this, "Digite o nome do jogador para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            app.removerJogadorPorNome(nome);
            JOptionPane.showMessageDialog(this, "Jogador removido se existia.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        });

        btnFiltrar.addActionListener(e -> {
            String filtroPosicao = ((String) comboFiltroPosicao.getSelectedItem()).trim();
            String filtroSelecao = txtFiltroSelecao.getText().trim();
            StringBuilder builder = new StringBuilder();
            for (Jogador jogador : app.getJogadores()) {
                boolean correspondePosicao = filtroPosicao.equals("Todas") || jogador.getPosicao().equalsIgnoreCase(filtroPosicao);
                boolean correspondeSelecao = filtroSelecao.isBlank() || jogador.getSelecao().getPais().equalsIgnoreCase(filtroSelecao);
                if (correspondePosicao && correspondeSelecao) {
                    builder.append(jogador.getNome())
                            .append(" - ").append(jogador.getPosicao())
                            .append(" - ").append(jogador.getSelecao().getPais())
                            .append("\n");
                }
            }
            outputArea.setText(builder.length() == 0 ? "Nenhum jogador encontrado." : builder.toString());
        });

        btnAtualizar.addActionListener(e -> updateSelecaoCombo(app, comboSelecao));
        btnEditar.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Edição ainda não implementada.", "Informação", JOptionPane.INFORMATION_MESSAGE));
        btnVoltar.addActionListener(e -> app.mostrarTela("menu"));
    }

    private void updateSelecaoCombo(CopaApp app, JComboBox<String> comboSelecao) {
        comboSelecao.removeAllItems();
        for (Selecao selecao : app.getSelecoes()) {
            comboSelecao.addItem(selecao.getPais());
        }
    }

    private JButton styleButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(40, 116, 252));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        return button;
    }
}
