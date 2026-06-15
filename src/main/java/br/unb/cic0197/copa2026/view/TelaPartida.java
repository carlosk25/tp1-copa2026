package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.enums.FaseCompeticao;
import br.unb.cic0197.copa2026.enums.StatusPartida;
import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Arbitro;
import br.unb.cic0197.copa2026.model.Estadio;
import br.unb.cic0197.copa2026.model.Partida;
import br.unb.cic0197.copa2026.model.ResultadoPartida;
import br.unb.cic0197.copa2026.model.Selecao;
import br.unb.cic0197.copa2026.repository.ArbitroRepository;
import br.unb.cic0197.copa2026.repository.EstadioRepository;
import br.unb.cic0197.copa2026.repository.SelecaoRepository;
import br.unb.cic0197.copa2026.controller.PartidaController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TelaPartida extends JPanel {
    private final CopaApp app;
    private final PartidaController partidaController;
    private final SelecaoRepository selecaoRepository;
    private final EstadioRepository estadioRepository;
    private final ArbitroRepository arbitroRepository;
    private Partida partidaSelecionada;

    private JTextField txtData;
    private JTextField txtHorario;
    private JComboBox<String> comboEstadio;
    private JComboBox<String> comboSelecaoA;
    private JComboBox<String> comboSelecaoB;
    private JComboBox<String> comboArbitro;
    private JComboBox<FaseCompeticao> comboFase;
    private JComboBox<StatusPartida> comboStatus;
    private JTextField txtPlacarA;
    private JTextField txtPlacarB;
    private JTextArea txtEventos;

    private JComboBox<String> comboBuscaSelecao;
    private JComboBox<Object> comboBuscaFase;
    private JComboBox<String> comboBuscaArbitro;
    private JTextField txtBuscaData;
    private DefaultTableModel tableModel;
    private JTable table;

    public TelaPartida(CopaApp app) {
        this.app = app;
        this.partidaController = new PartidaController();
        this.selecaoRepository = new SelecaoRepository();
        this.estadioRepository = new EstadioRepository();
        this.arbitroRepository = new ArbitroRepository();
        initComponents();
        atualizarTabela(partidaController.listarPartidas());
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

        JLabel titulo = new JLabel("Gerenciamento de Partidas");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel subtitulo = new JLabel("Cadastro, edição e consulta de partidas");
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

        container.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 220, 220)),
                        "Cadastro / Edição de Partida"));

        JPanel formPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        txtData = new JTextField();
        txtHorario = new JTextField();

        txtData.setFont(fieldFont);
        txtHorario.setFont(fieldFont);

        List<String> estadioNomes = new ArrayList<>();
        try {
            estadioNomes = estadioRepository.carregar()
                    .stream()
                    .map(Estadio::getNome)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar estádios", e);
        }

        if (estadioNomes.isEmpty()) {
            estadioNomes = List.of("Sem estádios");
        }

        comboEstadio = new JComboBox<>(
                estadioNomes.toArray(String[]::new));

        List<String> selecaoNomes = new ArrayList<>();

        try {
            selecaoNomes = selecaoRepository.carregar()
                    .stream()
                    .map(Selecao::getPais)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar seleções", e);
        }

        if (selecaoNomes.isEmpty()) {
            selecaoNomes = List.of("Sem seleções");
        }

        comboSelecaoA = new JComboBox<>(
                selecaoNomes.toArray(String[]::new));

        comboSelecaoB = new JComboBox<>(
                selecaoNomes.toArray(String[]::new));

        List<String> arbitroNomes = new ArrayList<>();

        try {
            arbitroNomes = arbitroRepository.carregar()
                    .stream()
                    .map(Arbitro::getNome)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar árbitros", e);
        }

        if (arbitroNomes.isEmpty()) {
            arbitroNomes = List.of("Sem árbitros");
        } else {
            arbitroNomes.add(0, "Nenhum");
        }

        comboArbitro = new JComboBox<>(
                arbitroNomes.toArray(String[]::new));

        comboFase = new JComboBox<>(FaseCompeticao.values());
        comboStatus = new JComboBox<>(StatusPartida.values());

        txtPlacarA = new JTextField(3);
        txtPlacarB = new JTextField(3);

        txtEventos = new JTextArea(5, 30);
        txtEventos.setLineWrap(true);
        txtEventos.setWrapStyleWord(true);
        txtEventos.setFont(fieldFont);

        // =====================================================
        // DATA E HORÁRIO
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Data"), gbc);

        gbc.gridx = 1;
        formPanel.add(txtData, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Horário"), gbc);

        gbc.gridx = 3;
        formPanel.add(txtHorario, gbc);

        // =====================================================
        // ESTÁDIO
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Estádio"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        formPanel.add(comboEstadio, gbc);
        gbc.gridwidth = 1;

        // =====================================================
        // EQUIPES
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Seleção A"), gbc);

        gbc.gridx = 1;
        formPanel.add(comboSelecaoA, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Seleção B"), gbc);

        gbc.gridx = 3;
        formPanel.add(comboSelecaoB, gbc);

        // =====================================================
        // ÁRBITRO
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Árbitro"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        formPanel.add(comboArbitro, gbc);
        gbc.gridwidth = 1;

        // =====================================================
        // FASE E STATUS
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Fase"), gbc);

        gbc.gridx = 1;
        formPanel.add(comboFase, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Status"), gbc);

        gbc.gridx = 3;
        formPanel.add(comboStatus, gbc);

        // =====================================================
        // PLACAR
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Resultado"), gbc);

        JPanel placarPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 0));

        JLabel xLabel = new JLabel("X");
        xLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 18));

        txtPlacarA.setHorizontalAlignment(
                JTextField.CENTER);

        txtPlacarB.setHorizontalAlignment(
                JTextField.CENTER);

        placarPanel.add(txtPlacarA);
        placarPanel.add(xLabel);
        placarPanel.add(txtPlacarB);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        formPanel.add(placarPanel, gbc);
        gbc.gridwidth = 1;

        // =====================================================
        // EVENTOS
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.NORTH;

        formPanel.add(new JLabel("Eventos"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;

        formPanel.add(
                new JScrollPane(txtEventos),
                gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        // =====================================================
        // BOTÕES
        // =====================================================

        JPanel buttonPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        10,
                        0));

        JButton btnSalvar = new JButton("Salvar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnNovo = new JButton("Novo");
        JButton btnVoltar = new JButton("Voltar");

        btnSalvar.setBackground(
                new Color(46, 204, 113));

        btnExcluir.setBackground(
                new Color(231, 76, 60));

        btnNovo.setBackground(
                new Color(149, 165, 166));

        btnVoltar.setBackground(
                new Color(52, 152, 219));

        for (JButton b : new JButton[] {
                btnSalvar,
                btnExcluir,
                btnNovo,
                btnVoltar }) {

            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            13));
        }

        btnSalvar.addActionListener(
                e -> salvarPartida());

        btnExcluir.addActionListener(
                e -> excluirPartida());

        btnNovo.addActionListener(
                e -> limparCampos());

        btnVoltar.addActionListener(
                e -> app.mostrarTela("menu"));

        buttonPanel.add(btnNovo);
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnVoltar);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 4;

        formPanel.add(buttonPanel, gbc);

        container.add(formPanel, BorderLayout.CENTER);

        return container;
    }

    private JPanel buildTablePanel() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 247, 250));

        panel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 220, 220)),
                        "Partidas Cadastradas"));

        tableModel = new DefaultTableModel(
                new Object[] {
                        "ID",
                        "Data",
                        "Horário",
                        "Estádio",
                        "Seleção A",
                        "Seleção B",
                        "Árbitro",
                        "Fase",
                        "Status",
                        "Placar",
                        "Eventos"
                }, 0);

        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel()
                .addListSelectionListener(this::selecionarPartidaNaTabela);

        // Aparência da tabela
        table.setRowHeight(34);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        table.setSelectionBackground(
                new Color(25, 118, 210));

        table.setSelectionForeground(Color.WHITE);

        table.setGridColor(
                new Color(230, 230, 230));

        table.setShowVerticalLines(false);

        table.setIntercellSpacing(new Dimension(0, 1));

        // Cabeçalho
        table.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13));

        table.getTableHeader().setPreferredSize(
                new Dimension(0, 36));

        table.getTableHeader().setReorderingAllowed(false);

        // Oculta ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(
                BorderFactory.createEmptyBorder());

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildSearchPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Partidas Cadastradas"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboBuscaSelecao = new JComboBox<>();
        comboBuscaSelecao.addItem("Todas");
        try {
            selecaoRepository.carregar().stream()
                    .map(Selecao::getPais)
                    .forEach(comboBuscaSelecao::addItem);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar seleções para busca", e);
        }

        comboBuscaFase = new JComboBox<>();
        comboBuscaFase.addItem("Todas");
        for (FaseCompeticao fase : FaseCompeticao.values()) {
            comboBuscaFase.addItem(fase);
        }

        comboBuscaArbitro = new JComboBox<>();
        comboBuscaArbitro.addItem("Todos");
        try {
            arbitroRepository.carregar().stream()
                    .map(Arbitro::getNome)
                    .forEach(comboBuscaArbitro::addItem);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar árbitros para busca", e);
        }

        txtBuscaData = new JTextField();

        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpar = new JButton("Limpar Filtro");

        btnBuscar.addActionListener(e -> buscarPartidas());
        btnLimpar.addActionListener(e -> {
            comboBuscaSelecao.setSelectedIndex(0);
            comboBuscaFase.setSelectedIndex(0);
            comboBuscaArbitro.setSelectedIndex(0);
            txtBuscaData.setText("");
            atualizarTabela(partidaController.listarPartidas());
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Seleção:"), gbc);
        gbc.gridx = 1;
        panel.add(comboBuscaSelecao, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Fase:"), gbc);
        gbc.gridx = 3;
        panel.add(comboBuscaFase, gbc);

        gbc.gridx = 4;
        panel.add(new JLabel("Árbitro:"), gbc);
        gbc.gridx = 5;
        panel.add(comboBuscaArbitro, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Data:"), gbc);
        gbc.gridx = 1;
        panel.add(txtBuscaData, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 6;
        gbc.anchor = GridBagConstraints.EAST;
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.add(btnBuscar);
        buttons.add(btnLimpar);
        panel.add(buttons, gbc);

        return panel;
    }

    private void salvarPartida() {
        String data = txtData.getText().trim();
        String horario = txtHorario.getText().trim();
        String estadio = (String) comboEstadio.getSelectedItem();
        String selecaoA = (String) comboSelecaoA.getSelectedItem();
        String selecaoB = (String) comboSelecaoB.getSelectedItem();
        String arbitroNome = (String) comboArbitro.getSelectedItem();
        FaseCompeticao fase = (FaseCompeticao) comboFase.getSelectedItem();
        StatusPartida status = (StatusPartida) comboStatus.getSelectedItem();
        String golsA = txtPlacarA.getText().trim();
        String golsB = txtPlacarB.getText().trim();
        String eventos = txtEventos.getText().trim();

        if (data.isEmpty() || horario.isEmpty() || estadio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha data, horário e estádio.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selecaoA.equals(selecaoB)) {
            JOptionPane.showMessageDialog(this, "Seleção A e Seleção B não podem ser iguais.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        ResultadoPartida resultado = null;
        if (!golsA.isEmpty() || !golsB.isEmpty()) {
            try {
                int golsAInt = golsA.isEmpty() ? 0 : Integer.parseInt(golsA);
                int golsBInt = golsB.isEmpty() ? 0 : Integer.parseInt(golsB);
                resultado = new ResultadoPartida(golsAInt, golsBInt, eventos);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Informe placar numérico válido.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Arbitro arbitro = null;
        if (arbitroNome != null && !arbitroNome.isBlank() && !arbitroNome.equals("Nenhum")
                && !arbitroNome.equals("Sem árbitros")) {
            try {
                arbitro = arbitroRepository.buscarPorNome(arbitroNome);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao buscar árbitro", e);
            }
        }

        try {
            if (partidaSelecionada == null) {
                Partida partida = new Partida(data, horario, estadio, selecaoA, selecaoB, fase, status);
                partida.setResultado(resultado);
                partida.setArbitro(arbitro);
                partidaController.salvarPartida(partida);
                JOptionPane.showMessageDialog(this, "Partida cadastrada com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                partidaSelecionada.setData(data);
                partidaSelecionada.setHorario(horario);
                partidaSelecionada.setEstadio(estadio);
                partidaSelecionada.setSelecaoA(selecaoA);
                partidaSelecionada.setSelecaoB(selecaoB);
                partidaSelecionada.setArbitro(arbitro);
                partidaSelecionada.setFase(fase);
                partidaSelecionada.setStatus(status);
                partidaSelecionada.setResultado(resultado);
                partidaController.atualizarPartida(partidaSelecionada);
                JOptionPane.showMessageDialog(this, "Partida atualizada com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            app.recarregarPartidas();
            atualizarTabela(partidaController.listarPartidas());
            limparCampos();
        } catch (Copa2026Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirPartida() {
        if (partidaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma partida na tabela para excluir.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir a partida selecionada?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                partidaController.removerPartida(partidaSelecionada);
                app.recarregarPartidas();
                atualizarTabela(partidaController.listarPartidas());
                limparCampos();
                JOptionPane.showMessageDialog(this, "Partida excluída.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Copa2026Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de exclusão", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarPartidas() {
        String selecao = (String) comboBuscaSelecao.getSelectedItem();
        FaseCompeticao fase = comboBuscaFase.getSelectedIndex() <= 0 ? null
                : (FaseCompeticao) comboBuscaFase.getSelectedItem();
        String arbitro = (String) comboBuscaArbitro.getSelectedItem();
        String data = txtBuscaData.getText().trim();
        List<Partida> resultado = partidaController.buscarPartidas(selecao, fase, data, arbitro);
        atualizarTabela(resultado);
    }

    private void selecionarPartidaNaTabela(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            return;
        }

        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        String id = (String) tableModel.getValueAt(selectedRow, 0);
        partidaController.obterPartidaPorId(id).ifPresent(this::carregarPartidaNoFormulario);
    }

    private void carregarPartidaNoFormulario(Partida partida) {
        partidaSelecionada = partida;
        txtData.setText(partida.getData());
        txtHorario.setText(partida.getHorario());
        comboEstadio.setSelectedItem(partida.getEstadio());
        comboSelecaoA.setSelectedItem(partida.getSelecaoA());
        comboSelecaoB.setSelectedItem(partida.getSelecaoB());
        comboFase.setSelectedItem(partida.getFase());
        comboStatus.setSelectedItem(partida.getStatus());
        comboArbitro.setSelectedItem(partida.getArbitro() != null ? partida.getArbitro().getNome() : "Nenhum");

        if (partida.getResultado() != null) {
            txtPlacarA.setText(String.valueOf(partida.getResultado().getGolsA()));
            txtPlacarB.setText(String.valueOf(partida.getResultado().getGolsB()));
            txtEventos.setText(partida.getResultado().getEventos());
        } else {
            txtPlacarA.setText("");
            txtPlacarB.setText("");
            txtEventos.setText("");
        }
    }

    private void atualizarTabela(List<Partida> partidas) {
        tableModel.setRowCount(0);
        for (Partida partida : partidas) {
            String eventos = partida.getResultado() == null ? "" : partida.getResultado().getEventos();
            tableModel.addRow(new Object[] {
                    partida.getId(),
                    partida.getData(),
                    partida.getHorario(),
                    partida.getEstadio(),
                    partida.getSelecaoA(),
                    partida.getSelecaoB(),
                    partida.getArbitroNome(),
                    partida.getFase(),
                    partida.getStatus(),
                    partida.getPlacarFormatado(),
                    eventos
            });
        }
    }

    private void limparCampos() {
        partidaSelecionada = null;
        txtData.setText("");
        txtHorario.setText("");
        comboEstadio.setSelectedIndex(0);
        comboSelecaoA.setSelectedIndex(0);
        comboSelecaoB.setSelectedIndex(Math.min(1, comboSelecaoB.getItemCount() - 1));
        comboArbitro.setSelectedIndex(0);
        comboFase.setSelectedIndex(0);
        comboStatus.setSelectedIndex(0);
        txtPlacarA.setText("");
        txtPlacarB.setText("");
        txtEventos.setText("");
        table.clearSelection();
    }
}
