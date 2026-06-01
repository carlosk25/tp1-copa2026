package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.model.Selecao;
import br.unb.cic0197.copa2026.repository.SelecaoRepository;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.UUID;

public class TelaSelecao extends JPanel {
    private final SelecaoRepository repository;
    private final CopaApp app;
    private Selecao selecaoSelecionada;

    private JTextField txtPais;
    private JComboBox<String> comboGrupo;
    private JTextField txtTecnico;
    private JComboBox<String> comboFiltroGrupo;
    private JTable table;
    private DefaultTableModel tableModel;

    public TelaSelecao(CopaApp app) {
        this.app = app;
        this.repository = new SelecaoRepository();
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
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastro de Seleções"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtPais = new JTextField();
        comboGrupo = new JComboBox<>(new String[]{"A", "B", "C", "D", "E", "F", "G", "H"});
        txtTecnico = new JTextField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("País:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtPais, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Grupo:"), gbc);
        gbc.gridx = 1;
        formPanel.add(comboGrupo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Técnico:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtTecnico, gbc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton btnNovo = new JButton("Novo");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnVoltar = new JButton("Voltar");

        btnNovo.addActionListener(e -> limparCampos());
        btnSalvar.addActionListener(e -> salvarSelecao());
        btnExcluir.addActionListener(e -> excluirSelecao());
        btnVoltar.addActionListener(e -> app.mostrarTela("menu"));

        buttons.add(btnNovo);
        buttons.add(btnSalvar);
        buttons.add(btnExcluir);
        buttons.add(btnVoltar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(buttons, gbc);

        return formPanel;
    }

    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Seleções Cadastradas"));

        tableModel = new DefaultTableModel(new Object[]{"ID", "País", "Grupo", "Técnico"}, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(this::selecionarSelecaoNaTabela);

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildSearchPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Filtrar Seleções"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboFiltroGrupo = new JComboBox<>(new String[]{"Todas", "A", "B", "C", "D", "E", "F", "G", "H"});
        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpar = new JButton("Limpar Filtro");

        btnBuscar.addActionListener(e -> buscarSelecoes());
        btnLimpar.addActionListener(e -> {
            comboFiltroGrupo.setSelectedIndex(0);
            atualizarTabela(repository.findAll());
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Grupo:"), gbc);
        gbc.gridx = 1;
        panel.add(comboFiltroGrupo, gbc);

        gbc.gridx = 2;
        panel.add(btnBuscar, gbc);
        gbc.gridx = 3;
        panel.add(btnLimpar, gbc);

        return panel;
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
        repository.findById(id).ifPresent(this::carregarSelecaoNoFormulario);
    }

    private void carregarSelecaoNoFormulario(Selecao selecao) {
        this.selecaoSelecionada = selecao;
        txtPais.setText(selecao.getPais());
        comboGrupo.setSelectedItem(selecao.getGrupo());
        txtTecnico.setText(selecao.getTecnico());
    }

    private void salvarSelecao() {
        String pais = txtPais.getText().trim();
        String grupo = (String) comboGrupo.getSelectedItem();
        String tecnico = txtTecnico.getText().trim();

        if (pais.isEmpty() || tecnico.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha país e técnico.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selecaoSelecionada == null) {
            Selecao selecao = new Selecao(UUID.randomUUID().toString(), pais, grupo, tecnico);
            repository.add(selecao);
            JOptionPane.showMessageDialog(this, "Seleção cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            selecaoSelecionada.setPais(pais);
            selecaoSelecionada.setGrupo(grupo);
            selecaoSelecionada.setTecnico(tecnico);
            repository.update(selecaoSelecionada);
            JOptionPane.showMessageDialog(this, "Seleção atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }

        atualizarTabela(repository.findAll());
        limparCampos();
    }

    private void excluirSelecao() {
        if (selecaoSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma seleção para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir a seleção selecionada?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            repository.delete(selecaoSelecionada);
            atualizarTabela(repository.findAll());
            limparCampos();
        }
    }

    private void buscarSelecoes() {
        String grupo = (String) comboFiltroGrupo.getSelectedItem();
        atualizarTabela(repository.search(grupo));
    }

    private void atualizarTabela(List<Selecao> selecoes) {
        tableModel.setRowCount(0);
        for (Selecao selecao : selecoes) {
            tableModel.addRow(new Object[]{selecao.getId(), selecao.getPais(), selecao.getGrupo(), selecao.getTecnico()});
        }
    }

    private void limparCampos() {
        selecaoSelecionada = null;
        txtPais.setText("");
        comboGrupo.setSelectedIndex(0);
        txtTecnico.setText("");
        table.clearSelection();
    }
}
