package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import javax.swing.*;
import java.awt.*;


public class TelaRelatorio extends JPanel {
    private CopaApp app;

    public TelaRelatorio(CopaApp app) {
        this.app = app;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("RELATÓRIOS CONSOLIDADOS DA COMPETIÇÃO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        String[] colunas = {"Métrica", "Valor Consolidado", "Detalhes"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        modelo.addRow(new Object[]{"Número de Partidas", "64", "Total do torneio"});
        modelo.addRow(new Object[]{"Público Total", "3.450.000", "Média de 53k por jogo"});
        modelo.addRow(new Object[]{"Desempenho de Seleções", "Brasil", "Melhor ataque"});

        JTable tabela = new JTable(modelo);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Painel de pesquisa 
        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelPesquisa.add(new JLabel("Pesquisar por:"));
        JComboBox<String> comboCriterios = new JComboBox<>(new String[]{"Nome", "Função", "País", "Status"});
        JTextField txtPesquisa = new JTextField(15);
        JButton btnBuscar = new JButton("Pesquisar");

        painelPesquisa.add(comboCriterios);
        painelPesquisa.add(txtPesquisa);
        painelPesquisa.add(btnBuscar);

      
        add(painelPesquisa, BorderLayout.BEFORE_FIRST_LINE);

      
        JButton btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.addActionListener(e -> app.mostrarTela("dashboard"));
        add(btnVoltar, BorderLayout.SOUTH);
    }
}
