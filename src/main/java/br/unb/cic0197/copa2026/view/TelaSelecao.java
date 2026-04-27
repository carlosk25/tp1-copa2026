package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import javax.swing.*;
import java.awt.*;

public class TelaSelecao extends JPanel {

    public TelaSelecao(CopaApp app) {
        setLayout(new GridLayout(0, 2, 10, 10));

        add(new JLabel("Nome da Seleção:"));
        JTextField txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("Técnico:"));
        JTextField txtTecnico = new JTextField();
        add(txtTecnico);

        add(new JLabel("Número de Jogadores:"));
        JTextField txtNumero = new JTextField();
        add(txtNumero);

        JButton btnAdicionar = new JButton("Adicionar");
        add(btnAdicionar);

        JButton btnEditar = new JButton("Editar");
        add(btnEditar);

        JButton btnListar = new JButton("Listar");
        add(btnListar);

        JButton btnExcluir = new JButton("Excluir");
        add(btnExcluir);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> app.mostrarTela("menu"));
        add(btnVoltar);
    }
}