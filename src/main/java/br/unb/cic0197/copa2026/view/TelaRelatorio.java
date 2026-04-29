package br.unb.cic0197.copa2026.view;

import javax.swing.*;
import java.awt.*;

public class TelaRelatorio extends JFrame {

    private JTextArea txtRelatorio = new JTextArea(10, 30);
    private JButton btnGerar = new JButton("Gerar Relatório");

    public TelaRelatorio() {
        setTitle("Relatórios");
        setSize(400, 300);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtRelatorio.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtRelatorio);

        add(scroll, BorderLayout.CENTER);
        add(btnGerar, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaRelatorio();
    }
}
