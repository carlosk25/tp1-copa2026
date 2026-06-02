package br.unb.cic0197.copa2026.service;

import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Arbitro;

public class ArbitroService {

    public void validarArbitro(Arbitro arbitro) throws Copa2026Exception {
        if (arbitro == null) {
            throw new Copa2026Exception("Árbitro inválido.");
        }

        if (arbitro.getNome() == null || arbitro.getNome().isBlank()) {
            throw new Copa2026Exception("Nome do árbitro não pode ser vazio.");
        }

        if (arbitro.getNacionalidade() == null || arbitro.getNacionalidade().isBlank()) {
            throw new Copa2026Exception("Nacionalidade do árbitro não pode ser vazia.");
        }

        if (arbitro.getExperiencia() == null || arbitro.getExperiencia().isBlank()) {
            throw new Copa2026Exception("Experiência do árbitro não pode ser vazia.");
        }
    }
}
