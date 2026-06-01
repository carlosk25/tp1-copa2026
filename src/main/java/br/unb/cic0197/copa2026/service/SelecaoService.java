package br.unb.cic0197.copa2026.service;

import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Jogador;
import br.unb.cic0197.copa2026.model.Selecao;

public class SelecaoService {

    public void adicionarJogador(
            Selecao selecao,
            Jogador jogador)
            throws Copa2026Exception {

        if (selecao.getJogadores().size() >= 26) {
            throw new Copa2026Exception(
                    "Seleção já possui 26 jogadores");
        }

        for (Jogador j : selecao.getJogadores()) {
            if (j.getNumero() == jogador.getNumero()) {
                throw new Copa2026Exception("Este número já está sendo utilizado por outro jogador");
            }
        }

        selecao.adicionarJogador(jogador);

    }
    /* pensei em usarmos esse metodo para validar se a seleção cumpre esses requisitos antes de iniciar uma partida. Porque tem jogadores que vão
    estar lesionados ou suspensos e não poderão entrar em partida. */

    public void validarElencoCompleto(Selecao selecao) throws Copa2026Exception {
        if (selecao.getJogadores().size() < 18) {
            throw new Copa2026Exception("Seleção precisa ter pelo menos 18 jogadores.");
        }

        if (selecao.getJogadores().size() > 26) {
            throw new Copa2026Exception("Seleção não pode ter mais de 26 jogadores.");
        }
    }
}