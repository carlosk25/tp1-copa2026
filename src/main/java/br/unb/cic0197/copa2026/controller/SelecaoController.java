package br.unb.cic0197.copa2026.controller;

import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Selecao;
import br.unb.cic0197.copa2026.service.SelecaoService;

import java.util.List;
import java.util.Optional;

/**
 * controlador de seleções.
 * funciona como ponte entre a interface gráfica e as regras de negócio do selecaoservice.
 */
public class SelecaoController {

    private final SelecaoService selecaoService;

    public SelecaoController() {
        this.selecaoService = new SelecaoService();
    }

    // lista todas as seleções cadastradas para exibição na tabela e nos combos.
    public List<Selecao> listarSelecoes() {
        return selecaoService.obterTodas();
    }

    // cadastra uma nova seleção após validação no Service.
    public void salvarSelecao(Selecao selecao) throws Copa2026Exception {
        selecaoService.salvar(selecao);
    }

    // atualiza os dados de uma seleção já existente.
    public void atualizarSelecao(Selecao selecao) throws Copa2026Exception {
        selecaoService.atualizar(selecao);
    }

    public void removerSelecao(Selecao selecao) throws Copa2026Exception {
        selecaoService.remover(selecao);
    }

    // consulta seleções por grupo, usada no filtro da tela.
    public List<Selecao> buscarSelecoesPorGrupo(String grupo) {
        return selecaoService.buscarPorGrupo(grupo);
    }

    public Optional<Selecao> buscarSelecaoPorPais(String pais) {
        return selecaoService.buscarPorPais(pais);
    }
}