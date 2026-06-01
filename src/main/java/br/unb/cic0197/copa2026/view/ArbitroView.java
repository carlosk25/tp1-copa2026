package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.model.Arbitro;
import br.unb.cic0197.copa2026.repository.ArbitroRepository;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ArbitroView extends JPanel {
    private final ArbitroRepository repository;
    private Arbitro arbitroSelecionado;

    private JTextField txtNome;
    private JTextField txtNacionalidade;
    private JTextField txtExperiencia;
    private JTextField txtFiltroNome;
    private JTextField txtFiltroNacionalidade;
    private DefaultTableModel tableModel;
    private JTable table;

    public ArbitroView(CopaApp app) {
        this.repository = new ArbitroRepository();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(app), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildFilterPanel(), BorderLayout.SOUTH);

        atualizarTabela(repository.findAll());
    }

    private JPanel buildFormPanel(CopaApp app) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Cadastro de Árbitros"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNome = new JTextField(20);
        txtNacionalidade = new JTextField(20);
        txtExperiencia = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nacionalidade:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNacionalidade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Experiência:"), gbc);
        gbc.gridx = 1;
        panel.add(txtExperiencia, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton btnNovo = new JButton("Novo");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnVoltar = new JButton("Voltar");

        btnNovo.addActionListener(e -> limparCampos());
        btnSalvar.addActionListener(e -> salvarArbitro());
        btnExcluir.addActionListener(e -> excluirArbitro());
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
        panel.setBorder(BorderFactory.createTitledBorder("Árbitros Cadastrados"));

        tableModel = new DefaultTableModel(new Object[]{"Nome", "Nacionalidade", "Experiência"}, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(this::selecionarArbitroNaTabela);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildFilterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Consultar Árbitros"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtFiltroNome = new JTextField(12);
        txtFiltroNacionalidade = new JTextField(12);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpar = new JButton("Limpar Filtro");

        btnBuscar.addActionListener(e -> buscarArbitros());
        btnLimpar.addActionListener(e -> {
            txtFiltroNome.setText("");
            txtFiltroNacionalidade.setText("");
            atualizarTabela(repository.findAll());
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        panel.add(txtFiltroNome, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Nacionalidade:"), gbc);
        gbc.gridx = 3;
        panel.add(txtFiltroNacionalidade, gbc);

        gbc.gridx = 4;
        panel.add(btnBuscar, gbc);
        gbc.gridx = 5;
        panel.add(btnLimpar, gbc);

        return panel;
    }

    private void salvarArbitro() {
        String nome = txtNome.getText().trim();
        String nacionalidade = txtNacionalidade.getText().trim();
        String experiencia = txtExperiencia.getText().trim();

        if (nome.isEmpty() || nacionalidade.isEmpty() || experiencia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos do árbitro.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Arbitro arbitro = new Arbitro(nome, nacionalidade, experiencia);
        if (arbitroSelecionado == null) {
            repository.add(arbitro);
            JOptionPane.showMessageDialog(this, "Árbitro cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            repository.update(arbitroSelecionado.getNome(), arbitro);
            JOptionPane.showMessageDialog(this, "Árbitro atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }

        atualizarTabela(repository.findAll());
        limparCampos();
    }

    private void excluirArbitro() {
        if (arbitroSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um árbitro para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir o árbitro selecionado?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            repository.delete(arbitroSelecionado);
            atualizarTabela(repository.findAll());
            limparCampos();
        }
    }

    private void buscarArbitros() {
        String nome = txtFiltroNome.getText().trim();
        String nacionalidade = txtFiltroNacionalidade.getText().trim();
        atualizarTabela(repository.search(nome, nacionalidade));
    }

    private void selecionarArbitroNaTabela(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            return;
        }

        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        String nome = (String) tableModel.getValueAt(selectedRow, 0);
        Arbitro arbitro = repository.findByNome(nome);
        if (arbitro != null) {
            carregarArbitroNoFormulario(arbitro);
        }
    }

    private void carregarArbitroNoFormulario(Arbitro arbitro) {
        arbitroSelecionado = arbitro;
        txtNome.setText(arbitro.getNome());
        txtNacionalidade.setText(arbitro.getNacionalidade());
        txtExperiencia.setText(arbitro.getExperiencia());
    }

    private void atualizarTabela(List<Arbitro> arbitros) {
        tableModel.setRowCount(0);
        for (Arbitro arbitro : arbitros) {
            tableModel.addRow(new Object[]{
                    arbitro.getNome(),
                    arbitro.getNacionalidade(),
                    arbitro.getExperiencia()
            });
        }
    }

    private void limparCampos() {
        arbitroSelecionado = null;
        txtNome.setText("");
        txtNacionalidade.setText("");
        txtExperiencia.setText("");
        table.clearSelection();
    }
}
