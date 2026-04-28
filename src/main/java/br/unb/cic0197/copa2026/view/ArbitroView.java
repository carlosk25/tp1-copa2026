package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import javax.swing.*;
import java.awt.*;

public class ArbitroView extends JPanel {
    private JTextField txtNome = new JTextField(20);
    private JTextField txtNacionalidade = new JTextField(20);
    private JTextField txtExperiencia = new JTextField(10);
    private JButton btnSalvar = new JButton("Salvar Árbitro");

    public ArbitroView(CopaApp app) {
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel(" Nome do Árbitro:"));
        add(txtNome);// Mudou para JPanel
        add(new JLabel(" Nacionalidade:"));
        add(txtNacionalidade);
        add(new JLabel(" Experiência (Anos):"));
        add(txtExperiencia);

        JButton btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.addActionListener(e -> app.mostrarTela("menu"));

        add(btnVoltar);
        add(btnSalvar);
    }
}