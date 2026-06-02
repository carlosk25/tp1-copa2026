package br.unb.cic0197.copa2026.service;

import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.DesignacaoArbitro;

public class DesignacaoArbitroService {

    public void validarDesignacao(DesignacaoArbitro designacao) throws Copa2026Exception {
        if (designacao == null) {
            throw new Copa2026Exception("Designação de árbitro inválida.");
        }

        if (designacao.getArbitro() == null) {
            throw new Copa2026Exception("Designação precisa ter um árbitro.");
        }

        if (designacao.getArbitro().getNome() == null || designacao.getArbitro().getNome().isBlank()) {
            throw new Copa2026Exception("Nome do árbitro na designação não pode ser vazio.");
        }

        if (designacao.getJogo() == null || designacao.getJogo().isBlank()) {
            throw new Copa2026Exception("Jogo da designação não pode ser vazio.");
        }
    }
}
