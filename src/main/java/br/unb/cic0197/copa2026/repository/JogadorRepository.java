package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.model.Jogador;
import br.unb.cic0197.copa2026.model.Selecao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JogadorRepository {

    private static final String ARQUIVO = "jogadores.txt";

    private final SelecaoRepository selecaoRepository;

    public JogadorRepository() {
        this.selecaoRepository = new SelecaoRepository();
    }

    public List<Jogador> findAll() {
        try {
            return carregar(selecaoRepository.findAll());
        } catch (IOException e) {
            throw new RuntimeException("Falha ao carregar jogadores", e);
        }
    }

    public void add(Jogador jogador) {
        try {
            List<Jogador> jogadores = findAll();
            jogadores.add(jogador);
            salvar(jogadores);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar jogador", e);
        }
    }

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

    public List<Jogador> search(String posicao, Jogador.StatusJogador status) {
        List<Jogador> resultado = new ArrayList<>();

        for (Jogador j : findAll()) {
            boolean posicaoOk = posicao == null || posicao.isBlank() ||
                    j.getPosicao().equalsIgnoreCase(posicao);

            boolean statusOk = status == null || j.getStatus() == status;

            if (posicaoOk && statusOk) {
                resultado.add(j);
            }
        }

        return resultado;
    }

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

            if (selecao != null) {
                selecao.adicionarJogador(jogador);
            }
        }

        reader.close();
        return jogadores;
    }

    private Selecao buscarSelecaoPorId(String id, List<Selecao> selecoes) {
        for (Selecao s : selecoes) {
            if (s.getId().equals(id)) {
                return s;
            }
        }

        return null;
    }
}