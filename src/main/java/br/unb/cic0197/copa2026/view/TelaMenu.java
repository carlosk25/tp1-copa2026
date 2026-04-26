// view/TelaMenu.java
package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import javax.swing.*;

public class TelaMenu extends JPanel {
    public TelaMenu(CopaApp app) {
        JButton btnPartidas = new JButton("Gerenciar Partidas");
        btnPartidas.addActionListener(e -> app.mostrarTela("partida"));

        add(btnPartidas);
    }
}