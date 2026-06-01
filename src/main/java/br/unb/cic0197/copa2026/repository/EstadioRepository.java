package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.model.Estadio;
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

public class EstadioRepository {
    private static final Path STORAGE_PATH = JsonUtil.dataPath("estadios.json");
    private List<Estadio> estadios = new ArrayList<>();

    public EstadioRepository() {
        loadFromFile();
        if (estadios.isEmpty()) {
            seedData();
            saveToFile();
        }
    }

    public List<Estadio> findAll() {
        return new ArrayList<>(estadios);
    }

    public Estadio findByNome(String nome) {
        return estadios.stream()
                .filter(estadio -> estadio.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    public void add(Estadio estadio) {
        estadios.add(estadio);
        saveToFile();
    }

    public void update(String nomeOriginal, Estadio estadioAtualizado) {
        for (int i = 0; i < estadios.size(); i++) {
            Estadio atual = estadios.get(i);
            if (atual.getNome().equalsIgnoreCase(nomeOriginal)) {
                estadios.set(i, estadioAtualizado);
                saveToFile();
                return;
            }
        }
    }

    public void delete(Estadio estadio) {
        estadios.removeIf(e -> e.getNome().equalsIgnoreCase(estadio.getNome()));
        saveToFile();
    }

    public List<Estadio> search(String nome, String localizacao) {
        return estadios.stream()
                .filter(estadio -> (nome == null || nome.isBlank() || estadio.getNome().toLowerCase().contains(nome.toLowerCase()))
                        && (localizacao == null || localizacao.isBlank() || estadio.getLocalizacao().toLowerCase().contains(localizacao.toLowerCase())))
                .collect(Collectors.toList());
    }

    private void saveToFile() {
        try {
            JsonUtil.ensureStorageDirectory(STORAGE_PATH);
            String json = JsonUtil.GSON.toJson(estadios);
            Files.writeString(STORAGE_PATH, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Erro ao salvar estádios: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        if (!Files.exists(STORAGE_PATH)) {
            return;
        }
        try {
            String json = Files.readString(STORAGE_PATH, StandardCharsets.UTF_8);
            Type listType = new TypeToken<List<Estadio>>() {}.getType();
            List<Estadio> loaded = JsonUtil.GSON.fromJson(json, listType);
            if (loaded != null) {
                estadios = loaded;
            }
        } catch (IOException e) {
            System.err.println("Não foi possível carregar estádios: " + e.getMessage());
        }
    }

    private void seedData() {
        estadios.add(new Estadio("Estádio Nacional", "Cidade A", 65000));
        estadios.add(new Estadio("Arena Central", "Cidade B", 53000));
        estadios.add(new Estadio("Estádio Olímpico", "Cidade C", 47000));
    }
}
