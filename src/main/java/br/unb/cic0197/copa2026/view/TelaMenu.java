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

       
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        menuPanel.setOpaque(false); 
        
        menuPanel.add(createMenuButton("Jogadores", "jogadores", new Color(52, 152, 219)));
        menuPanel.add(createMenuButton("Seleções", "selecoes", new Color(46, 204, 113)));
        menuPanel.add(createMenuButton("Partidas", "partidas", new Color(241, 196, 15)));
        menuPanel.add(createMenuButton("Estádios", "estadios", new Color(231, 76, 60)));
        menuPanel.add(createMenuButton("Árbitros", "arbitros", new Color(155, 89, 182)));
        menuPanel.add(createMenuButton("Relatórios", "relatorios", new Color(52, 73, 94)));

        headerPanel.add(menuPanel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel centerArea = new JPanel();
        centerArea.setOpaque(false);
        add(centerArea, BorderLayout.CENTER);

        // Botão logout (Sair)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(240, 248, 255));
        JButton logoutButton = new JButton("Sair");
        logoutButton.addActionListener(e -> app.mostrarTela("login"));
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);
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

public class DashboardView extends JPanel {
    private CopaApp app;
    private JPanel menuPanel; 

    public DashboardView(CopaApp app) {
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
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); /
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> app.mostrarTela(tela));
        return button;
    }
}
