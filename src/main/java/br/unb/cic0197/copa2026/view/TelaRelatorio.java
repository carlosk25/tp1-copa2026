package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.controller.RelatorioController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class TelaRelatorio extends JPanel {
    private CopaApp app;
    private RelatorioController controller;

    private JTable tabelaRelatorio;
    private DefaultTableModel modeloRelatorio;
    private JTextField txtPesquisa;
    private TableRowSorter<DefaultTableModel> sorter;

    public TelaRelatorio(CopaApp app) {
        this.app = app;
        this.controller = new RelatorioController();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

       
        JPanel painelTopo = new JPanel(new BorderLayout(0, 15));
        painelTopo.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("RELATÓRIOS GERAIS", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(20, 80, 20)); // Verde escuro elegante para o título
        painelTopo.add(lblTitulo, BorderLayout.NORTH);

        
        JPanel painelBusca = new JPanel(new BorderLayout(10, 0));
        painelBusca.setBackground(Color.WHITE);
        painelBusca.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JLabel lblIcone = new JLabel("🔍 Filtrar Dados: ");
        lblIcone.setFont(new Font("Arial", Font.BOLD, 12));

        txtPesquisa = new JTextField();
        txtPesquisa.setFont(new Font("Arial", Font.PLAIN, 13));
        txtPesquisa.setBorder(BorderFactory.createEmptyBorder()); // Remove borda interna padrão feia

        painelBusca.add(lblIcone, BorderLayout.WEST);
        painelBusca.add(txtPesquisa, BorderLayout.CENTER);
        painelTopo.add(painelBusca, BorderLayout.SOUTH);

        add(painelTopo, BorderLayout.NORTH);

        String[] colunas = {"Tipo de Registro", "Confronto / Nome", "Fase / N° Camisa", "Grupo / Posição", "Data Hora / Idade"};

        modeloRelatorio = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabelaRelatorio = new JTable(modeloRelatorio);
        tabelaRelatorio.setRowHeight(25); // Linhas confortáveis e elegantes
        tabelaRelatorio.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaRelatorio.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabelaRelatorio.getTableHeader().setReorderingAllowed(false);

       
        sorter = new TableRowSorter<>(modeloRelatorio);
        tabelaRelatorio.setRowSorter(sorter);

       
        txtPesquisa.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
        });

        JScrollPane scrollPane = new JScrollPane(tabelaRelatorio);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        add(scrollPane, BorderLayout.CENTER);

        
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        painelRodape.setBackground(Color.WHITE);

        JButton btnVoltar = createStyledButton("← Voltar ao Menu", new Color(70, 70, 70));
        btnVoltar.addActionListener(e -> app.mostrarTela("menu"));

        painelRodape.add(btnVoltar);
        add(painelRodape, BorderLayout.SOUTH);

        
        atualizarDadosDoRelatorio();
    }

    private void filtrar() {
        String texto = txtPesquisa.getText().trim();
        if (texto.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        }
    }

    public void atualizarDadosDoRelatorio() {
        modeloRelatorio.setRowCount(0);

        if (controller != null) {
            java.util.List<Object[]> linhas = controller.obterDadosConsolidados();
            for (Object[] linha : linhas) {
                modeloRelatorio.addRow(linha);
            }
        }

        this.revalidate();
        this.repaint();
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        return button;
    }
}
