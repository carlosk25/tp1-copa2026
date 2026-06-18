package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import javax.swing.*;
import java.awt.*;
import br.unb.cic0197.copa2026.controller.SessaoSistema;
import br.unb.cic0197.copa2026.model.Usuario;
import br.unb.cic0197.copa2026.model.Administrador;
import br.unb.cic0197.copa2026.model.Organizador;
import br.unb.cic0197.copa2026.model.ArbitroUser;
import br.unb.cic0197.copa2026.repository.UsuarioRepository;

import javax.swing.*;
import java.awt.*;

public class TelaMenu extends JPanel {
    private CopaApp app;
    private JPanel cardsContainer; 

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
        sair.setFont(new Font("Segoe UI", Font.BOLD, 12));
        sair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sair.addActionListener(e -> {
            SessaoSistema.encerrarSessao();
            app.mostrarTela("login");
        });

        header.add(titlePanel, BorderLayout.WEST);
        header.add(sair, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        
        cardsContainer = new JPanel();
        cardsContainer.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        cardsContainer.setBackground(getBackground());

        add(cardsContainer, BorderLayout.CENTER);
    }


    public void renderizarMenu() {
        cardsContainer.removeAll();

        Usuario usuarioLogado = SessaoSistema.getUsuarioLogado();

        if (usuarioLogado != null) {

            //recarrega o objeto
            java.util.Optional<Usuario> usuarioAtualizado =
                    new UsuarioRepository().findById(usuarioLogado.getEmail());

            if (usuarioAtualizado.isPresent()) {
                usuarioLogado = usuarioAtualizado.get();
                SessaoSistema.iniciarSessao(usuarioLogado); // atualiza a sessão
            }

            if (usuarioLogado instanceof Organizador) {
                cardsContainer.setLayout(new GridLayout(2, 2, 25, 25));
                cardsContainer.add(createCard("Jogadores", "jogadores", new Color(52, 152, 219)));
                cardsContainer.add(createCard("Seleções",  "selecoes",  new Color(46, 204, 113)));
                cardsContainer.add(createCard("Partidas",  "partidas",  new Color(241, 196, 15)));
                cardsContainer.add(createCard("Estádios",  "estadios",  new Color(231, 76, 60)));
            } else if (usuarioLogado instanceof Administrador) {
                cardsContainer.setLayout(new GridLayout(0, 3, 25, 25));
                cardsContainer.add(createCard("Jogadores",       "jogadores",     new Color(52, 152, 219)));
                cardsContainer.add(createCard("Seleções",        "selecoes",      new Color(46, 204, 113)));
                cardsContainer.add(createCard("Partidas",        "partidas",      new Color(241, 196, 15)));
                cardsContainer.add(createCard("Estádios",        "estadios",      new Color(231, 76, 60)));
                cardsContainer.add(createCard("Árbitros",        "arbitros",      new Color(155, 89, 182)));
                cardsContainer.add(createCard("Relatórios",      "relatorios",    new Color(52, 73, 94)));
                cardsContainer.add(createCard("Gestão Usuários", "gestaoUsuarios",new Color(211, 84, 0)));
            } else {
                cardsContainer.setLayout(new GridLayout(1, 1, 25, 25));
                cardsContainer.add(createCard("Partidas", "partidas", new Color(241, 196, 15)));
            }
        }

        cardsContainer.revalidate();
        cardsContainer.repaint();
    }

    private JPanel createCard(String titulo, String tela, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 235, 240), 1, true),
                BorderFactory.createEmptyBorder(25, 20, 25, 20)));

        JLabel titleLabel = new JLabel(titulo);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton acessar = new JButton("Acessar");
        acessar.setBackground(color);
        acessar.setForeground(Color.WHITE);
        acessar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        acessar.setFocusPainted(false);
        acessar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        acessar.setAlignmentX(Component.CENTER_ALIGNMENT);
        acessar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        acessar.addActionListener(e -> app.mostrarTela(tela));

        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);
        card.add(Box.createVerticalGlue());
        card.add(acessar);

        return card;
    }
}
