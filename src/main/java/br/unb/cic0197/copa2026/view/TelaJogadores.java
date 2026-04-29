package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import javax.swing.*;
import java.awt.*;

public class TelaJogadores extends JPanel {
    public TelaJogadores(CopaApp app){
        setLayout(new GridLayout(0, 2, 10, 10));

        add(new JLabel("Nome:"));
        JTextField txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("Posição:"));
        JTextField txtPosicao = new JTextField();
        add(txtPosicao);

        add(new JLabel("Número:"));
        JTextField txtNumero = new JTextField();
        add(txtNumero);

        add(new JLabel("Idade:"));
        JTextField txtIdade = new JTextField();
        add(txtIdade);

        add(new JLabel("Seleção:"));
        JTextField txtSelecao = new JTextField();
        add(txtSelecao);

        add(new JLabel("Status:"));
        String[] statusOpcoes = {"Ativo", "Lesionado", "Suspenso"};
        JComboBox<String> comboStatus = new JComboBox<>(statusOpcoes);
        add(comboStatus);

        JButton btnAdicionar = new JButton("Adicionar");
        add(btnAdicionar);

        JButton btnListar = new JButton("Listar");
        add(btnListar);

        JButton btnEditar = new JButton("Editar");
        add(btnEditar);

        JButton btnExcluir = new JButton("Excluir");
        add(btnExcluir);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> app.mostrarTela("menu"));
        add(btnVoltar);
    }
}
