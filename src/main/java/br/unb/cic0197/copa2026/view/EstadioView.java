package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import javax.swing.*;
import java.awt.*;

public class EstadioView extends JPanel {
    private JTextField txtNome = new JTextField(20);
    private JTextField txtLocal = new JTextField(20);
    private JTextField txtCapacidade = new JTextField(10);
    private JButton btnSalvar = new JButton("Salvar Estádio");

    public EstadioView(CopaApp app) {
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel(" Nome do Estádio:"));
        add(txtNome);
        add(new JLabel(" Localização:"));
        add(txtLocal);
        add(new JLabel(" Capacidade:"));
        add(txtCapacidade);

        JButton btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.addActionListener(e -> app.mostrarTela("menu"));

        add(btnVoltar);
        add(btnSalvar);
    }
}