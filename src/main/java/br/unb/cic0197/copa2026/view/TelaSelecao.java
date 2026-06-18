package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.controller.SelecaoController;
import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Selecao;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TelaSelecao extends JPanel {

    private final CopaApp app;
    private final SelecaoController selecaoController;
    private Selecao selecaoSelecionada;

    private JTextField txtPais;
    private JTextField txtTecnico;
    private JComboBox<String> comboGrupo;
    private JComboBox<String> comboFiltroGrupo;
    private DefaultTableModel tableModel;
    private JTable table;

    private static final String[] GRUPOS = {
            "Grupo A", "Grupo B", "Grupo C", "Grupo D",
            "Grupo E", "Grupo F", "Grupo G", "Grupo H",
            "Grupo I", "Grupo J", "Grupo K", "Grupo L"
    };

    private static final String[] GRUPOS_FILTRO = {
            "Todos", "Grupo A", "Grupo B", "Grupo C", "Grupo D",
            "Grupo E", "Grupo F", "Grupo G", "Grupo H",
            "Grupo I", "Grupo J", "Grupo K", "Grupo L"
    };

    public TelaSelecao(CopaApp app) {
        this.app = app;
        this.selecaoController = new SelecaoController();

        initComponents();
        atualizarTabela(selecaoController.listarSelecoes());
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(buildHeader(), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);

        centerPanel.add(buildFormPanel(), BorderLayout.NORTH);
        centerPanel.add(buildSearchPanel(), BorderLayout.CENTER);
        centerPanel.add(buildTablePanel(), BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(25, 118, 210));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titulo = new JLabel("Gerenciamento de Seleções");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel subtitulo = new JLabel("Cadastro, edição e consulta de seleções");
        subtitulo.setForeground(Color.WHITE);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(titulo);
        textPanel.add(subtitulo);

        panel.add(textPanel, BorderLayout.WEST);
        return panel;
    }

    private JPanel buildFormPanel() {
        JPanel container = new JPanel(new BorderLayout(10, 10));
        container.setOpaque(false);
        container.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                        "Cadastro / Edição de Seleção"));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        txtPais = new JTextField();
        txtTecnico = new JTextField();
        comboGrupo = new JComboBox<>(GRUPOS);

        txtPais.setFont(fieldFont);
        txtTecnico.setFont(fieldFont);
        comboGrupo.setFont(fieldFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("País"), gbc);

        gbc.gridx = 1;
        formPanel.add(txtPais, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Grupo"), gbc);

        gbc.gridx = 3;
        formPanel.add(comboGrupo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Técnico"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        formPanel.add(txtTecnico, gbc);
        gbc.gridwidth = 1;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton btnNovo = new JButton("Novo");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnVoltar = new JButton("Voltar");

        estilizarBotao(btnNovo, new Color(149, 165, 166));
        estilizarBotao(btnSalvar, new Color(46, 204, 113));
        estilizarBotao(btnExcluir, new Color(231, 76, 60));
        estilizarBotao(btnVoltar, new Color(52, 152, 219));

        btnNovo.addActionListener(e -> limparCampos());
        btnSalvar.addActionListener(e -> salvarSelecao());
        btnExcluir.addActionListener(e -> excluirSelecao());
        btnVoltar.addActionListener(e -> app.mostrarTela("menu"));

        buttonPanel.add(btnNovo);
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnVoltar);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        formPanel.add(buttonPanel, gbc);

        container.add(formPanel, BorderLayout.CENTER);
        return container;
    }

    private JPanel buildSearchPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                        "Filtro de Seleções"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        comboFiltroGrupo = new JComboBox<>(GRUPOS_FILTRO);

        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpar = new JButton("Limpar Filtro");

        estilizarBotao(btnBuscar, new Color(25, 118, 210));
        estilizarBotao(btnLimpar, new Color(149, 165, 166));

        btnBuscar.addActionListener(e -> buscarSelecoes());
        btnLimpar.addActionListener(e -> {
            comboFiltroGrupo.setSelectedIndex(0);
            atualizarTabela(selecaoController.listarSelecoes());
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Grupo"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 2.0;
        panel.add(comboFiltroGrupo, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        panel.add(btnBuscar, gbc);

        gbc.gridx = 3;
        panel.add(btnLimpar, gbc);

        return panel;
    }

    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                        "Seleções Cadastradas"));

        tableModel = new DefaultTableModel(
                new Object[] { "ID", "País", "Grupo", "Técnico" },
                0);

        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(this::selecionarSelecaoNaTabela);

        table.setRowHeight(34);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(25, 118, 210));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(230, 230, 230));
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 36));
        table.getTableHeader().setReorderingAllowed(false);

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(0, 250));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void salvarSelecao() {
        String pais = txtPais.getText().trim();
        String grupo = (String) comboGrupo.getSelectedItem();
        String tecnico = txtTecnico.getText().trim();

        try {
            if (selecaoSelecionada == null) {
                Selecao selecao = new Selecao(app.gerarId(), pais, grupo, tecnico);
                selecaoController.salvarSelecao(selecao);
                JOptionPane.showMessageDialog(this,
                        "Seleção cadastrada com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                Selecao selecaoEditada = new Selecao(
                        selecaoSelecionada.getId(),
                        pais,
                        grupo,
                        tecnico);
                selecaoEditada.setJogadores(selecaoSelecionada.getJogadores());
                selecaoController.atualizarSelecao(selecaoEditada);
                JOptionPane.showMessageDialog(this,
                        "Seleção atualizada com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            app.recarregarSelecoes();
            atualizarTabela(selecaoController.listarSelecoes());
            limparCampos();

        } catch (Copa2026Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Erro de validação",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void excluirSelecao() {
        if (selecaoSelecionada == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma seleção na tabela para excluir.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir a seleção selecionada?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                selecaoController.removerSelecao(selecaoSelecionada);
                app.recarregarSelecoes();
                atualizarTabela(selecaoController.listarSelecoes());
                limparCampos();
                JOptionPane.showMessageDialog(this,
                        "Seleção excluída com sucesso.",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Copa2026Exception ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Erro de exclusão",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarSelecoes() {
        String grupo = (String) comboFiltroGrupo.getSelectedItem();

        List<Selecao> resultado;
        if (grupo == null || grupo.equals("Todos")) {
            resultado = selecaoController.listarSelecoes();
        } else {
            resultado = selecaoController.buscarSelecoesPorGrupo(grupo);
        }

        atualizarTabela(resultado);
    }

    private void selecionarSelecaoNaTabela(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            return;
        }

        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        String id = (String) tableModel.getValueAt(selectedRow, 0);
        buscarSelecaoPorId(id).ifPresent(this::carregarSelecaoNoFormulario);
    }

    private Optional<Selecao> buscarSelecaoPorId(String id) {
        for (Selecao selecao : selecaoController.listarSelecoes()) {
            if (selecao.getId().equals(id)) {
                return Optional.of(selecao);
            }
        }
        return Optional.empty();
    }

    private void carregarSelecaoNoFormulario(Selecao selecao) {
        selecaoSelecionada = selecao;
        txtPais.setText(selecao.getPais());
        comboGrupo.setSelectedItem(selecao.getGrupo());
        txtTecnico.setText(selecao.getTecnico());
    }

    private void atualizarTabela(List<Selecao> selecoes) {
        List<Selecao> ordenadas = new ArrayList<>(selecoes);
        ordenadas.sort(
                Comparator.comparing(Selecao::getGrupo)
                        .thenComparing(Selecao::getPais, String.CASE_INSENSITIVE_ORDER));

        tableModel.setRowCount(0);
        for (Selecao selecao : ordenadas) {
            tableModel.addRow(new Object[] {
                    selecao.getId(),
                    selecao.getPais(),
                    selecao.getGrupo(),
                    selecao.getTecnico()
            });
        }
    }

    private void limparCampos() {
        selecaoSelecionada = null;
        txtPais.setText("");
        txtTecnico.setText("");
        comboGrupo.setSelectedIndex(0);
        if (table != null) {
            table.clearSelection();
        }
    }

    private void estilizarBotao(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
    }
}
