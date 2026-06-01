package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.model.Estadio;
import br.unb.cic0197.copa2026.repository.EstadioRepository;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EstadioView extends JPanel {
    private final EstadioRepository repository;
    private Estadio estadioSelecionado;

    private JTextField txtNome;
    private JTextField txtLocal;
    private JTextField txtCapacidade;
    private JTextField txtFiltroNome;
    private JTextField txtFiltroLocal;
    private DefaultTableModel tableModel;
    private JTable table;

    public EstadioView(CopaApp app) {
        this.repository = new EstadioRepository();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(app), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildFilterPanel(), BorderLayout.SOUTH);

        atualizarTabela(repository.findAll());
    }

    private JPanel buildFormPanel(CopaApp app) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Cadastro de Estádios"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNome = new JTextField(20);
        txtLocal = new JTextField(20);
        txtCapacidade = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Localização:"), gbc);
        gbc.gridx = 1;
        panel.add(txtLocal, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Capacidade:"), gbc);
        gbc.gridx = 1;
        panel.add(txtCapacidade, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton btnNovo = new JButton("Novo");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnVoltar = new JButton("Voltar");

        btnNovo.addActionListener(e -> limparCampos());
        btnSalvar.addActionListener(e -> salvarEstadio());
        btnExcluir.addActionListener(e -> excluirEstadio());
        btnVoltar.addActionListener(e -> app.mostrarTela("menu"));

        buttonPanel.add(btnNovo);
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnVoltar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Estádios Cadastrados"));

        tableModel = new DefaultTableModel(new Object[]{"Nome", "Localização", "Capacidade"}, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(this::selecionarEstadioNaTabela);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildFilterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Consultar Estádios"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtFiltroNome = new JTextField(12);
        txtFiltroLocal = new JTextField(12);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpar = new JButton("Limpar Filtro");

        btnBuscar.addActionListener(e -> buscarEstadios());
        btnLimpar.addActionListener(e -> {
            txtFiltroNome.setText("");
            txtFiltroLocal.setText("");
            atualizarTabela(repository.findAll());
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        panel.add(txtFiltroNome, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Localização:"), gbc);
        gbc.gridx = 3;
        panel.add(txtFiltroLocal, gbc);

        gbc.gridx = 4;
        panel.add(btnBuscar, gbc);
        gbc.gridx = 5;
        panel.add(btnLimpar, gbc);

        return panel;
    }

    private void salvarEstadio() {
        String nome = txtNome.getText().trim();
        String local = txtLocal.getText().trim();
        String capacidadeTexto = txtCapacidade.getText().trim();

        if (nome.isEmpty() || local.isEmpty() || capacidadeTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos do estádio.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int capacidade;
        try {
            capacidade = Integer.parseInt(capacidadeTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Capacidade deve ser um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Estadio estadio = new Estadio(nome, local, capacidade);
        if (estadioSelecionado == null) {
            repository.add(estadio);
            JOptionPane.showMessageDialog(this, "Estádio cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            repository.update(estadioSelecionado.getNome(), estadio);
            JOptionPane.showMessageDialog(this, "Estádio atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }

        atualizarTabela(repository.findAll());
        limparCampos();
    }

    private void excluirEstadio() {
        if (estadioSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um estádio para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir o estádio selecionado?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            repository.delete(estadioSelecionado);
            atualizarTabela(repository.findAll());
            limparCampos();
        }
    }

    private void buscarEstadios() {
        String nome = txtFiltroNome.getText().trim();
        String local = txtFiltroLocal.getText().trim();
        atualizarTabela(repository.search(nome, local));
    }

    private void selecionarEstadioNaTabela(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            return;
        }

        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        String nome = (String) tableModel.getValueAt(selectedRow, 0);
        Estadio estadio = repository.findByNome(nome);
        if (estadio != null) {
            carregarEstadioNoFormulario(estadio);
        }
    }

    private void carregarEstadioNoFormulario(Estadio estadio) {
        estadioSelecionado = estadio;
        txtNome.setText(estadio.getNome());
        txtLocal.setText(estadio.getLocalizacao());
        txtCapacidade.setText(String.valueOf(estadio.getCapacidade()));
    }

    private void atualizarTabela(List<Estadio> estadios) {
        tableModel.setRowCount(0);
        for (Estadio estadio : estadios) {
            tableModel.addRow(new Object[]{
                    estadio.getNome(),
                    estadio.getLocalizacao(),
                    estadio.getCapacidade()
            });
        }
    }

    private void limparCampos() {
        estadioSelecionado = null;
        txtNome.setText("");
        txtLocal.setText("");
        txtCapacidade.setText("");
        table.clearSelection();
    }
}
