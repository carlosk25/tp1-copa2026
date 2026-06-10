package br.unb.cic0197.copa2026.controller;

import br.unb.cic0197.copa2026.enums.FaseCompeticao;
import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Partida;
import br.unb.cic0197.copa2026.service.PartidaService;

import java.util.List;
import java.util.Optional;

public class PartidaController {
    private final PartidaService partidaService;

    public PartidaController() {
        this.partidaService = new PartidaService();
    }

    public List<Partida> listarPartidas() {
        return partidaService.obterTodas();
    }

    public void salvarPartida(Partida partida) throws Copa2026Exception {
        partidaService.salvar(partida);
    }

    public void atualizarPartida(Partida partida) throws Copa2026Exception {
        partidaService.atualizar(partida);
    }

    public void removerPartida(Partida partida) throws Copa2026Exception {
        partidaService.remover(partida);
    }

    public Optional<Partida> obterPartidaPorId(String id) {
        return partidaService.obterPorId(id);
    }

    public List<Partida> buscarPartidas(String selecao, FaseCompeticao fase, String data, String arbitro) {
        return partidaService.buscar(selecao, fase, data, arbitro);
    }
}
