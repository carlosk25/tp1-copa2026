package br.unb.cic0197.copa2026.view;

import br.unb.cic0197.copa2026.app.CopaApp;
import javax.swing.*;
import java.awt.*;
/*
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
*/
/*
import br.unb.cic0197.copa2026.controller.SessaoSistema;
import br.unb.cic0197.copa2026.model.Usuario;
import br.unb.cic0197.copa2026.model.Administrador;
import br.unb.cic0197.copa2026.model.Organizador;
import br.unb.cic0197.copa2026.model.ArbitroUser;

import javax.swing.*;
import java.awt.*;

public class TelaMenu extends JPanel {
    private CopaApp app;
    private JPanel menuPanel; 

    public TelaMenu(CopaApp app) {
        this.app = app;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(0, 100, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Copa 2026 - Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel);

        headerPanel.add(Box.createRigidArea(new Dimension(0, 10))); 

        menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        menuPanel.setOpaque(false); 

        headerPanel.add(menuPanel);
        add(headerPanel, BorderLayout.NORTH);

        
        JPanel centerArea = new JPanel();
        centerArea.setOpaque(false);
        add(centerArea, BorderLayout.CENTER);

      
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(240, 248, 255));
        JButton logoutButton = new JButton("Sair");

        logoutButton.addActionListener(e -> {
            SessaoSistema.encerrarSessao();
            app.mostrarTela("login");
        });

        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void renderizarMenu() {
       
        menuPanel.removeAll();

        Usuario usuarioLogado = SessaoSistema.getUsuarioLogado();

        if (usuarioLogado != null) {

            if (usuarioLogado instanceof Organizador) {
                // O Organizador só tem acesso aos 4 botões solicitados
                menuPanel.add(createMenuButton("Jogadores", "jogadores", new Color(52, 152, 219)));
                menuPanel.add(createMenuButton("Seleções", "selecoes", new Color(46, 204, 113)));
                menuPanel.add(createMenuButton("Partidas", "partidas", new Color(241, 196, 15)));
                menuPanel.add(createMenuButton("Estádios", "estadios", new Color(231, 76, 60)));
            }

            else if (usuarioLogado instanceof Administrador) {
                // O Administrador tem acesso total a todos os recursos do sistema
                menuPanel.add(createMenuButton("Jogadores", "jogadores", new Color(52, 152, 219)));
                menuPanel.add(createMenuButton("Seleções", "selecoes", new Color(46, 204, 113)));
                menuPanel.add(createMenuButton("Partidas", "partidas", new Color(241, 196, 15)));
                menuPanel.add(createMenuButton("Estádios", "estadios", new Color(231, 76, 60)));
                menuPanel.add(createMenuButton("Árbitros", "arbitros", new Color(155, 89, 182)));
                menuPanel.add(createMenuButton("Relatórios", "relatorios", new Color(52, 73, 94)));

                menuPanel.add(createMenuButton("Gestão Usuários", "gestaoUsuarios", new Color(211, 84, 0)));
            }

            else {

                menuPanel.add(createMenuButton("Partidas", "partidas", new Color(241, 196, 15)));

            }
        }

        menuPanel.revalidate();
        menuPanel.repaint();
    }

    private JButton createMenuButton(String text, String tela, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12)); 
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); 
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> app.mostrarTela(tela));
        return button;
    }
}
*/
import br.unb.cic0197.copa2026.controller.SessaoSistema;
import br.unb.cic0197.copa2026.model.Usuario;
import br.unb.cic0197.copa2026.model.Administrador;
import br.unb.cic0197.copa2026.model.Organizador;
import br.unb.cic0197.copa2026.model.ArbitroUser;

import javax.swing.*;
import java.awt.*;

public class TelaMenu extends JPanel {
    private CopaApp app;
    private JPanel cardsContainer; // Container que guardará os blocos (cards) dinâmicos

    public TelaMenu(CopaApp app) {
        this.app = app;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        // --- HEADER SUPERIOR (Design Moderno Azul) ---
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

        // Botão Sair com encerramento de sessão correto
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

        // --- CONTAINER CENTRAL DOS CARDS ---
        // Inicializa como um Grid adaptável. O layout será redefinido no renderizarMenu().
        cardsContainer = new JPanel();
        cardsContainer.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        cardsContainer.setBackground(getBackground());

        add(cardsContainer, BorderLayout.CENTER);
    }

    /**
     * Monta os Cards na tela de forma dinâmica baseado em quem está logado no sistema.
     */
    public void renderizarMenu() {
        // Limpa tudo o que estava na tela antes
        cardsContainer.removeAll();

        Usuario usuarioLogado = SessaoSistema.getUsuarioLogado();

        if (usuarioLogado != null) {
            if (usuarioLogado instanceof Organizador) {
                // Organizador vê 4 itens (Grid 2x2 fica elegante)
                cardsContainer.setLayout(new GridLayout(2, 2, 25, 25));

                cardsContainer.add(createCard("Jogadores", "jogadores", new Color(52, 152, 219)));
                cardsContainer.add(createCard("Seleções", "selecoes", new Color(46, 204, 113)));
                cardsContainer.add(createCard("Partidas", "partidas", new Color(241, 196, 15)));
                cardsContainer.add(createCard("Estádios", "estadios", new Color(231, 76, 60)));
            }
            else if (usuarioLogado instanceof Administrador) {
                // Administrador vê todos os 7 itens (Grid de 3 colunas)
                cardsContainer.setLayout(new GridLayout(0, 3, 25, 25));

                cardsContainer.add(createCard("Jogadores", "jogadores", new Color(52, 152, 219)));
                cardsContainer.add(createCard("Seleções", "selecoes", new Color(46, 204, 113)));
                cardsContainer.add(createCard("Partidas", "partidas", new Color(241, 196, 15)));
                cardsContainer.add(createCard("Estádios", "estadios", new Color(231, 76, 60)));
                cardsContainer.add(createCard("Árbitros", "arbitros", new Color(155, 89, 182)));
                cardsContainer.add(createCard("Relatórios", "relatorios", new Color(52, 73, 94)));
                cardsContainer.add(createCard("Gestão Usuários", "gestaoUsuarios", new Color(211, 84, 0))); // Alinhado com a sua rota "usuarios"
            }
            else {
                // Árbitro ou outros perfis básicos vêm apenas Partidas
                cardsContainer.setLayout(new GridLayout(1, 1, 25, 25));
                cardsContainer.add(createCard("Partidas", "partidas", new Color(241, 196, 15)));
            }
        }

        // Força o Swing a redesenhar a tela com as permissões novas aplicado o layout correto
        cardsContainer.revalidate();
        cardsContainer.repaint();
    }

    /**
     * Construtor estético dos blocos (Cards) clicáveis
     */
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

        // Organização dos espaços internos do bloco
        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);
        card.add(Box.createVerticalGlue());
        card.add(acessar);

        return card;
    }
}
