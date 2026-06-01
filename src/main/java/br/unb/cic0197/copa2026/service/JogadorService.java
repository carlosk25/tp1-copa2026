package br.unb.cic0197.copa2026.service;

import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Jogador;

public class JogadorService {

    public void validarJogador(Jogador jogador)
            throws Copa2026Exception {

        if (jogador.getNome() == null ||
                jogador.getNome().isBlank()) {

            throw new Copa2026Exception(
                    "Nome não pode ser vazio");
        }

        if (jogador.getIdade() < 15 ||
                jogador.getIdade() > 50) {

            throw new Copa2026Exception(
                    "Idade inválida");
        }

        if (jogador.getNumero() <= 0) {

            throw new Copa2026Exception(
                    "Número inválido");
        }
        if (jogador.getSelecao() == null){
            throw new Copa2026Exception("O jogador deve estar vinculado a uma seleção");
        }
    }
}