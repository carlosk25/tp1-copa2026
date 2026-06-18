package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.controller.JogadorController;
import br.unb.cic0197.copa2026.controller.SelecaoController;
import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Jogador;
import br.unb.cic0197.copa2026.model.Selecao;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * tela de gerenciamento de jogadores.
 * responsável apenas pela interface: os dados são enviados ao controller,
 * e as validações ficam no Service.
 */
public class TelaJogadores extends JPanel {

    private final CopaApp app;
    private final JogadorController jogadorController;
    private final SelecaoController selecaoController;
    private Jogador jogadorSelecionado;

    private JTextField txtNome;
    private JComboBox<String> comboPosicao;
    private JTextField txtNumero;
    private JTextField txtIdade;
    private JComboBox<String> comboSelecao;
    private JComboBox<Jogador.StatusJogador> comboStatus;

    private JComboBox<String> comboFiltroPosicao;
    private JComboBox<String> comboFiltroSelecao;
    private JTextField txtFiltroNumero;
    private JTextField txtFiltroIdade;
    private JComboBox<String> comboFiltroStatus;

    private DefaultTableModel tableModel;
    private JTable table;

    // posições disponíveis no cadastro de jogadores.
    private static final String[] POSICOES = {
            "Goleiro",
            "Lateral Esquerdo",
            "Lateral Direito",
            "Zagueiro",
            "Volante",
            "Meia-Central",
            "Meia-Direito",
            "Meia-Atacante",
            "Ponta Esquerda",
            "Ponta Direita",
            "Atacante",
            "Segundo Atacante"
    };

    // filtro possui a opção "Todas" para permitir consulta sem restringir posição.
    private static final String[] POSICOES_FILTRO = {
            "Todas",
            "Goleiro",
            "Lateral Esquerdo",
            "Lateral Direito",
            "Zagueiro",
            "Volante",
            "Meia-Central",
            "Meia-Direito",
            "Meia-Atacante",
            "Ponta Esquerda",
            "Ponta Direita",
            "Atacante",
            "Segundo Atacante"
    };

    public TelaJogadores(CopaApp app) {
        this.app = app;
        this.jogadorController = new JogadorController();
        this.selecaoController = new SelecaoController();

        initComponents();
        atualizarTabela(jogadorController.listarJogadores());
    }

    // monta a tela em três partes: formulário, filtros e tabela.
    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(buildHeader(), BorderLayout.NORTH);

