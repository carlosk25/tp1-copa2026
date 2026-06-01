package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.enums.FaseCompeticao;
import br.unb.cic0197.copa2026.model.Partida;
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
import java.util.stream.Collectors;

public class PartidaRepository {
    private static final Path STORAGE_PATH = JsonUtil.dataPath("partidas.json");
    private List<Partida> partidas = new ArrayList<>();

    public PartidaRepository() {
        loadFromFile();
    }

    public List<Partida> findAll() {
        return new ArrayList<>(partidas);
    }

    public Optional<Partida> findById(String id) {
        return partidas.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public void add(Partida partida) {
        partidas.add(partida);
        saveToFile();
    }

    public void update(Partida partida) {
        findById(partida.getId()).ifPresent(existing -> {
            int index = partidas.indexOf(existing);
            partidas.set(index, partida);
            saveToFile();
        });
    }

    public void delete(Partida partida) {
        partidas.removeIf(p -> p.getId().equals(partida.getId()));
        saveToFile();
    }

    public List<Partida> search(String selecao, FaseCompeticao fase, String data, String arbitro) {
        return partidas.stream()
                .filter(partida -> {
                    boolean matchesSelecao = selecao == null || selecao.isBlank() || "Todas".equalsIgnoreCase(selecao)
                            || partida.envolveSelecao(selecao);
                    boolean matchesFase = fase == null || partida.getFase() == fase;
                    boolean matchesData = data == null || data.isBlank() || partida.getData().equalsIgnoreCase(data.trim());
                    boolean matchesArbitro = arbitro == null || arbitro.isBlank() || "Todos".equalsIgnoreCase(arbitro)
                            || (partida.getArbitro() != null && partida.getArbitro().getNome().equalsIgnoreCase(arbitro));
                    return matchesSelecao && matchesFase && matchesData && matchesArbitro;
                })
                .collect(Collectors.toList());
    }

    private void saveToFile() {
        try {
            JsonUtil.ensureStorageDirectory(STORAGE_PATH);
            String json = JsonUtil.GSON.toJson(partidas);
            Files.writeString(STORAGE_PATH, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Erro ao salvar partidas: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        if (!Files.exists(STORAGE_PATH)) {
            return;
        }

        try {
            String json = Files.readString(STORAGE_PATH, StandardCharsets.UTF_8);
            Type listType = new TypeToken<List<Partida>>() {}.getType();
            List<Partida> loaded = JsonUtil.GSON.fromJson(json, listType);
            if (loaded != null) {
                partidas = loaded;
            }
        } catch (IOException e) {
            System.err.println("Não foi possível carregar partidas: " + e.getMessage());
        }
    }
}
