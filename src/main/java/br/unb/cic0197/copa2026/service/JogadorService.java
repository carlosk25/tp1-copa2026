package br.unb.cic0197.copa2026.service;

import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Jogador;
import br.unb.cic0197.copa2026.repository.JogadorRepository;

import java.util.List;
import java.util.Optional;

/**
 * camada de regras de negócio dos jogadores.
 * aqui ficam as validações antes de qualquer alteração ser salva no repository.
 */
public class JogadorService {

    private final JogadorRepository repository;

    public JogadorService() {
        this.repository = new JogadorRepository();
    }

    public List<Jogador> obterTodos() {
        return repository.findAll();
    }

    // fluxo de cadastro: valida os dados, verifica camisa repetida e salva.
    public void salvar(Jogador jogador) throws Copa2026Exception {
        validarJogador(jogador);
        validarNumeroRepetido(jogador);
        repository.add(jogador);
    }

    // fluxo de edição: valida, confirma existência do jogador e atualiza pelo ID.
    public void atualizar(Jogador jogador) throws Copa2026Exception {
        validarJogador(jogador);

        Optional<Jogador> existente = repository.findById(jogador.getId());

        if (existente.isEmpty()) {
            throw new Copa2026Exception("Jogador não encontrado para atualização.");
        }

        validarNumeroRepetido(jogador);
        repository.update(jogador);
    }

    // remove somente jogadores válidos e já existentes.
    public void remover(Jogador jogador) throws Copa2026Exception {
        if (jogador == null || jogador.getId() == null || jogador.getId().isBlank()) {
            throw new Copa2026Exception("Jogador inválido para exclusão.");
        }

        Optional<Jogador> existente = repository.findById(jogador.getId());

        if (existente.isEmpty()) {
            throw new Copa2026Exception("Jogador não encontrado para exclusão.");
        }

        repository.delete(jogador);
    }

    public void removerPorNome(String nome) throws Copa2026Exception {
        Optional<Jogador> jogador = buscarPorNome(nome);

        if (jogador.isEmpty()) {
            throw new Copa2026Exception("Jogador não encontrado para exclusão.");
        }

        repository.delete(jogador.get());
    }

    public Optional<Jogador> obterPorId(String id) {
        return repository.findById(id);
    }

    // encaminha os filtros da tela para o Repository.
    public List<Jogador> buscar(
            String posicao,
            String paisSelecao,
            Jogador.StatusJogador status,
            Integer numero,
            Integer idade) {

        return repository.search(
                posicao,
                paisSelecao,
                status,
                numero,
                idade
        );
    }

    public Optional<Jogador> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return Optional.empty();
        }

        return repository.findByNome(nome);
    }

    // valida os campos obrigatórios e as regras básicas de cadastro do jogador.
    public void validarJogador(Jogador jogador)
            throws Copa2026Exception {

        if (jogador == null) {
            throw new Copa2026Exception("Jogador inválido.");
        }

        if (jogador.getNome() == null || jogador.getNome().isBlank()) {
            throw new Copa2026Exception("Nome do jogador não pode ser vazio.");
        }

        if (jogador.getPosicao() == null || jogador.getPosicao().isBlank()) {
            throw new Copa2026Exception("Posição do jogador não pode ser vazia.");
        }

        if (jogador.getIdade() < 15 || jogador.getIdade() > 50) {
            throw new Copa2026Exception("Idade inválida.");
        }

        if (jogador.getNumero() < 1 || jogador.getNumero() > 26) {
            throw new Copa2026Exception("O número da camisa deve estar entre 1 e 26.");
        }

        if (jogador.getStatus() == null) {
            throw new Copa2026Exception("Status do jogador é obrigatório.");
        }

        if (jogador.getSelecao() == null) {
            throw new Copa2026Exception("O jogador deve estar vinculado a uma seleção.");
        }
    }

    // regra de negócio: uma camisa não pode se repetir dentro da mesma seleção.
    // a mesma camisa pode existir em seleções diferentes.
    private void validarNumeroRepetido(Jogador jogador)
            throws Copa2026Exception {

        for (Jogador existente : repository.findAll()) {
            boolean mesmaSelecao =
                    existente.getSelecao() != null &&
                            jogador.getSelecao() != null &&
                            existente.getSelecao().getId().equals(jogador.getSelecao().getId());

            boolean mesmoNumero =
                    existente.getNumero() == jogador.getNumero();

            boolean jogadorDiferente =
                    !existente.getId().equals(jogador.getId());

            if (mesmaSelecao && mesmoNumero && jogadorDiferente) {
                throw new Copa2026Exception(
                        "Este número já está sendo utilizado por outro jogador da mesma seleção."
                );
            }
        }
    }
}