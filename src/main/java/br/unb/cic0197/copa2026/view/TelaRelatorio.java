package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import br.unb.cic0197.copa2026.controller.RelatorioController;

public class TelaRelatorio extends JPanel {
    private CopaApp app;
    private DefaultTableModel modeloUsuarios;
    private DefaultTableModel modeloSolicitacoes;
    private JTable tabelaUsuarios;
    private JTable tabelaSolicitacoes;
    private JPanel painelAdminAprovacao;
    private RelatorioController controller; 

    public TelaRelatorio(CopaApp app) {
        this.app = app;
        this.controller = new RelatorioController(this);

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("PAINEL OPERACIONAL E RELATÓRIOS CONSOLIDADOS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        String[] colunasUsers = {"Nome / Métrica", "Perfil", "Detalhes Funcionais (Polimorfismo)"};
        modeloUsuarios = new DefaultTableModel(colunasUsers, 0);
        tabelaUsuarios = new JTable(modeloUsuarios);

        JPanel painelCentro = new JPanel(new GridLayout(2, 1, 10, 10));
        painelCentro.add(new JScrollPane(tabelaUsuarios));
        add(painelCentro, BorderLayout.CENTER);
   
        painelAdminAprovacao = new JPanel(new BorderLayout(5, 5));
        painelAdminAprovacao.setBorder(BorderFactory.createTitledBorder("Solicitações Pendentes de Cadastro (Apenas Administradores)"));

        String[] colunasSol = {"Nome Solicitante", "E-mail informado", "Perfil Desejado"};
        modeloSolicitacoes = new DefaultTableModel(colunasSol, 0);
        tabelaSolicitacoes = new JTable(modeloSolicitacoes);
        painelAdminAprovacao.add(new JScrollPane(tabelaSolicitacoes), BorderLayout.CENTER);

        JButton btnAprovar = new JButton("✔ Aprovar Selecionado e Gerar Senha");
        painelAdminAprovacao.add(btnAprovar, BorderLayout.SOUTH);

        btnAprovar.addActionListener(e -> controller.executarAprovacao());

        painelCentro.add(painelAdminAprovacao);

        // Botão Voltar
        JButton btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.addActionListener(e -> app.mostrarTela("dashboard"));
        add(btnVoltar, BorderLayout.SOUTH);

        atualizarDados();
    }

 
    public void atualizarDados() {
        controller.configurarPermissoesExibicao();
    }

    public DefaultTableModel getModeloUsuarios() { return modeloUsuarios; }
    public DefaultTableModel getModeloSolicitacoes() { return modeloSolicitacoes; }
    public JTable getTabelaSolicitacoes() { return tabelaSolicitacoes; }
    public JPanel getPainelAdminAprovacao() { return painelAdminAprovacao; }
}
