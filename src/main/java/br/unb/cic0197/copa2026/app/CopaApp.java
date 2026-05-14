package br.unb.cic0197.copa2026.app;

import br.unb.cic0197.copa2026.view.*;
import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.image.BufferedImage;

public class CopaApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel container;

    public CopaApp() {
        initUI();
        setupScreens();
    }

    private void initUI() {
        setTitle("Copa 2026 - Sistema de Gerenciamento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        
        BufferedImage imageVizia = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        setIconImage(imageVizia);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        // FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            System.out.println("Usando LookAndFeel padrão");
        }
    }

    private void setupScreens() {
        //telas
        container.add(new LoginView(this), "login");
        container.add(new CadastroView(this), "cadastro");
        container.add(new DashboardView(this), "dashboard");
        container.add(new JogadoresView(this), "jogadores");
        container.add(new PartidaView(this), "partidas");
        container.add(new SelecaoView(this), "selecoes");
        container.add(new EstadioView(this), "estadios");
        container.add(new ArbitroView(this), "arbitros");
        container.add(new RelatorioView(this), "relatorios");

        add(container);
    }

    public void mostrarTela(String nomeTela) {
        cardLayout.show(container, nomeTela);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CopaApp().setVisible(true);
        });
    }
}
