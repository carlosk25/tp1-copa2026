package br.unb.cic0197.copa2026.view;

import javax.swing.*;
import java.awt.*;

public class EstadioView extends JFrame {
    private JTextField txtNome = new JTextField(20);
    private JTextField txtLocal = new JTextField(20);
    private JTextField txtCapacidade = new JTextField(10);
    private JButton btnSalvar = new JButton("Salvar Estádio");

    public EstadioView(){
        setTitle("Copa 2026 - Gestão de Estádios");
        setSize(400, 250);
        setLayout(new GridLayout(4, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel(" Nome do Estádio:"));
        add(txtNome);
        add(new JLabel(" Localização:"));
        add(txtLocal);
        add(new JLabel(" Capacidade:"));
        add(txtCapacidade);
        add(new JLabel(""));
        add(btnSalvar);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new EstadioView();
    }
}