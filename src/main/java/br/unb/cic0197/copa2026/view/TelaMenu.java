package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import javax.swing.*;

public class TelaMenu extends JPanel {
    public TelaMenu(CopaApp app) {
        JButton btnPartidas = new JButton("Gerenciar Partidas");
        btnPartidas.addActionListener(e -> app.mostrarTela("partida"));
        add(btnPartidas);

        JButton btnSelecoes = new JButton("Gerenciar Seleções");
        btnSelecoes.addActionListener(e -> app.mostrarTela("selecao"));
        add(btnSelecoes);

        JButton btnEstadio = new JButton("Gerenciar Estádios");
        btnEstadio.addActionListener(e -> app.mostrarTela("estadio"));
        add(btnEstadio);

        JButton btnArbitro = new JButton("Gerenciar Árbitros");
        btnArbitro.addActionListener(e -> app.mostrarTela("arbitro"));
        add(btnArbitro);

        JButton btnJogadores = new JButton("Gerenciar Jogadores");
        btnJogadores.addActionListener(e -> app.mostrarTela("jogador"));
        add(btnJogadores);
    }
}