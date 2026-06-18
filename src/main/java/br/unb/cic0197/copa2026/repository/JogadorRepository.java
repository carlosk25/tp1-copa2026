package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.model.Jogador;
import br.unb.cic0197.copa2026.model.Selecao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * repositório responsável por persistir e recuperar jogadores no arquivo jogadores.txt.
 * formato de cada linha: id;nome;posicao;numero;idade;status;idselecao.
 */
public class JogadorRepository {

    // arquivo texto usado como armazenamento simples dos jogadores.
    private static final String ARQUIVO = "jogadores.txt";

    private final SelecaoRepository selecaoRepository;

    public JogadorRepository() {
        this.selecaoRepository = new SelecaoRepository();
    }

    // carrega todos os jogadores e já associa cada um à seleção correspondente.
    public List<Jogador> findAll() {
        try {
            return carregar(selecaoRepository.findAll());
        } catch (IOException e) {
            throw new RuntimeException("Falha ao carregar jogadores", e);
        }
    }

    // adiciona um jogador novo mantendo os registros anteriores do arquivo.
    public void add(Jogador jogador) {
        try {
            List<Jogador> jogadores = findAll();
            jogadores.add(jogador);
            salvar(jogadores);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar jogador", e);
        }
    }

    // atualiza o jogador pelo ID. Se não encontrar, adiciona como segurança.
    public void update(Jogador jogador) {
        try {
            List<Jogador> jogadores = findAll();
            boolean atualizado = false;

            for (int i = 0; i < jogadores.size(); i++) {
                if (jogadores.get(i).getId().equals(jogador.getId())) {
                    jogadores.set(i, jogador);
                    atualizado = true;
                    break;
                }
            }

            if (!atualizado) {
                jogadores.add(jogador);
            }

            salvar(jogadores);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao atualizar jogador", e);
        }
    }

    // remove o jogador pelo ID e regrava o arquivo.
    public void delete(Jogador jogador) {
        try {
            List<Jogador> jogadores = findAll();
            jogadores.removeIf(j -> j.getId().equals(jogador.getId()));
            salvar(jogadores);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao excluir jogador", e);
        }
    }

    public Optional<Jogador> findById(String id) {
        return findAll()
                .stream()
                .filter(j -> j.getId().equals(id))
                .findFirst();
    }

    public Optional<Jogador> findByNome(String nome) {
        return findAll()
                .stream()
                .filter(j -> j.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    // busca combinando filtros opcionais; filtros nulos ou vazios são ignorados.
    public List<Jogador> search(
            String posicao,
            String paisSelecao,
            Jogador.StatusJogador status,
            Integer numero,
            Integer idade) {

        List<Jogador> resultado = new ArrayList<>();

        for (Jogador jogador : findAll()) {
            boolean posicaoOk =
                    posicao == null ||
                            posicao.isBlank() ||
                            jogador.getPosicao().equalsIgnoreCase(posicao);

            boolean selecaoOk =
                    paisSelecao == null ||
                            paisSelecao.isBlank() ||
                            jogador.getSelecao().getPais().equalsIgnoreCase(paisSelecao);

            boolean statusOk =
                    status == null ||
                            jogador.getStatus() == status;

            boolean numeroOk =
                    numero == null ||
                            jogador.getNumero() == numero;

            boolean idadeOk =
                    idade == null ||
                            jogador.getIdade() == idade;

            if (posicaoOk && selecaoOk && statusOk && numeroOk && idadeOk) {
                resultado.add(jogador);
            }
        }

        return resultado;
    }

    // regrava todo o arquivo jogadores.txt com a lista atualizada.
    public void salvar(List<Jogador> jogadores) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO));

        for (Jogador jogador : jogadores) {
            writer.write(
                    jogador.getId() + ";" +
                            jogador.getNome() + ";" +
                            jogador.getPosicao() + ";" +
                            jogador.getNumero() + ";" +
                            jogador.getIdade() + ";" +
                            jogador.getStatus().name() + ";" +
                            jogador.getSelecao().getId()
            );
            writer.newLine();
        }

        writer.close();
    }

    // lê o arquivo e reconstrói os objetos Jogador, ligando cada um à seleção pelo idSelecao.
    public List<Jogador> carregar(List<Selecao> selecoes) throws IOException {
        List<Jogador> jogadores = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return jogadores;
        }

        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        String linha;

        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");

            String id = dados[0];
            String nome = dados[1];
            String posicao = dados[2];
            int numero = Integer.parseInt(dados[3]);
            int idade = Integer.parseInt(dados[4]);
            Jogador.StatusJogador status = Jogador.StatusJogador.valueOf(dados[5]);
            String selecaoId = dados[6];

            // o arquivo guarda somente o ID da seleção; aqui o ID é convertido novamente em objeto Selecao.
            Selecao selecao = buscarSelecaoPorId(selecaoId, selecoes);

            Jogador jogador = new Jogador(
                    id,
                    nome,
                    posicao,
                    numero,
                    idade,
                    status,
                    selecao
            );

            jogadores.add(jogador);

            // também atualiza a lista interna da seleção para permitir validações de elenco.
            if (selecao != null) {
                selecao.adicionarJogador(jogador);
            }
        }

        reader.close();
        return jogadores;
    }

    // procura a seleção carregada que corresponde ao ID informado no jogadores.txt.
    private Selecao buscarSelecaoPorId(String id, List<Selecao> selecoes) {
        for (Selecao s : selecoes) {
            if (s.getId().equals(id)) {
                return s;
            }
        }

        return null;
    }
}