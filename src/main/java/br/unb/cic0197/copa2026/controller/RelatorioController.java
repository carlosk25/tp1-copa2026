package br.unb.cic0197.copa2026.controller;

import br.unb.cic0197.copa2026.model.*;
import br.unb.cic0197.copa2026.services.UsuarioService;
import br.unb.cic0197.copa2026.view.TelaRelatorio;
import javax.swing.*;
import java.util.List;

public class RelatorioController {
    private final TelaRelatorio view;
    private final UsuarioService usuarioService;

    public RelatorioController(TelaRelatorio view) {
        this.view = view;
        this.usuarioService = new UsuarioService();
    }

    public void configurarPermissoesExibicao() {
        Usuario logado = SessaoSistema.getUsuarioLogado();

        if (logado == null || !(logado instanceof Administrador)) {
            view.getPainelAdminAprovacao().setVisible(false);
        } else {
            view.getPainelAdminAprovacao().setVisible(true);
            carregarSolicitacoesPendentes();
        }
        carregarUsuariosEMetricas();
    }

    public void carregarSolicitacoesPendentes() {
        view.getModeloSolicitacoes().setRowCount(0);
  
        List<SolicitacaoCadastro> solicitacoes = usuarioService.obterTodasSolicitacoes();
        for (SolicitacaoCadastro s : solicitacoes) {
            view.getModeloSolicitacoes().addRow(new Object[]{
                    s.getNome(),
                    s.getEmail(),
                    s.getTipoPerfilSolicitado()
            });
        }
    }

    public void carregarUsuariosEMetricas() {
        view.getModeloUsuarios().setRowCount(0);
        view.getModeloUsuarios().addRow(new Object[]{"Métrica: Total de Partidas", "Global", "64 partidas registradas"});

        List<Usuario> usuarios = usuarioService.obtertodas();
        for (Usuario u : usuarios) {
            view.getModeloUsuarios().addRow(new Object[]{
                    u.getNome(),
                    u.getTipoPerfil(),
                    u.obterDadosMetricaConsolidada()
            });
        }
    }

    public void executarAprovacao() {
        int linhaSelecionada = view.getTabelaSolicitacoes().getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(view, "Selecione uma solicitação na tabela acima para aprovar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<SolicitacaoCadastro> solicitacoes = usuarioService.obterTodasSolicitacoes();
        SolicitacaoCadastro solicitacaoEscolhida = solicitacoes.get(linhaSelecionada);

        try {
          
            String senhaGerada = usuarioService.aprovarSolicitacao(solicitacaoEscolhida);

            JOptionPane.showMessageDialog(view,
                    "Cadastro aprovado com sucesso!\n\n" +
                            "SENHA TEMPORÁRIA GERADA: " + senhaGerada + "\n" +
                            "Forneça essa senha ao usuário. Ele será obrigado a alterá-la no primeiro acesso.",
                    "Acesso Liberado", JOptionPane.INFORMATION_MESSAGE);

            carregarSolicitacoesPendentes();
            carregarUsuariosEMetricas();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erro ao aprovar cadastro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
