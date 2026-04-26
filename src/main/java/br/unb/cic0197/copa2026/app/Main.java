package br.unb.cic0197.copa2026.app;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CopaApp().start();
        });
    }
}

