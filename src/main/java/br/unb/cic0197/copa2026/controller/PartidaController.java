package br.unb.cic0197.copa2026.controller;

import br.unb.cic0197.copa2026.enums.FaseCompeticao;
import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Partida;
import br.unb.cic0197.copa2026.service.PartidaService;

import java.util.List;
import java.util.Optional;

// controlador que recebe ações da tela de partidas e repassa para o service.
public class PartidaController {
    private final PartidaService partidaService;

    public PartidaController() {
        this.partidaService = new PartidaService();
    }

    // busca todas as partidas para preencher a tabela da interface.
    public List<Partida> listarPartidas() {
        return partidaService.obterTodas();
    }

    // salva uma nova partida depois das validações feitas no service.
    public void salvarPartida(Partida partida) throws Copa2026Exception {
        partidaService.salvar(partida);
    }

    // atualiza uma partida já cadastrada, mantendo o mesmo id.
    public void atualizarPartida(Partida partida) throws Copa2026Exception {
        partidaService.atualizar(partida);
    }

    // remove a partida selecionada pela tela.
    public void removerPartida(Partida partida) throws Copa2026Exception {
        partidaService.remover(partida);
    }

    public Optional<Partida> obterPartidaPorId(String id) {
        return partidaService.obterPorId(id);
    }

    // aplica os filtros da tela: seleção, fase, data e árbitro.
    public List<Partida> buscarPartidas(String selecao, FaseCompeticao fase, String data, String arbitro) {
        return partidaService.buscar(selecao, fase, data, arbitro);
    }
}
