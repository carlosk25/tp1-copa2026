package br.unb.cic0197.copa2026.service;

import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Estadio;

public class EstadioService {

    public void validarEstadio(Estadio estadio) throws Copa2026Exception {
        if (estadio == null) {
            throw new Copa2026Exception("Estádio inválido.");
        }

        if (estadio.getNome() == null || estadio.getNome().isBlank()) {
            throw new Copa2026Exception("Nome do estádio não pode ser vazio.");
        }

        if (estadio.getLocalizacao() == null || estadio.getLocalizacao().isBlank()) {
            throw new Copa2026Exception("Localização do estádio não pode ser vazia.");
        }

        if (estadio.getCapacidade() <= 0) {
            throw new Copa2026Exception("Capacidade do estádio deve ser maior que zero.");
        }
    }
}
