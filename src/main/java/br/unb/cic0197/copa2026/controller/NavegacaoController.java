package br.unb.cic0197.copa2026.controller;

import br.unb.cic0197.copa2026.app.CopaApp;
import br.unb.cic0197.copa2026.model.Administrador;
import br.unb.cic0197.copa2026.model.Organizador;
import br.unb.cic0197.copa2026.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class NavegacaoController {
    private final CopaApp app;
    private final CardLayout cardLayout;
    private final JPanel container;

    public NavegacaoController(CopaApp app, CardLayout cardLayout, JPanel container) {
        this.app = app;
        this.cardLayout = cardLayout;
        this.container = container;
    }

    public boolean navegarPara(String nomeTela) {
        Usuario logado = SessaoSistema.getUsuarioLogado();

        // Bloqueio para Organizador
        if (nomeTela.equalsIgnoreCase("arbitros") || nomeTela.equalsIgnoreCase("relatorios")) {
            if (logado instanceof Organizador) {
                JOptionPane.showMessageDialog(app,
                        "Acesso Restrito: Organizadores não possuem permissão para acessar esta tela.",
                        "Bloqueio de Perfil", JOptionPane.WARNING_MESSAGE);
                return false; // Cancela a navegação
            }
        }

        // Bloqueio para Gestão de Usuários (Apenas Admin)
        if (nomeTela.equalsIgnoreCase("gestaoUsuarios")) {
            if (app.getGestaoUsuariosView() != null) {
                app.getGestaoUsuariosView().atualizarTela();
                if (logado == null || !(logado instanceof Administrador)) {
                    return false; // Bloqueia virada de página
                }
            }
        }

        if (nomeTela.equalsIgnoreCase("dashboard") && app.getDashboardView() != null) {
            app.getDashboardView().renderizarMenu();
        }
        else if (nomeTela.equalsIgnoreCase("relatorios") && app.getRelatorioView() != null) {
            app.getRelatorioView().atualizarDados();
        }

        cardLayout.show(container, nomeTela);
        return true;
    }
}
