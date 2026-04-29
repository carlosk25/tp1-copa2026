package br.unb.cic0197.copa2026.app;

import br.unb.cic0197.copa2026.view.TelaLogin; // Import alterado
import javax.swing.*;

public class RunLogin {
    public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
            TelaLogin tela = new TelaLogin();
            tela.setVisible(true);
        });
    }
}
