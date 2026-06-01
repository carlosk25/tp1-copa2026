package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.model.Jogador;
import br.unb.cic0197.copa2026.model.Selecao;
import br.unb.cic0197.copa2026.util.JsonUtil;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JogadorRepository {
    private static final Path STORAGE_PATH = JsonUtil.dataPath("jogadores.json");
    private final List<Jogador> jogadores = new ArrayList<>();
    private final SelecaoRepository selecaoRepository;

    public JogadorRepository(SelecaoRepository selecaoRepository) {
        this.selecaoRepository = selecaoRepository;
        loadFromFile();
        if (jogadores.isEmpty()) {
            seedData();
            saveToFile();
        }
    }

    public List<Jogador> findAll() {
        return new ArrayList<>(jogadores);
    }

    public Optional<Jogador> findById(String id) {
        return jogadores.stream().filter(j -> j.getId().equals(id)).findFirst();
    }

    public List<Jogador> search(String selecao, String posicao, br.unb.cic0197.copa2026.enums.StatusJogador status) {
        return jogadores.stream()
                .filter(j -> (selecao == null || selecao.isBlank() || selecao.equalsIgnoreCase("Todas") || j.getSelecao() == null || j.getSelecao().getPais().equalsIgnoreCase(selecao)))
                .filter(j -> (posicao == null || posicao.isBlank() || posicao.equalsIgnoreCase("Todas") || j.getPosicao().equalsIgnoreCase(posicao)))
                .filter(j -> (status == null || j.getStatus() == status))
                .toList();
    }

    public void add(Jogador jogador) {
        jogadores.add(jogador);
        saveToFile();
    }

    public void update(Jogador jogador) {
        findById(jogador.getId()).ifPresent(existing -> {
            int index = jogadores.indexOf(existing);
            jogadores.set(index, jogador);
            saveToFile();
        });
    }

    public void delete(Jogador jogador) {
        jogadores.removeIf(j -> j.getId().equals(jogador.getId()));
        saveToFile();
    }

    private void saveToFile() {
        try {
            JsonUtil.ensureStorageDirectory(STORAGE_PATH);
            String json = JsonUtil.GSON.toJson(jogadores);
            Files.writeString(STORAGE_PATH, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Erro ao salvar jogadores: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        if (!Files.exists(STORAGE_PATH)) {
            return;
        }
        try {
            String json = Files.readString(STORAGE_PATH, StandardCharsets.UTF_8);
            Type listType = new TypeToken<List<Jogador>>() {}.getType();
            List<Jogador> loaded = JsonUtil.GSON.fromJson(json, listType);
            if (loaded != null) {
                jogadores.clear();
                jogadores.addAll(loaded);
                restoreSelecaoReferences();
            }
        } catch (IOException e) {
            System.err.println("Não foi possível carregar jogadores: " + e.getMessage());
        }
    }

    private void restoreSelecaoReferences() {
        for (Jogador jogador : jogadores) {
            if (jogador.getSelecaoId() != null) {
                selecaoRepository.findById(jogador.getSelecaoId()).ifPresent(jogador::setSelecao);
            }
        }
    }

    private void seedData() {
        List<Selecao> selecoes = selecaoRepository.findAll();
        for (Selecao selecao : selecoes) {
            for (int i = 1; i <= selecao.getJogadores().size(); i++) {
                jogadores.add(selecao.getJogadores().get(i - 1));
            }
        }
    }
}