        // gridBagLayout evita que a tabela engula os campos de filtro em telas menores.
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 8, 0);

        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(buildFormPanel(), gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(buildSearchPanel(), gbc);

        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        centerPanel.add(buildTablePanel(), gbc);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(25, 118, 210));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titulo = new JLabel("Gerenciamento de Jogadores");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel subtitulo = new JLabel("Cadastro, edição e consulta de jogadores por seleção");
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

    // formulário usado tanto para cadastrar quanto para editar jogadores.
    private JPanel buildFormPanel() {
        JPanel container = new JPanel(new BorderLayout(10, 10));
        container.setOpaque(false);
        container.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                        "Cadastro / Edição de Jogador"
                )
        );

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        txtNome = new JTextField();
        comboPosicao = new JComboBox<>(POSICOES);
        txtNumero = new JTextField();
        txtIdade = new JTextField();
        comboSelecao = new JComboBox<>();
        comboStatus = new JComboBox<>(Jogador.StatusJogador.values());

        txtNome.setFont(fieldFont);
        comboPosicao.setFont(fieldFont);
        txtNumero.setFont(fieldFont);
        txtIdade.setFont(fieldFont);
        comboSelecao.setFont(fieldFont);
        comboStatus.setFont(fieldFont);

        atualizarComboSelecao(comboSelecao, false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nome"), gbc);

        gbc.gridx = 1;
        formPanel.add(txtNome, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Posição"), gbc);

        gbc.gridx = 3;
        formPanel.add(comboPosicao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Camisa"), gbc);

        gbc.gridx = 1;
        formPanel.add(txtNumero, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Idade"), gbc);

        gbc.gridx = 3;
        formPanel.add(txtIdade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Seleção"), gbc);

        gbc.gridx = 1;
        formPanel.add(comboSelecao, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Status"), gbc);

        gbc.gridx = 3;
        formPanel.add(comboStatus, gbc);

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

        container.add(formPanel, BorderLayout.CENTER);
        return container;
    }

    // painel de consulta com filtros combinados.
    private JPanel buildSearchPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                        "Filtro de Jogadores"
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        comboFiltroPosicao = new JComboBox<>(POSICOES_FILTRO);
        comboFiltroSelecao = new JComboBox<>();
        txtFiltroNumero = new JTextField();
        txtFiltroIdade = new JTextField();
        comboFiltroStatus = new JComboBox<>(new String[]{"Todos", "ATIVO", "LESIONADO", "SUSPENSO"});

        atualizarComboSelecao(comboFiltroSelecao, true);

        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpar = new JButton("Limpar Filtro");
        JButton btnAtualizarSelecoes = new JButton("Atualizar Seleções");

        estilizarBotao(btnBuscar, new Color(25, 118, 210));
        estilizarBotao(btnLimpar, new Color(149, 165, 166));
        estilizarBotao(btnAtualizarSelecoes, new Color(52, 152, 219));

        btnBuscar.addActionListener(e -> buscarJogadores());
        btnLimpar.addActionListener(e -> limparFiltro());
        btnAtualizarSelecoes.addActionListener(e -> {
            atualizarComboSelecao(comboSelecao, false);
            atualizarComboSelecao(comboFiltroSelecao, true);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Posição"), gbc);

        gbc.gridx = 1;
        panel.add(comboFiltroPosicao, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Seleção"), gbc);

        gbc.gridx = 3;
        panel.add(comboFiltroSelecao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Camisa"), gbc);

        gbc.gridx = 1;
        panel.add(txtFiltroNumero, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Idade"), gbc);

        gbc.gridx = 3;
        panel.add(txtFiltroIdade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Status"), gbc);

        gbc.gridx = 1;
        panel.add(comboFiltroStatus, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.EAST;

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setOpaque(false);
        buttons.add(btnAtualizarSelecoes);
        buttons.add(btnBuscar);
        buttons.add(btnLimpar);

        panel.add(buttons, gbc);

        return panel;
    }

    // tabela de listagem. Ao selecionar uma linha, os dados são carregados no formulário.
    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                        "Jogadores Cadastrados"
                )
        );

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Posição", "Camisa", "Idade", "Seleção", "Status"},
                0
        );

        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(this::selecionarJogadorNaTabela);

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
        scrollPane.setPreferredSize(new Dimension(0, 280));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // decide entre cadastro e edição conforme existe ou não jogador selecionado na tabela.
    private void salvarJogador() {
        String nome = txtNome.getText().trim();
        String posicao = (String) comboPosicao.getSelectedItem();
        String numeroTexto = txtNumero.getText().trim();
        String idadeTexto = txtIdade.getText().trim();
        String paisSelecao = (String) comboSelecao.getSelectedItem();
        Jogador.StatusJogador status = (Jogador.StatusJogador) comboStatus.getSelectedItem();

        try {
            if (nome.isBlank() || numeroTexto.isBlank() || idadeTexto.isBlank()
                    || paisSelecao == null || paisSelecao.isBlank()) {
                throw new Copa2026Exception("Preencha todos os campos.");
            }

            Optional<Selecao> selecaoEncontrada = selecaoController.buscarSelecaoPorPais(paisSelecao);

            if (selecaoEncontrada.isEmpty()) {
                throw new Copa2026Exception("Seleção inválida. Atualize as seleções.");
            }

            int numero = Integer.parseInt(numeroTexto);
            int idade = Integer.parseInt(idadeTexto);

            if (jogadorSelecionado == null) {
                Jogador jogador = new Jogador(
                        app.gerarId(),
                        nome,
                        posicao,
                        numero,
                        idade,
                        status,
                        selecaoEncontrada.get()
                );

                jogadorController.salvarJogador(jogador);
                JOptionPane.showMessageDialog(this,
                        "Jogador cadastrado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                Jogador jogadorEditado = new Jogador(
                        jogadorSelecionado.getId(),
                        nome,
                        posicao,
                        numero,
                        idade,
                        status,
                        selecaoEncontrada.get()
                );

                jogadorController.atualizarJogador(jogadorEditado);
                JOptionPane.showMessageDialog(this,
                        "Jogador atualizado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            atualizarTabela(jogadorController.listarJogadores());
            limparCampos();

        } catch (Copa2026Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Erro de validação",
                    JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Camisa e idade devem ser valores numéricos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // exclui o jogador atualmente selecionado na tabela.
    private void excluirJogador() {
        if (jogadorSelecionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um jogador na tabela para excluir.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir o jogador selecionado?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                jogadorController.removerJogador(jogadorSelecionado);
                atualizarTabela(jogadorController.listarJogadores());
                limparCampos();
                JOptionPane.showMessageDialog(this,
                        "Jogador excluído com sucesso.",
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

    // converte os campos de filtro da tela e atualiza a tabela com o resultado.
    private void buscarJogadores() {
        String posicao = (String) comboFiltroPosicao.getSelectedItem();
        String selecao = (String) comboFiltroSelecao.getSelectedItem();
        String statusTexto = (String) comboFiltroStatus.getSelectedItem();
        String numeroTexto = txtFiltroNumero.getText().trim();
        String idadeTexto = txtFiltroIdade.getText().trim();

        try {
            if ("Todas".equalsIgnoreCase(posicao)) {
                posicao = null;
            }

            if ("Todas".equalsIgnoreCase(selecao)) {
                selecao = null;
            }

            Jogador.StatusJogador status = null;
            if (statusTexto != null && !statusTexto.equalsIgnoreCase("Todos")) {
                status = Jogador.StatusJogador.valueOf(statusTexto);
            }

            Integer numero = null;
            if (!numeroTexto.isBlank()) {
                numero = Integer.parseInt(numeroTexto);
            }

            Integer idade = null;
            if (!idadeTexto.isBlank()) {
                idade = Integer.parseInt(idadeTexto);
            }

            List<Jogador> resultado = jogadorController.buscarJogadores(
                    posicao,
                    selecao,
                    status,
                    numero,
                    idade
            );

            atualizarTabela(resultado);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Camisa e idade devem ser números.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparFiltro() {
        comboFiltroPosicao.setSelectedIndex(0);
        comboFiltroSelecao.setSelectedIndex(0);
        comboFiltroStatus.setSelectedIndex(0);
        txtFiltroNumero.setText("");
        txtFiltroIdade.setText("");
        atualizarTabela(jogadorController.listarJogadores());
    }

    // quando o usuário seleciona uma linha, o jogador é localizado pelo ID escondido na tabela.
    private void selecionarJogadorNaTabela(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            return;
        }

        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        String id = (String) tableModel.getValueAt(selectedRow, 0);
        buscarJogadorPorId(id).ifPresent(this::carregarJogadorNoFormulario);
    }

    private Optional<Jogador> buscarJogadorPorId(String id) {
        for (Jogador jogador : jogadorController.listarJogadores()) {
            if (jogador.getId().equals(id)) {
                return Optional.of(jogador);
            }
        }
        return Optional.empty();
    }

    private void carregarJogadorNoFormulario(Jogador jogador) {
        jogadorSelecionado = jogador;
        txtNome.setText(jogador.getNome());
        comboPosicao.setSelectedItem(jogador.getPosicao());
        txtNumero.setText(String.valueOf(jogador.getNumero()));
        txtIdade.setText(String.valueOf(jogador.getIdade()));
        comboStatus.setSelectedItem(jogador.getStatus());

        if (jogador.getSelecao() != null) {
            comboSelecao.setSelectedItem(jogador.getSelecao().getPais());
        }
    }

    // ordena os jogadores por nome antes de exibir na tabela.
    private void atualizarTabela(List<Jogador> jogadores) {
        List<Jogador> ordenados = new ArrayList<>(jogadores);
        ordenados.sort(
                Comparator.comparing(
                        (Jogador jogador) -> jogador.getSelecao() == null ? "" : jogador.getSelecao().getPais(),
                        String.CASE_INSENSITIVE_ORDER
                ).thenComparingInt(Jogador::getNumero)
        );

        tableModel.setRowCount(0);
        for (Jogador jogador : ordenados) {
            String nomeSelecao = jogador.getSelecao() == null
                    ? "Seleção não encontrada"
                    : jogador.getSelecao().getPais();

            tableModel.addRow(new Object[]{
                    jogador.getId(),
                    jogador.getNome(),
                    jogador.getPosicao(),
                    jogador.getNumero(),
                    jogador.getIdade(),
                    nomeSelecao,
                    jogador.getStatus()
            });
        }
    }

    // limpa o formulário e remove a seleção atual, voltando para modo de novo cadastro.
    private void limparCampos() {
        jogadorSelecionado = null;
        txtNome.setText("");
        txtNumero.setText("");
        txtIdade.setText("");
        comboPosicao.setSelectedIndex(0);
        if (comboSelecao.getItemCount() > 0) {
            comboSelecao.setSelectedIndex(0);
        }
        comboStatus.setSelectedIndex(0);
        if (table != null) {
            table.clearSelection();
        }
    }

    // recarrega combos de seleção. No filtro, inclui a opção "Todas"; no cadastro, lista apenas seleções reais.
    private void atualizarComboSelecao(JComboBox<String> combo, boolean incluirTodas) {
        combo.removeAllItems();

        if (incluirTodas) {
            combo.addItem("Todas");
        }

        List<Selecao> selecoes = new ArrayList<>(selecaoController.listarSelecoes());
        selecoes.sort(Comparator.comparing(Selecao::getPais, String.CASE_INSENSITIVE_ORDER));

        for (Selecao selecao : selecoes) {
            combo.addItem(selecao.getPais());
        }
    }

    private void estilizarBotao(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
    }
}
