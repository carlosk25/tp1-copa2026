package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import javax.swing.*;
import java.awt.*;

public class TelaJogadores extends JPanel {

    public TelaJogadores(CopaApp app) {
        setLayout(new BorderLayout(10, 10));


        JPanel painelCadastro = new JPanel(new GridLayout(0, 2, 10, 10));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Dados do Jogador"));

        painelCadastro.add(new JLabel("Nome:"));
        JTextField txtNome = new JTextField();
        painelCadastro.add(txtNome);

        painelCadastro.add(new JLabel("Posição:"));
        String[] posicoes = {"Goleiro", "Lateral Direito", "Lateral Esquerdo",
                "Zagueiro", "Volante", "Meio-Campo",
                "Atacante", "Centroavante"};
        JComboBox<String> comboPosicao = new JComboBox<>(posicoes);
        painelCadastro.add(comboPosicao);

        painelCadastro.add(new JLabel("Número:"));
        JTextField txtNumero = new JTextField();
        painelCadastro.add(txtNumero);

        painelCadastro.add(new JLabel("Idade:"));
        JTextField txtIdade = new JTextField();
        painelCadastro.add(txtIdade);

        painelCadastro.add(new JLabel("Seleção:"));
        JTextField txtSelecao = new JTextField();
        painelCadastro.add(txtSelecao);


        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelFiltros.setBorder(BorderFactory.createTitledBorder("Filtrar por"));

        painelFiltros.add(new JLabel("Posição:"));
        String[] posicoesComTodas = {"Todas", "Goleiro", "Lateral Direito", "Lateral Esquerdo",
                "Zagueiro", "Volante", "Meio-Campo",
                "Atacante", "Centroavante"};
        JComboBox<String> comboFiltroPosicao = new JComboBox<>(posicoesComTodas);
        painelFiltros.add(comboFiltroPosicao);

        painelFiltros.add(new JLabel("Seleção:"));
        JTextField txtFiltroSelecao = new JTextField(10);
        painelFiltros.add(txtFiltroSelecao);

        JButton btnFiltrar = new JButton("Filtrar");
        painelFiltros.add(btnFiltrar);


        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));

        JButton btnAdicionar = new JButton("Adicionar");
        painelBotoes.add(btnAdicionar);

        JButton btnListar = new JButton("Listar");
        painelBotoes.add(btnListar);

        JButton btnEditar = new JButton("Editar");
        painelBotoes.add(btnEditar);

        JButton btnExcluir = new JButton("Excluir");
        painelBotoes.add(btnExcluir);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> app.mostrarTela("menu"));
        Component add = painelBotoes.add(btnVoltar);


        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(painelCadastro, BorderLayout.NORTH);
        painelSuperior.add(painelFiltros, BorderLayout.CENTER);
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);

        add(painelSuperior, BorderLayout.NORTH);
    }
}
