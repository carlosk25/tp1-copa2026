package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.enums.StatusJogador;
import br.unb.cic0197.copa2026.model.Jogador;
import br.unb.cic0197.copa2026.model.Selecao;
import br.unb.cic0197.copa2026.repository.JogadorRepository;
import br.unb.cic0197.copa2026.repository.SelecaoRepository;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.UUID;

public class TelaJogadores extends JPanel {
    private final CopaApp app;
    private final JogadorRepository repository;
    private final SelecaoRepository selecaoRepository;
    private Jogador jogadorSelecionado;

    private JTextField txtNome;
    private JComboBox<String> comboPosicao;
    private JTextField txtNumero;
    private JTextField txtIdade;
    private JComboBox<String> comboSelecao;
    private JComboBox<StatusJogador> comboStatus;

    private JComboBox<String> comboFiltroPosicao;
    private JComboBox<String> comboFiltroSelecao;
    private JComboBox<StatusJogador> comboFiltroStatus;
    private JTable table;
    private DefaultTableModel tableModel;

    public TelaJogadores(CopaApp app) {
        this.app = app;
        this.selecaoRepository = new SelecaoRepository();
        this.repository = new JogadorRepository(selecaoRepository);
        initComponents();
        atualizarTabela(repository.findAll());
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildSearchPanel(), BorderLayout.SOUTH);
    }

    private JPanel buildFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastro de Jogadores"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNome = new JTextField();
        comboPosicao = new JComboBox<>(new String[]{"Goleiro", "Lateral Direito", "Lateral Esquerdo", "Zagueiro", "Volante", "Meio-Campo", "Atacante", "Centroavante"});
        txtNumero = new JTextField();
        txtIdade = new JTextField();
        comboSelecao = new JComboBox<>(selecaoRepository.findAll().stream().map(Selecao::getPais).toArray(String[]::new));
        comboStatus = new JComboBox<>(StatusJogador.values());

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtNome, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Posição:"), gbc);
        gbc.gridx = 3;
        formPanel.add(comboPosicao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Número:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtNumero, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Idade:"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtIdade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Seleção:"), gbc);
        gbc.gridx = 1;
        formPanel.add(comboSelecao, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 3;
        formPanel.add(comboStatus, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton btnNovo = new JButton("Novo");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnVoltar = new JButton("Voltar");

        btnNovo.addActionListener(e -> limparCampos());
        btnSalvar.addActionListener(e -> salvarJogador());
        btnExcluir.addActionListener(e -> excluirJogador());
        btnVoltar.addActionListener(e -> app.mostrarTela("menu"));

        buttonPanel.add(btnNovo);
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnVoltar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Jogadores Cadastrados"));

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Posição", "Número", "Idade", "Seleção", "Status"}, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(this::selecionarJogadorNaTabela);

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildSearchPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Filtrar Jogadores"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboFiltroPosicao = new JComboBox<>(new String[]{"Todas", "Goleiro", "Lateral Direito", "Lateral Esquerdo", "Zagueiro", "Volante", "Meio-Campo", "Atacante", "Centroavante"});
        comboFiltroSelecao = new JComboBox<>();
        comboFiltroSelecao.addItem("Todas");
        selecaoRepository.findAll().stream().map(Selecao::getPais).forEach(comboFiltroSelecao::addItem);
        comboFiltroStatus = new JComboBox<>();
        comboFiltroStatus.addItem(null);
        for (StatusJogador status : StatusJogador.values()) {
            comboFiltroStatus.addItem(status);
        }

        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpar = new JButton("Limpar Filtro");

        btnBuscar.addActionListener(e -> buscarJogadores());
        btnLimpar.addActionListener(e -> {
            comboFiltroPosicao.setSelectedIndex(0);
            comboFiltroSelecao.setSelectedIndex(0);
            comboFiltroStatus.setSelectedIndex(0);
            atualizarTabela(repository.findAll());
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Posição:"), gbc);
        gbc.gridx = 1;
        panel.add(comboFiltroPosicao, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Seleção:"), gbc);
        gbc.gridx = 3;
        panel.add(comboFiltroSelecao, gbc);

        gbc.gridx = 4;
        panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 5;
        panel.add(comboFiltroStatus, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 6;
        gbc.anchor = GridBagConstraints.EAST;
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.add(btnBuscar);
        buttons.add(btnLimpar);
        panel.add(buttons, gbc);

        return panel;
    }

    private void selecionarJogadorNaTabela(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            return;
        }

        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        String id = (String) tableModel.getValueAt(selectedRow, 0);
        repository.findById(id).ifPresent(this::carregarJogadorNoFormulario);
    }

    private void carregarJogadorNoFormulario(Jogador jogador) {
        this.jogadorSelecionado = jogador;
        txtNome.setText(jogador.getNome());
        comboPosicao.setSelectedItem(jogador.getPosicao());
        txtNumero.setText(String.valueOf(jogador.getNumero()));
        txtIdade.setText(String.valueOf(jogador.getIdade()));
        comboSelecao.setSelectedItem(jogador.getSelecao() != null ? jogador.getSelecao().getPais() : "");
        comboStatus.setSelectedItem(jogador.getStatus());
    }

    private void salvarJogador() {
        String nome = txtNome.getText().trim();
        String posicao = (String) comboPosicao.getSelectedItem();
        String numeroTexto = txtNumero.getText().trim();
        String idadeTexto = txtIdade.getText().trim();
        String selecaoPais = (String) comboSelecao.getSelectedItem();
        StatusJogador status = (StatusJogador) comboStatus.getSelectedItem();

        if (nome.isEmpty() || numeroTexto.isEmpty() || idadeTexto.isEmpty() || selecaoPais == null || selecaoPais.isBlank()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos do jogador.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int numero;
        int idade;
        try {
            numero = Integer.parseInt(numeroTexto);
            idade = Integer.parseInt(idadeTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número e idade devem ser valores numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Selecao selecao = selecaoRepository.findAll().stream()
                .filter(s -> s.getPais().equalsIgnoreCase(selecaoPais))
                .findFirst()
                .orElse(null);

        if (selecao == null) {
            JOptionPane.showMessageDialog(this, "Seleção inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (jogadorSelecionado == null) {
            Jogador jogador = new Jogador(UUID.randomUUID().toString(), nome, posicao, numero, idade, status, selecao);
            repository.add(jogador);
            JOptionPane.showMessageDialog(this, "Jogador cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            jogadorSelecionado.setNome(nome);
            jogadorSelecionado.setPosicao(posicao);
            jogadorSelecionado.setNumero(numero);
            jogadorSelecionado.setIdade(idade);
            jogadorSelecionado.setSelecao(selecao);
            jogadorSelecionado.setStatus(status);
            repository.update(jogadorSelecionado);
            JOptionPane.showMessageDialog(this, "Jogador atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }

        atualizarTabela(repository.findAll());
        limparCampos();
    }

    private void excluirJogador() {
        if (jogadorSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um jogador para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir o jogador selecionado?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            repository.delete(jogadorSelecionado);
            atualizarTabela(repository.findAll());
            limparCampos();
        }
    }

    private void buscarJogadores() {
        String posicao = (String) comboFiltroPosicao.getSelectedItem();
        String selecao = (String) comboFiltroSelecao.getSelectedItem();
        StatusJogador status = (StatusJogador) comboFiltroStatus.getSelectedItem();
        atualizarTabela(repository.search(selecao, posicao, status));
    }

    private void atualizarTabela(List<Jogador> jogadores) {
        tableModel.setRowCount(0);
        for (Jogador jogador : jogadores) {
            tableModel.addRow(new Object[]{
                    jogador.getId(),
                    jogador.getNome(),
                    jogador.getPosicao(),
                    jogador.getNumero(),
                    jogador.getIdade(),
                    jogador.getSelecao() != null ? jogador.getSelecao().getPais() : "",
                    jogador.getStatus()
            });
        }
    }

    private void limparCampos() {
        jogadorSelecionado = null;
        txtNome.setText("");
        comboPosicao.setSelectedIndex(0);
        txtNumero.setText("");
        txtIdade.setText("");
        comboSelecao.setSelectedIndex(0);
        comboStatus.setSelectedIndex(0);
        table.clearSelection();
    }
}
