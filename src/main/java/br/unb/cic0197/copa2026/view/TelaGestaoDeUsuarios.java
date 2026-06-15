package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.controller.UsuarioGerenciador;
import br.unb.cic0197.copa2026.model.Usuario;
import br.unb.cic0197.copa2026.service.UsuarioService;
import br.unb.cic0197.copa2026.model.SolicitacaoCadastro;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class TelaGestaoDeUsuarios extends JPanel {
    private CopaApp app;
    // aba 1
    private DefaultTableModel modeloSolicitacoes;
    private JTable tabelaSolicitacoes;
    private TableRowSorter<DefaultTableModel> sorterSolicitacoes;
    // aba 2
    private DefaultTableModel modeloUsuariosAtivos;
    private JTable tabelaUsuariosAtivos;
    private TableRowSorter<DefaultTableModel> sorterUsuariosAtivos;

    public TelaGestaoDeUsuarios(CopaApp app) {
        this.app = app;
        initComponents();
    }

    private void initComponents() {
        
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 118, 210)); 
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel lblTitulo = new JLabel("Gerenciamento de Usuários");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Central de controle de acessos, perfis e aprovação de cadastros");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(220, 230, 242));

        headerPanel.add(lblTitulo);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        headerPanel.add(lblSubtitulo);

        add(headerPanel, BorderLayout.NORTH);

        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(new Color(245, 245, 245));
        mainContentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        
        JPanel containerBranco = new JPanel(new BorderLayout());
        containerBranco.setBackground(Color.WHITE);
        containerBranco.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(225, 225, 225), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        // sistema de 2 abas
        JTabbedPane abasPainel = new JTabbedPane();
        abasPainel.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Aba 1 : Solicitaçoes pendentes
        JPanel painelAba1 = new JPanel(new BorderLayout(10, 10));
        painelAba1.setBackground(Color.WHITE);

        String[] colunasSol = {"Nome Completo", "E-mail Informado", "Data de Nascimento", "Perfil Solicitado"};
        modeloSolicitacoes = new DefaultTableModel(colunasSol, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tabelaSolicitacoes = new JTable(modeloSolicitacoes);
        tabelaSolicitacoes.setRowHeight(24);
        tabelaSolicitacoes.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        sorterSolicitacoes = new TableRowSorter<>(modeloSolicitacoes);
        tabelaSolicitacoes.setRowSorter(sorterSolicitacoes);

        JScrollPane scrollSol = new JScrollPane(tabelaSolicitacoes);
        scrollSol.setBorder(new LineBorder(new Color(230, 230, 230)));

        
        JPanel painelFiltroAba1 = criarPainelBarraFiltro(sorterSolicitacoes, 0, 3);

        painelAba1.add(painelFiltroAba1, BorderLayout.NORTH);
        painelAba1.add(scrollSol, BorderLayout.CENTER);

        JPanel painelSulAba1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        painelSulAba1.setBackground(Color.WHITE);

        JButton btnAprovar = createStyledButton("Aprovar Entrada", new Color(46, 204, 113)); // Verde
        JButton btnReprovar = createStyledButton("Reprovar Solicitação", new Color(231, 76, 60)); // Vermelho
        JButton btnVoltarAba1 = createStyledButton("Voltar", new Color(52, 152, 219)); // Azul

        btnAprovar.addActionListener(e -> executarAprovacao());
        btnReprovar.addActionListener(e -> executarReprovacao());
        btnVoltarAba1.addActionListener(e -> app.mostrarTela("menu"));

        painelSulAba1.add(btnAprovar);
        painelSulAba1.add(btnReprovar);
        painelSulAba1.add(btnVoltarAba1);
        painelAba1.add(painelSulAba1, BorderLayout.SOUTH);

        // Aba 2: usuarios ativos no sistema
        JPanel painelAba2 = new JPanel(new BorderLayout(10, 10));
        painelAba2.setBackground(Color.WHITE);

        String[] colunasAtivos = {"Nome", "E-mail", "Data Nascimento", "Perfil do Sistema"};
        modeloUsuariosAtivos = new DefaultTableModel(colunasAtivos, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tabelaUsuariosAtivos = new JTable(modeloUsuariosAtivos);
        tabelaUsuariosAtivos.setRowHeight(24);
        tabelaUsuariosAtivos.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        sorterUsuariosAtivos = new TableRowSorter<>(modeloUsuariosAtivos);
        tabelaUsuariosAtivos.setRowSorter(sorterUsuariosAtivos);

        JScrollPane scrollAtivos = new JScrollPane(tabelaUsuariosAtivos);
        scrollAtivos.setBorder(new LineBorder(new Color(230, 230, 230)));

        
        JPanel painelFiltroAba2 = criarPainelBarraFiltro(sorterUsuariosAtivos, 0, 3);

        painelAba2.add(painelFiltroAba2, BorderLayout.NORTH);
        painelAba2.add(scrollAtivos, BorderLayout.CENTER);

        
        JPanel painelSulAba2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        painelSulAba2.setBackground(Color.WHITE);

        JButton btnEditar = createStyledButton("Editar Usuário", new Color(241, 196, 15)); // Amarelo padrão
        JButton btnExcluir = createStyledButton("Excluir Conta", new Color(231, 76, 60)); // Vermelho
        JButton btnVoltarAba2 = createStyledButton("Voltar", new Color(52, 152, 219)); // Azul

        btnEditar.addActionListener(e -> executarEdicao());
        btnExcluir.addActionListener(e -> executarExclusao());
        btnVoltarAba2.addActionListener(e -> app.mostrarTela("menu"));

        painelSulAba2.add(btnEditar);
        painelSulAba2.add(btnExcluir);
        painelSulAba2.add(btnVoltarAba2);
        painelAba2.add(painelSulAba2, BorderLayout.SOUTH);

        abasPainel.addTab("Solicitações Pendentes", painelAba1);
        abasPainel.addTab("Usuários Ativos", painelAba2);

        containerBranco.add(abasPainel, BorderLayout.CENTER);
        mainContentPanel.add(containerBranco, BorderLayout.CENTER);

        add(mainContentPanel, BorderLayout.CENTER);

        //atualiza para a solicitação apararecer
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                atualizarTela();
            }
        });

        atualizarTela();
    }

    
    private JPanel criarPainelBarraFiltro(TableRowSorter<DefaultTableModel> sorter, int indiceColunaNome, int indiceColunaFuncao) {
        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        painelFiltro.setBackground(Color.WHITE);
        painelFiltro.setBorder(new EmptyBorder(5, 2, 5, 2));

        JLabel lblPesquisa = new JLabel("Pesquisar:");
        lblPesquisa.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JTextField txtPesquisa = new JTextField(25);
        txtPesquisa.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel lblOpcao = new JLabel("Filtrar por:");
        lblOpcao.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JComboBox<String> comboOpcao = new JComboBox<>(new String[]{"Nome", "Função"});
        comboOpcao.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboOpcao.setBackground(Color.WHITE);

        // executa o filtro na tabela em tempo real enquanto digita
        Runnable aplicarFiltro = () -> {
            String texto = txtPesquisa.getText().trim();
            if (texto.isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                int colunaAlvo = comboOpcao.getSelectedItem().equals("Nome") ? indiceColunaNome : indiceColunaFuncao;
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, colunaAlvo));
            }
        };

        txtPesquisa.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { aplicarFiltro.run(); }
            @Override public void removeUpdate(DocumentEvent e) { aplicarFiltro.run(); }
            @Override public void changedUpdate(DocumentEvent e) { aplicarFiltro.run(); }
        });

        comboOpcao.addActionListener(e -> aplicarFiltro.run());

        painelFiltro.add(lblPesquisa);
        painelFiltro.add(txtPesquisa);
        painelFiltro.add(lblOpcao);
        painelFiltro.add(comboOpcao);

        return painelFiltro;
    }

    
     
    public void atualizarTela() {
        modeloSolicitacoes.setRowCount(0);
        try {
            java.util.List<br.unb.cic0197.copa2026.model.SolicitacaoCadastro> solicitacoes =
                    br.unb.cic0197.copa2026.controller.UsuarioGerenciador.obterTodasSolicitacoes();

            for (br.unb.cic0197.copa2026.model.SolicitacaoCadastro s : solicitacoes) {
                modeloSolicitacoes.addRow(new Object[]{
                        s.getNome(),
                        s.getEmail(),
                        s.getDataNascimento(),
                        s.getTipoPerfilSolicitado()
                });
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar solicitações: " + e.getMessage());
        }

        modeloUsuariosAtivos.setRowCount(0);
        try {
            java.util.List<br.unb.cic0197.copa2026.model.Usuario> usuarios =
                    br.unb.cic0197.copa2026.controller.UsuarioGerenciador.obterTodosUsuarios();

            for (br.unb.cic0197.copa2026.model.Usuario u : usuarios) {
                modeloUsuariosAtivos.addRow(new Object[]{
                        u.getNome(),
                        u.getEmail(),
                        u.getDataNascimento(),
                        u.getTipoPerfil()
                });
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar usuários: " + e.getMessage());
        }

        this.revalidate();
        this.repaint();
    }


    private void executarAprovacao() {
        int linhaVisivel = tabelaSolicitacoes.getSelectedRow();
        if (linhaVisivel == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma solicitação para aprovar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int linha = tabelaSolicitacoes.convertRowIndexToModel(linhaVisivel);

        List<SolicitacaoCadastro> solicitacoes = UsuarioGerenciador.listarSolicitacoes();
        SolicitacaoCadastro sol = solicitacoes.get(linha);

        try {
            // envia a solicitação para aprovar usando a senha do cadastro
            String senhaUtilizada = br.unb.cic0197.copa2026.controller.UsuarioGerenciador.aprovarSolicitacao(sol);

            String mensagemSucesso = String.format(
                    "Cadastro aprovado com sucesso!\n\n" +
                            "📧 Usuário: %s\n" +
                            "🔑 Senha de Acesso: %s\n\n" +
                            "O usuário já pode logar utilizando a senha definida por ele no cadastro.",
                    sol.getEmail(), senhaUtilizada
            );

            JOptionPane.showMessageDialog(this,
                    mensagemSucesso,
                    "Usuário Ativado", JOptionPane.INFORMATION_MESSAGE);

            atualizarTela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao aprovar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void executarReprovacao() {
        int linhaVisivel = tabelaSolicitacoes.getSelectedRow();
        if (linhaVisivel == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma solicitação para reprovar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int linha = tabelaSolicitacoes.convertRowIndexToModel(linhaVisivel);

        List<SolicitacaoCadastro> solicitacoes = UsuarioGerenciador.listarSolicitacoes();
        SolicitacaoCadastro sol = solicitacoes.get(linha);

        int certeza = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja REPROVAR e descartar a solicitação de " + sol.getNome() + "?",
                "Confirmar Reprovação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (certeza == JOptionPane.YES_OPTION) {
            try {
                UsuarioGerenciador.reprovarSolicitacao(sol);
                atualizarTela();
                JOptionPane.showMessageDialog(this, "Solicitação rejeitada e removida com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao reprovar solicitação: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void executarEdicao() {
        int linhaVisivel = tabelaUsuariosAtivos.getSelectedRow();
        if (linhaVisivel == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int linha = tabelaUsuariosAtivos.convertRowIndexToModel(linhaVisivel);

        String emailAtual = (String) modeloUsuariosAtivos.getValueAt(linha, 1);
        String nomeAtual = (String) modeloUsuariosAtivos.getValueAt(linha, 0);
        String dataAtual = (String) modeloUsuariosAtivos.getValueAt(linha, 2);
        String perfilAtual = (String) modeloUsuariosAtivos.getValueAt(linha, 3);

        JTextField txtNome = new JTextField(nomeAtual);
        JTextField txtEmail = new JTextField(emailAtual);
        JTextField txtData = new JTextField(dataAtual);
        JPasswordField txtSenha = new JPasswordField();
        JComboBox<String> comboPerfil = new JComboBox<>(new String[]{"Administrador", "Organizador", "Arbitro"});
        comboPerfil.setSelectedItem(perfilAtual);

        Object[] formulario = {
                "Nome:", txtNome,
                "E-mail:", txtEmail,
                "Data Nascimento:", txtData,
                "Nova Senha (deixe em branco para não alterar):", txtSenha,
                "Perfil:", comboPerfil
        };

        int result = JOptionPane.showConfirmDialog(this, formulario, "Editar Usuário", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String novaSenha = new String(txtSenha.getPassword()).trim();
                String novoPerfilSelecionado = (String) comboPerfil.getSelectedItem();

                br.unb.cic0197.copa2026.controller.UsuarioGerenciador.editarUsuario(
                        emailAtual,
                        txtNome.getText().trim(),
                        txtEmail.getText().trim(),
                        txtData.getText().trim(),
                        novaSenha.isEmpty() ? "12345" : novaSenha,
                        novoPerfilSelecionado
                );

                JOptionPane.showMessageDialog(this, "Dados do usuário updated com sucesso!");
                atualizarTela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Falha na sincronização: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //executa uma exclusao de solicitaçao
    private void executarExclusao() {
        int linhaVisivel = tabelaUsuariosAtivos.getSelectedRow();
        if (linhaVisivel == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int línea = tabelaUsuariosAtivos.convertRowIndexToModel(linhaVisivel);

        String perfilSelecionado = (String) modeloUsuariosAtivos.getValueAt(línea, 3);

        if (perfilSelecionado.equalsIgnoreCase("Administrador")) {
            JOptionPane.showMessageDialog(this, "Erro: Não é permitido excluir um usuário com perfil Administrador!", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String email = (String) modeloUsuariosAtivos.getValueAt(línea, 1);
        int certeza = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o usuário: " + email + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (certeza == JOptionPane.YES_OPTION) {
            try {
                br.unb.cic0197.copa2026.controller.UsuarioGerenciador.excluirUsuario(email);
                JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso!");
                atualizarTela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
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
}

