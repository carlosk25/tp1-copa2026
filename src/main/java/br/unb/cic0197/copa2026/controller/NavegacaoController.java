/*
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
            if (app.getTelaGestaoDeUsuarios() != null) {
                app.getTelaGestaoDeUsuarios().atualizarTela();
                if (logado == null || !(logado instanceof br.unb.cic0197.copa2026.model.Administrador)) {
                    return false;
                }
            }
        }

        // Atualizações automáticas antes de abrir a tela correspondente
        if (nomeTela.equalsIgnoreCase("menu") && app.getTelaMenu() != null) {
            app.getTelaMenu().renderizarMenu();
        }
        if (nomeTela.equalsIgnoreCase("relatorios")) {
            if (app.getTelaRelatorio() != null) {
                app.getTelaRelatorio().atualizarDadosDoRelatorio(); 
            }
        }

        cardLayout.show(container, nomeTela);
        return true;
    }

}
*/
