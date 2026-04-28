package br.unb.cic0197.copa2026.app;

import br.unb.cic0197.copa2026.view.*;
import javax.swing.*;

public class CopaApp extends JFrame {
    private JPanel contentPanel;

    public CopaApp() {
        setTitle("Copa 2026");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        contentPanel = new JPanel();
        setContentPane(contentPanel);
    }

    public void start() {
        // Você pode mudar para "login" se quiser que comece pela tela de login
        mostrarTela("menu");
        setVisible(true);
    }

    public void mostrarTela(String telaNome) {
        contentPanel.removeAll();

        switch(telaNome) {
            case "login":
                contentPanel.add(new TelaLogin(this));
                break;
            case "partida":
                contentPanel.add(new TelaPartida());
                break;
            case "menu":
                contentPanel.add(criarTelaMenu());
                break;
            case "selecao":
                contentPanel.add(new TelaSelecao(this));
                break;
            case "estadio":
                contentPanel.add(new EstadioView(this));
                break;
            case "arbitro":
                contentPanel.add(new ArbitroView(this));
                break;
            default:
                contentPanel.add(criarTelaMenu());
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel criarTelaMenu() {
        return new TelaMenu(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CopaApp().start();
        });
    }
}