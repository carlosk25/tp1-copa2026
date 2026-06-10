package br.unb.cic0197.copa2026.service;

import br.unb.cic0197.copa2026.enums.FaseCompeticao;
import br.unb.cic0197.copa2026.enums.StatusPartida;
import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Partida;
import br.unb.cic0197.copa2026.model.ResultadoPartida;
import br.unb.cic0197.copa2026.repository.PartidaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class PartidaService {
    private final PartidaRepository repository;
    private static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HORA_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public PartidaService() {
        this.repository = new PartidaRepository();
    }

    public List<Partida> obterTodas() {
        return repository.findAll();
    }

    public void salvar(Partida partida) throws Copa2026Exception {
        validarPartida(partida);
        repository.add(partida);
    }

    public void atualizar(Partida partida) throws Copa2026Exception {
        validarPartida(partida);
        Optional<Partida> existente = repository.findById(partida.getId());
        if (existente.isEmpty()) {
            throw new Copa2026Exception("Partida não encontrada para atualização.");
        }
        repository.update(partida);
    }

    public void remover(Partida partida) throws Copa2026Exception {
        if (partida == null || partida.getId() == null || partida.getId().isBlank()) {
            throw new Copa2026Exception("Partida inválida para exclusão.");
        }
        Optional<Partida> existente = repository.findById(partida.getId());
        if (existente.isEmpty()) {
            throw new Copa2026Exception("Partida não encontrada para exclusão.");
        }
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

    private void validarPartida(Partida partida) throws Copa2026Exception {
        if (partida == null) {
            throw new Copa2026Exception("Partida inválida.");
        }
        if (partida.getData() == null || partida.getData().isBlank()) {
            throw new Copa2026Exception("Data da partida é obrigatória.");
        }
        if (partida.getHorario() == null || partida.getHorario().isBlank()) {
            throw new Copa2026Exception("Horário da partida é obrigatório.");
        }
        if (partida.getEstadio() == null || partida.getEstadio().isBlank()) {
            throw new Copa2026Exception("Estádio da partida é obrigatório.");
        }
        if (partida.getSelecaoA() == null || partida.getSelecaoA().isBlank() ||
                partida.getSelecaoB() == null || partida.getSelecaoB().isBlank()) {
            throw new Copa2026Exception("Seleção A e Seleção B são obrigatórias.");
        }
        if (partida.getSelecaoA().equalsIgnoreCase(partida.getSelecaoB())) {
            throw new Copa2026Exception("Seleção A e Seleção B não podem ser iguais.");
        }
        if (partida.getFase() == null) {
            throw new Copa2026Exception("Fase da competição é obrigatória.");
        }
        if (partida.getStatus() == null) {
            throw new Copa2026Exception("Status da partida é obrigatório.");
        }

        validarDataHora(partida.getData(), partida.getHorario());
        validarResultado(partida);
        validarConflitosDeAgenda(partida);
    }

    private void validarDataHora(String data, String horario) throws Copa2026Exception {
        try {
            LocalDate.parse(data, DATA_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new Copa2026Exception("Data inválida. Use o formato dd/MM/yyyy.");
        }

        try {
            LocalTime.parse(horario, HORA_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new Copa2026Exception("Horário inválido. Use o formato HH:mm.");
        }
    }

    private void validarResultado(Partida partida) throws Copa2026Exception {
        ResultadoPartida resultado = partida.getResultado();
        if (partida.getStatus() == StatusPartida.FINALIZADA && resultado == null) {
            throw new Copa2026Exception("Partida finalizada precisa ter resultado.");
        }
        if (resultado != null) {
            if (resultado.getGolsA() < 0 || resultado.getGolsB() < 0) {
                throw new Copa2026Exception("Gols não podem ser negativos.");
            }
        }
    }

    private void validarConflitosDeAgenda(Partida partida) throws Copa2026Exception {
        List<Partida> todas = repository.findAll();
        for (Partida existente : todas) {
            if (existente.getId().equals(partida.getId())) {
                continue;
            }
            boolean mesmaData = existente.getData().equals(partida.getData());
            boolean mesmoHorario = existente.getHorario().equals(partida.getHorario());
            if (!mesmaData || !mesmoHorario) {
                continue;
            }
            if (existente.getEstadio().equalsIgnoreCase(partida.getEstadio())) {
                throw new Copa2026Exception("Já existe uma partida agendada para este estádio na mesma data e horário.");
            }
            boolean mesmaSelecao = existente.getSelecaoA().equalsIgnoreCase(partida.getSelecaoA()) ||
                    existente.getSelecaoA().equalsIgnoreCase(partida.getSelecaoB()) ||
                    existente.getSelecaoB().equalsIgnoreCase(partida.getSelecaoA()) ||
                    existente.getSelecaoB().equalsIgnoreCase(partida.getSelecaoB());
            if (mesmaSelecao) {
                throw new Copa2026Exception("Uma seleção já está escalada para outra partida no mesmo dia e horário.");
            }
        }
    }
}
