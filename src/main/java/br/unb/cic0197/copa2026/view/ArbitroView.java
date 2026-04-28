package br.unb.cic0197.copa2026.view;

import javax.swing.*;
import java.awt.*;

public class ArbitroView extends JFrame {
    private JTextField txtNome = new JTextField(20);
    private JTextField txtNacionalidade = new JTextField(20);
    private JTextField txtExperiencia = new JTextField(10);
    private JButton btnSalvar = new JButton("Salvar Árbitro");

    public ArbitroView() {
        setTitle("Copa 2026 - Gestão de Árbitros");
        setSize(400, 250);
        setLayout(new GridLayout(4, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel(" Nome do Árbitro:"));
        add(txtNome);
        add(new JLabel(" Nacionalidade:"));
        add(txtNacionalidade);
        add(new JLabel(" Experiência (Anos):"));
        add(txtExperiencia);

        add(new JLabel(""));
        add(btnSalvar);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ArbitroView();
    }
}