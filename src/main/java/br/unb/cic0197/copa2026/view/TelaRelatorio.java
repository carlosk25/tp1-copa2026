package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.enums.FaseCompeticao;
import br.unb.cic0197.copa2026.model.Partida;
import br.unb.cic0197.copa2026.model.ResultadoPartida;
import br.unb.cic0197.copa2026.model.Selecao;
import br.unb.cic0197.copa2026.service.PartidaService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TelaRelatorio extends JPanel {
    private CopaApp app;

    private JLabel lblPartidasGerais;
    private JTextField txtPesquisaSelecaoPartida;
    private JLabel lblPartidasSelecaoEspecifica;

    private DefaultTableModel modeloDesempenho;
    private JTable tabelaDesempenho;
    private JComboBox<String> comboFiltroDesempenho;

    public TelaRelatorio(CopaApp app) {
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

        JLabel lblTitulo = new JLabel("Estatísticas e Relatórios Oficiais");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Consulte o engajamento geral de partidas e ordene o desempenho técnico das seleções");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(220, 230, 242));

        headerPanel.add(lblTitulo);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        headerPanel.add(lblSubtitulo);
        add(headerPanel, BorderLayout.NORTH);

        // painel central
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBackground(new Color(245, 245, 245));
        mainContentPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // controle de partidas
        JPanel cardPartidas = criarCardBranco("Métricas Quantitativas de Partidas");
        cardPartidas.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblGeralText = new JLabel("Total de Partidas Cadastradas no Sistema:");
        lblGeralText.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cardPartidas.add(lblGeralText, gbc);

        gbc.gridx = 1;
        lblPartidasGerais = new JLabel("0");
        lblPartidasGerais.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPartidasGerais.setForeground(new Color(25, 118, 210));
        cardPartidas.add(lblPartidasGerais, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblBuscaText = new JLabel("Buscar Seleção Específica (País):");
        lblBuscaText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cardPartidas.add(lblBuscaText, gbc);

        gbc.gridx = 1;
        txtPesquisaSelecaoPartida = new JTextField(15);
        txtPesquisaSelecaoPartida.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPesquisaSelecaoPartida.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 210, 210)), new EmptyBorder(4, 6, 4, 6)
        ));
        cardPartidas.add(txtPesquisaSelecaoPartida, gbc);

        gbc.gridx = 2;
        JButton btnCalcular = createStyledButton("Calcular", new Color(25, 118, 210));
        btnCalcular.addActionListener(e -> calcularPartidasEspecificas());
        cardPartidas.add(btnCalcular, gbc);

        gbc.gridx = 3;
        lblPartidasSelecaoEspecifica = new JLabel("Partidas desta Seleção: 0");
        lblPartidasSelecaoEspecifica.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPartidasSelecaoEspecifica.setForeground(new Color(46, 204, 113));
        cardPartidas.add(lblPartidasSelecaoEspecifica, gbc);

        mainContentPanel.add(cardPartidas);
        mainContentPanel.add(Box.createRigidArea(new Dimension(0, 15)));


        JPanel cardDesempenho = criarCardBranco("Classificação e Rendimento Técnico das Seleções");
        cardDesempenho.setLayout(new BorderLayout(10, 10));

        // Barra de Filtros
        JPanel painelFiltroEstiloSite = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelFiltroEstiloSite.setBackground(Color.WHITE);
        painelFiltroEstiloSite.add(new JLabel("Ordenar Tabela por:"));

        String[] opcoesFiltro = {"Padrão (Alfabética)", "Mais Pontos", "Menos Pontos", "Mais Gols Marcados", "Menos Gols Marcados"};
        comboFiltroDesempenho = new JComboBox<>(opcoesFiltro);
        comboFiltroDesempenho.setBackground(Color.WHITE);
        comboFiltroDesempenho.addActionListener(e -> processarEAtualizarDados());
        painelFiltroEstiloSite.add(comboFiltroDesempenho);

        cardDesempenho.add(painelFiltroEstiloSite, BorderLayout.NORTH);

        // Tabela de Dados
        String[] colunas = {"Colocação", "Seleção/País", "Pontos (Fase de Grupos)", "Gols Marcados"};
        modeloDesempenho = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaDesempenho = new JTable(modeloDesempenho);
        tabelaDesempenho.setRowHeight(24);
        tabelaDesempenho.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JScrollPane scrollTabela = new JScrollPane(tabelaDesempenho);
        scrollTabela.setBorder(new LineBorder(new Color(230, 230, 230)));
        cardDesempenho.add(scrollTabela, BorderLayout.CENTER);


        JPanel painelAcoesRelatorio = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        painelAcoesRelatorio.setBackground(Color.WHITE);

        JButton btnVoltar = createStyledButton("Voltar", new Color(100, 100, 100));

        btnVoltar.addActionListener(e -> app.mostrarTela("menu"));

        painelAcoesRelatorio.add(btnVoltar);
        cardDesempenho.add(painelAcoesRelatorio, BorderLayout.SOUTH);

        mainContentPanel.add(cardDesempenho);
        add(mainContentPanel, BorderLayout.CENTER);
    }


    public void atualizarTela() {
        lblPartidasGerais.setText(String.valueOf(app.getPartidas().size()));
        txtPesquisaSelecaoPartida.setText("");
        lblPartidasSelecaoEspecifica.setText("Partidas desta Seleção: 0");
        comboFiltroDesempenho.setSelectedIndex(0);
        processarEAtualizarDados();
    }

    private void processarEAtualizarDados() {
        List<Selecao> listaSelecoes = app.getSelecoes();

        br.unb.cic0197.copa2026.service.PartidaService partidaService = new PartidaService();
        List<Partida> listaPartidas = partidaService.obterTodas();

        if (lblPartidasGerais != null) {
            lblPartidasGerais.setText(String.valueOf(listaPartidas.size()));
        }

        List<LinhaDesempenho> dadosCalculados = new ArrayList<>();

        for (Selecao s : listaSelecoes) {
            LinhaDesempenho linha = new LinhaDesempenho();
            linha.pais = s.getPais();

            for (Partida p : listaPartidas) {
                ResultadoPartida res = p.getResultado();
                if (res == null) continue;

                boolean ehSelecaoA = p.getSelecaoA().equalsIgnoreCase(s.getPais());
                boolean ehSelecaoB = p.getSelecaoB().equalsIgnoreCase(s.getPais());

                if (ehSelecaoA) {
                    linha.golsMarcados += res.getGolsA();
                    if (p.getFase() == FaseCompeticao.GRUPO || p.getFase() == null) {
                        if (res.getGolsA() > res.getGolsB()) linha.pontos += 3;
                        else if (res.getGolsA() == res.getGolsB()) linha.pontos += 1;
                    }
                } else if (ehSelecaoB) {
                    linha.golsMarcados += res.getGolsB();
                    if (p.getFase() == FaseCompeticao.GRUPO || p.getFase() == null) {
                        if (res.getGolsB() > res.getGolsA()) linha.pontos += 3;
                        else if (res.getGolsB() == res.getGolsA()) linha.pontos += 1;
                    }
                }
            }
            dadosCalculados.add(linha);
        }

        String filter = (String) comboFiltroDesempenho.getSelectedItem();
        if (filter == null) filter = "Padrão (Alfabética)";

        switch (filter) {
            case "Mais Pontos":
                dadosCalculados.sort((d1, d2) -> Integer.compare(d2.pontos, d1.pontos));
                break;
            case "Menos Pontos":
                dadosCalculados.sort(Comparator.comparingInt(d -> d.pontos));
                break;
            case "Mais Gols Marcados":
                dadosCalculados.sort((d1, d2) -> Integer.compare(d2.golsMarcados, d1.golsMarcados));
                break;
            case "Menos Gols Marcados":
                dadosCalculados.sort(Comparator.comparingInt(d -> d.golsMarcados));
                break;
            default:
                dadosCalculados.sort(Comparator.comparing(d -> d.pais));
                break;
        }

        modeloDesempenho.setRowCount(0);
        int posicao = 1;
        for (LinhaDesempenho d : dadosCalculados) {
            modeloDesempenho.addRow(new Object[]{
                    filter.equals("Padrão (Alfabética)") ? "N/A" : posicao + "º",
                    d.pais,
                    d.pontos + " Pts",
                    d.golsMarcados + " Gols"
            });
            posicao++;
        }
    }

    private void calcularPartidasEspecificas() {
        String busca = txtPesquisaSelecaoPartida.getText().trim();
        if (busca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome de um país para pesquisar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Partida> todasPartidas = app.getPartidas();
        long contador = todasPartidas.stream()
                .filter(p -> p.getSelecaoA().equalsIgnoreCase(busca) ||
                        p.getSelecaoB().equalsIgnoreCase(busca))
                .count();

        lblPartidasSelecaoEspecifica.setText("Partidas desta Seleção: " + contador);
    }



    private JPanel criarCardBranco(String tituloCard) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(225, 225, 225), 1),
                        new EmptyBorder(12, 15, 12, 15)
                ), tituloCard, 0, 0, new Font("Segoe UI", Font.BOLD, 13), new Color(25, 118, 210)
        ));
        return card;
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
    //desempenho na tabela
    private static class LinhaDesempenho {
        String pais;
        int pontos = 0;
        int golsMarcados = 0;
    }
}
