package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import javax.swing.*;
import java.awt.*;

public class TelaMenu extends JPanel {
    private CopaApp app;

    public TelaMenu(CopaApp app) {
        this.app = app;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 118, 210));
        header.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Copa do Mundo 2026");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Sistema de Gerenciamento");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(Color.WHITE);

        titlePanel.add(titulo);
        titlePanel.add(subtitulo);

        JButton sair = new JButton("Sair");
        sair.addActionListener(e -> app.mostrarTela("login"));

        header.add(titlePanel, BorderLayout.WEST);
        header.add(sair, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(2, 3, 25, 25));
        cards.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        cards.setBackground(getBackground());

        cards.add(createCard("Jogadores", "jogadores",
                new Color(52, 152, 219)));

        cards.add(createCard("Seleções", "selecoes",
                new Color(46, 204, 113)));

        cards.add(createCard("Partidas", "partidas",
                new Color(241, 196, 15)));

        cards.add(createCard("Estádios", "estadios",
                new Color(231, 76, 60)));

        cards.add(createCard("Árbitros", "arbitros",
                new Color(155, 89, 182)));

        cards.add(createCard("Relatórios", "relatorios",
                new Color(52, 73, 94)));

        add(cards, BorderLayout.CENTER);
    }

    private JPanel createCard(
            String titulo,
            String tela,
            Color color) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel titleLabel = new JLabel(titulo);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton acessar = new JButton("Acessar");
        acessar.setBackground(color);
        acessar.setForeground(Color.WHITE);
        acessar.setFocusPainted(false);
        acessar.setAlignmentX(Component.CENTER_ALIGNMENT);

        acessar.addActionListener(e -> app.mostrarTela(tela));

        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);
        card.add(Box.createVerticalGlue());
        card.add(acessar);

        return card;
    }

}
