// view/TelaMenu.java
package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import javax.swing.*;

public class TelaMenu extends JPanel {
    public TelaMenu(CopaApp app) {
        JButton btnPartidas = new JButton("Gerenciar Partidas");
        btnPartidas.addActionListener(e -> app.mostrarTela("partida"));

        add(btnPartidas);

        JButton btnSelecao = new JButton("Gerenciar Seleções");
        btnSelecao.addActionListener(e -> app.mostrarTela("selecao"));

        add(btnSelecao);

        JButton btnEstadio = new JButton("Gerenciar Estádios");
        btnEstadio.addActionListener(e -> app.mostrarTela("estadio"));
        add(btnEstadio);

        JButton btnArbitro = new JButton("Gerenciar Árbitros");
        btnArbitro.addActionListener(e -> app.mostrarTela("arbitro"));
        add(btnArbitro);
    }
}