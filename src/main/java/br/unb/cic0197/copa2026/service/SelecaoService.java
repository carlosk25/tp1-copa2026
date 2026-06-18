package br.unb.cic0197.copa2026.service;

import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Jogador;
import br.unb.cic0197.copa2026.model.Selecao;
import br.unb.cic0197.copa2026.repository.SelecaoRepository;
import br.unb.cic0197.copa2026.repository.JogadorRepository;

import java.util.List;
import java.util.Optional;

/**
 * camada de regras de negócio das seleções.
 * centraliza validações de cadastro, exclusão e aptidão para partidas.
 */
public class SelecaoService {

    private final SelecaoRepository repository;
    private final JogadorRepository jogadorRepository;

    public SelecaoService() {
        this.repository = new SelecaoRepository();
        this.jogadorRepository = new JogadorRepository();
    }

    public List<Selecao> obterTodas() {
        return repository.findAll();
    }

    // cadastra uma seleção depois de validar campos obrigatórios e duplicidade de país.
    public void salvar(Selecao selecao) throws Copa2026Exception {
        validarSelecao(selecao);
        validarSelecaoDuplicada(selecao);
        repository.add(selecao);
    }

    // atualiza uma seleção existente, mantendo o mesmo ID.
    public void atualizar(Selecao selecao) throws Copa2026Exception {
        validarSelecao(selecao);
        validarSelecaoDuplicada(selecao);

        Optional<Selecao> existente = repository.findById(selecao.getId());

        if (existente.isEmpty()) {
            throw new Copa2026Exception("Seleção não encontrada para atualização.");
        }

        repository.update(selecao);
    }

    // exclui a seleção somente se ela existir e não possuir jogadores vinculados.
    public void remover(Selecao selecao) throws Copa2026Exception {
        if (selecao == null || selecao.getId() == null || selecao.getId().isBlank()) {
            throw new Copa2026Exception("Seleção inválida para exclusão.");
        }

        Optional<Selecao> existente = repository.findById(selecao.getId());

        if (existente.isEmpty()) {
            throw new Copa2026Exception("Seleção não encontrada para exclusão.");
        }

        validarSelecaoSemJogadoresVinculados(selecao);

        repository.delete(selecao);
    }

    public Optional<Selecao> obterPorId(String id) {
        return repository.findById(id);
    }

    public List<Selecao> buscarPorGrupo(String grupo) {
        return repository.search(grupo);
    }

    public Optional<Selecao> buscarPorPais(String pais) {
        if (pais == null || pais.isBlank()) {
            return Optional.empty();
        }

        return repository.findByPais(pais);
    }

    // valida os campos obrigatórios da seleção.
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

    // impede cadastrar ou editar uma seleção para um país que já existe em outro registro.
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


    // regra de integridade: não permite excluir seleção que ainda possui jogadores associados.
    private void validarSelecaoSemJogadoresVinculados(Selecao selecao)
            throws Copa2026Exception {

        for (Jogador jogador : jogadorRepository.findAll()) {
            if (jogador.getSelecao() != null &&
                    jogador.getSelecao().getId().equals(selecao.getId())) {

                throw new Copa2026Exception(
                        "Não é possível excluir a seleção, pois existem jogadores vinculados a ela."
                );
            }
        }
    }

    // valida a quantidade total de jogadores quando for necessário checar o elenco completo.
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

    // validação chamada pela parte de partidas.
    // a seleção precisa ter 16 a 26 jogadores cadastrados e pelo menos 16 ATIVOS.
    // jogadores LESIONADOS ou SUSPENSOS não contam como disponíveis para jogar.
    public void validarSelecaoAptaParaPartida(String paisSelecao)
            throws Copa2026Exception {

        Optional<Selecao> selecaoEncontrada = repository.findByPais(paisSelecao);

        if (selecaoEncontrada.isEmpty()) {
            throw new Copa2026Exception("Seleção não encontrada: " + paisSelecao);
        }

        Selecao selecao = selecaoEncontrada.get();

        int totalJogadores = 0;
        int jogadoresDisponiveis = 0;

        for (Jogador jogador : jogadorRepository.findAll()) {
            boolean mesmaSelecao =
                    jogador.getSelecao() != null &&
                            jogador.getSelecao().getId().equals(selecao.getId());

            if (mesmaSelecao) {
                totalJogadores++;

                if (jogador.getStatus() == Jogador.StatusJogador.ATIVO) {
                    jogadoresDisponiveis++;
                }
            }
        }

        if (totalJogadores < 16) {
            throw new Copa2026Exception(
                    "A seleção " + selecao.getPais() +
                            " precisa ter pelo menos 16 jogadores cadastrados. " +
                            "Total cadastrado: " + totalJogadores + "."
            );
        }

        if (totalJogadores > 26) {
            throw new Copa2026Exception(
                    "A seleção " + selecao.getPais() +
                            " não pode ter mais de 26 jogadores cadastrados. " +
                            "Total cadastrado: " + totalJogadores + "."
            );
        }

        if (jogadoresDisponiveis < 16) {
            throw new Copa2026Exception(
                    "A seleção " + selecao.getPais() +
                            " não possui jogadores disponíveis suficientes para participar da partida. " +
                            "Mínimo necessário: 16. Disponíveis: " + jogadoresDisponiveis + "."
            );
        }
    }
}