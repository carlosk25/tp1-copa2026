package br.unb.cic0197.copa2026.controller;

import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Jogador;
import br.unb.cic0197.copa2026.service.JogadorService;

import java.util.List;
import java.util.Optional;

public class JogadorController {

    private final JogadorService jogadorService;

    public JogadorController() {
        this.jogadorService = new JogadorService();
    }

    public List<Jogador> listarJogadores() {
        return jogadorService.obterTodos();
    }

    public void salvarJogador(Jogador jogador) throws Copa2026Exception {
        jogadorService.salvar(jogador);
    }

    public void atualizarJogador(Jogador jogador) throws Copa2026Exception {
        jogadorService.atualizar(jogador);
    }

    public void removerJogador(Jogador jogador) throws Copa2026Exception {
        jogadorService.remover(jogador);
    }

    public void removerJogadorPorNome(String nome) throws Copa2026Exception {
        jogadorService.removerPorNome(nome);
    }

    public Optional<Jogador> obterJogadorPorId(String id) {
        return jogadorService.obterPorId(id);
    }

    public Optional<Jogador> buscarJogadorPorNome(String nome) {
        for (Jogador jogador : jogadorService.obterTodos()) {
            if (jogador.getNome().equalsIgnoreCase(nome)) {
                return Optional.of(jogador);
            }
        }

        return Optional.empty();
    }
    public List<Jogador> buscarJogadores(
            String posicao,
            String paisSelecao,
            Jogador.StatusJogador status,
            Integer numero,
            Integer idade) {

        return jogadorService.buscar(
                posicao,
                paisSelecao,
                status,
                numero,
                idade
        );
    }
}