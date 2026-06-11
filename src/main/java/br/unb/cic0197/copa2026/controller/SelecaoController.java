package br.unb.cic0197.copa2026.controller;

import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Jogador;
import br.unb.cic0197.copa2026.model.Selecao;
import br.unb.cic0197.copa2026.service.SelecaoService;

import java.util.List;
import java.util.Optional;

public class SelecaoController {

    private final SelecaoService selecaoService;

    public SelecaoController() {
        this.selecaoService = new SelecaoService();
    }

    public List<Selecao> listarSelecoes() {
        return selecaoService.obterTodas();
    }

    public void salvarSelecao(Selecao selecao) throws Copa2026Exception {
        selecaoService.salvar(selecao);
    }

    public void atualizarSelecao(Selecao selecao) throws Copa2026Exception {
        selecaoService.atualizar(selecao);
    }

    public void removerSelecao(Selecao selecao) throws Copa2026Exception {
        selecaoService.remover(selecao);
    }

    public Optional<Selecao> obterSelecaoPorId(String id) {
        return selecaoService.obterPorId(id);
    }

    public List<Selecao> buscarSelecoesPorGrupo(String grupo) {
        return selecaoService.buscarPorGrupo(grupo);
    }

    public void adicionarJogador(Selecao selecao, Jogador jogador)
            throws Copa2026Exception {
        selecaoService.adicionarJogador(selecao, jogador);
    }

    public void validarElencoCompleto(Selecao selecao)
            throws Copa2026Exception {
        selecaoService.validarElencoCompleto(selecao);
    }
}