package br.unb.cic0197.copa2026.service;

import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Jogador;
import br.unb.cic0197.copa2026.model.Selecao;
import br.unb.cic0197.copa2026.repository.SelecaoRepository;

import java.util.List;
import java.util.Optional;

public class SelecaoService {

    private final SelecaoRepository repository;

    public SelecaoService() {
        this.repository = new SelecaoRepository();
    }

    public List<Selecao> obterTodas() {
        return repository.findAll();
    }

    public void salvar(Selecao selecao) throws Copa2026Exception {
        validarSelecao(selecao);
        validarSelecaoDuplicada(selecao);
        repository.add(selecao);
    }

    public void atualizar(Selecao selecao) throws Copa2026Exception {
        validarSelecao(selecao);

        Optional<Selecao> existente = repository.findById(selecao.getId());

        if (existente.isEmpty()) {
            throw new Copa2026Exception("Seleção não encontrada para atualização.");
        }

        repository.update(selecao);
    }

    public void remover(Selecao selecao) throws Copa2026Exception {
        if (selecao == null || selecao.getId() == null || selecao.getId().isBlank()) {
            throw new Copa2026Exception("Seleção inválida para exclusão.");
        }

        Optional<Selecao> existente = repository.findById(selecao.getId());

        if (existente.isEmpty()) {
            throw new Copa2026Exception("Seleção não encontrada para exclusão.");
        }

        repository.delete(selecao);
    }

    public Optional<Selecao> obterPorId(String id) {
        return repository.findById(id);
    }

    public List<Selecao> buscarPorGrupo(String grupo) {
        return repository.search(grupo);
    }

    public void validarSelecao(Selecao selecao) throws Copa2026Exception {
        if (selecao == null) {
            throw new Copa2026Exception("Seleção inválida.");
        }

        if (selecao.getPais() == null || selecao.getPais().isBlank()) {
            throw new Copa2026Exception("País da seleção é obrigatório.");
        }

        if (selecao.getGrupo() == null || selecao.getGrupo().isBlank()) {
            throw new Copa2026Exception("Grupo da seleção é obrigatório.");
        }

        if (selecao.getTecnico() == null || selecao.getTecnico().isBlank()) {
            throw new Copa2026Exception("Técnico da seleção é obrigatório.");
        }
    }

    private void validarSelecaoDuplicada(Selecao selecao)
            throws Copa2026Exception {

        for (Selecao existente : repository.findAll()) {

            boolean mesmoPais =
                    existente.getPais().equalsIgnoreCase(selecao.getPais());

            boolean selecaoDiferente =
                    !existente.getId().equals(selecao.getId());

            if (mesmoPais && selecaoDiferente) {
                throw new Copa2026Exception(
                        "Já existe uma seleção cadastrada com esse país."
                );
            }
        }
    }

    public void adicionarJogador(Selecao selecao, Jogador jogador)
            throws Copa2026Exception {

        if (selecao == null) {
            throw new Copa2026Exception("Seleção inválida.");
        }

        if (jogador == null) {
            throw new Copa2026Exception("Jogador inválido.");
        }

        if (jogador.getNumero() < 1 || jogador.getNumero() > 26) {
            throw new Copa2026Exception("O número da camisa deve estar entre 1 e 26.");
        }

        if (selecao.getJogadores().size() >= 26) {
            throw new Copa2026Exception("Seleção já possui 26 jogadores.");
        }

        for (Jogador j : selecao.getJogadores()) {
            if (j.getNumero() == jogador.getNumero()) {
                throw new Copa2026Exception(
                        "Este número já está sendo utilizado por outro jogador."
                );
            }
        }

        selecao.adicionarJogador(jogador);
        repository.update(selecao);
    }

    public void validarElencoCompleto(Selecao selecao)
            throws Copa2026Exception {

        int quantidade = selecao.getJogadores().size();

        if (quantidade < 16) {
            throw new Copa2026Exception(
                    "Seleção precisa ter pelo menos 16 jogadores."
            );
        }

        if (quantidade > 26) {
            throw new Copa2026Exception(
                    "Seleção não pode ter mais de 26 jogadores."
            );
        }
    }
}