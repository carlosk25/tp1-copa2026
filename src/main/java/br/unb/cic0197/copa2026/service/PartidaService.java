package br.unb.cic0197.copa2026.service;

import br.unb.cic0197.copa2026.enums.FaseCompeticao;
import br.unb.cic0197.copa2026.model.Partida;
import br.unb.cic0197.copa2026.repository.PartidaRepository;

import java.util.List;
import java.util.Optional;

public class PartidaService {
    private final PartidaRepository repository;

    public PartidaService() {
        this.repository = new PartidaRepository();
    }

    public List<Partida> obterTodas() {
        return repository.findAll();
    }

    public void salvar(Partida partida) {
        if (partida.getId() == null || partida.getId().isEmpty()) {
            repository.add(partida);
        } else {
            repository.update(partida);
        }
    }

    public void atualizar(Partida partida) {
        repository.update(partida);
    }

    public void remover(Partida partida) {
        repository.delete(partida);
    }

    public Optional<Partida> obterPorId(String id) {
        return repository.findById(id);
    }

    public List<Partida> buscar(String selecao, FaseCompeticao fase, String data, String arbitro) {
        return repository.search(selecao, fase, data, arbitro);
    }

    public List<Partida> carregarTodas() {
        return repository.findAll();
    }

    public void salvarTodas(List<Partida> partidas) {
        // Não é necessário neste caso, pois o Repository
        // já persiste automaticamente em add/update/delete
    }
}
