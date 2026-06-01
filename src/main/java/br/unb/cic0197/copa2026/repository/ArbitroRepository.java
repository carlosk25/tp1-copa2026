package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.model.Arbitro;
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
import java.util.stream.Collectors;

public class ArbitroRepository {
    private static final Path STORAGE_PATH = JsonUtil.dataPath("arbitros.json");
    private List<Arbitro> arbitros = new ArrayList<>();

    public ArbitroRepository() {
        loadFromFile();
        if (arbitros.isEmpty()) {
            seedData();
            saveToFile();
        }
    }

    public List<Arbitro> findAll() {
        return new ArrayList<>(arbitros);
    }

    public Arbitro findByNome(String nome) {
        return arbitros.stream()
                .filter(arbitro -> arbitro.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    public void add(Arbitro arbitro) {
        arbitros.add(arbitro);
        saveToFile();
    }

    public void update(String nomeOriginal, Arbitro arbitroAtualizado) {
        for (int i = 0; i < arbitros.size(); i++) {
            Arbitro atual = arbitros.get(i);
            if (atual.getNome().equalsIgnoreCase(nomeOriginal)) {
                arbitros.set(i, arbitroAtualizado);
                saveToFile();
                return;
            }
        }
    }

    public void delete(Arbitro arbitro) {
        arbitros.removeIf(a -> a.getNome().equalsIgnoreCase(arbitro.getNome()));
        saveToFile();
    }

    public List<Arbitro> search(String nome, String nacionalidade) {
        return arbitros.stream()
                .filter(arbitro -> (nome == null || nome.isBlank() || arbitro.getNome().toLowerCase().contains(nome.toLowerCase()))
                        && (nacionalidade == null || nacionalidade.isBlank() || arbitro.getNacionalidade().toLowerCase().contains(nacionalidade.toLowerCase())))
                .collect(Collectors.toList());
    }

    private void saveToFile() {
        try {
            JsonUtil.ensureStorageDirectory(STORAGE_PATH);
            String json = JsonUtil.GSON.toJson(arbitros);
            Files.writeString(STORAGE_PATH, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Erro ao salvar árbitros: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        if (!Files.exists(STORAGE_PATH)) {
            return;
        }

        try {
            String json = Files.readString(STORAGE_PATH, StandardCharsets.UTF_8);
            Type listType = new TypeToken<List<Arbitro>>() {}.getType();
            List<Arbitro> loaded = JsonUtil.GSON.fromJson(json, listType);
            if (loaded != null) {
                arbitros = loaded;
            }
        } catch (IOException e) {
            System.err.println("Não foi possível carregar árbitros: " + e.getMessage());
        }
    }

    private void seedData() {
        arbitros.add(new Arbitro("Carlos Silva", "Brasil", "12 anos"));
        arbitros.add(new Arbitro("Jorge Martínez", "Argentina", "10 anos"));
        arbitros.add(new Arbitro("Emma Thompson", "Inglaterra", "8 anos"));
    }
}
