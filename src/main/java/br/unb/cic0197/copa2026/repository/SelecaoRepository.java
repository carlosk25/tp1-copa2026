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

public class SelecaoRepository {
    private static final Path STORAGE_PATH = JsonUtil.dataPath("selecoes.json");
    private List<Selecao> selecoes = new ArrayList<>();

    public SelecaoRepository() {
        loadFromFile();
        if (selecoes.isEmpty()) {
            seedData();
            saveToFile();
        }
    }

    public List<Selecao> findAll() {
        return new ArrayList<>(selecoes);
    }

    public List<Selecao> search(String grupo) {
        if (grupo == null || grupo.isBlank() || grupo.equalsIgnoreCase("Todas")) {
            return findAll();
        }
        return selecoes.stream()
                .filter(s -> s.getGrupo().equalsIgnoreCase(grupo))
                .toList();
    }

    public Optional<Selecao> findById(String id) {
        return selecoes.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public void add(Selecao selecao) {
        selecoes.add(selecao);
        saveToFile();
    }

    public void update(Selecao selecao) {
        findById(selecao.getId()).ifPresent(existing -> {
            int index = selecoes.indexOf(existing);
            selecoes.set(index, selecao);
            saveToFile();
        });
    }

    public void delete(Selecao selecao) {
        selecoes.removeIf(s -> s.getId().equals(selecao.getId()));
        saveToFile();
    }

    private void saveToFile() {
        try {
            JsonUtil.ensureStorageDirectory(STORAGE_PATH);
            String json = JsonUtil.GSON.toJson(selecoes);
            Files.writeString(STORAGE_PATH, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Erro ao salvar seleções: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        if (!Files.exists(STORAGE_PATH)) {
            return;
        }
        try {
            String json = Files.readString(STORAGE_PATH, StandardCharsets.UTF_8);
            Type listType = new TypeToken<List<Selecao>>() {}.getType();
            List<Selecao> loaded = JsonUtil.GSON.fromJson(json, listType);
            if (loaded != null) {
                selecoes = loaded;
                for (Selecao selecao : selecoes) {
                    for (Jogador jogador : selecao.getJogadores()) {
                        jogador.setSelecao(selecao);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Não foi possível carregar seleções: " + e.getMessage());
        }
    }

    private void seedData() {
        Selecao brasil = new Selecao("BRA", "Brasil", "A", "Tite");
        brasil.adicionarJogador(new Jogador("BRA01", "Alisson Becker", "Goleiro", 1, 31, br.unb.cic0197.copa2026.enums.StatusJogador.ATIVO, brasil));
        brasil.adicionarJogador(new Jogador("BRA02", "Neymar Jr.", "Atacante", 10, 32, br.unb.cic0197.copa2026.enums.StatusJogador.ATIVO, brasil));
        brasil.adicionarJogador(new Jogador("BRA03", "Casemiro", "Volante", 5, 30, br.unb.cic0197.copa2026.enums.StatusJogador.ATIVO, brasil));

        Selecao argentina = new Selecao("ARG", "Argentina", "B", "Lionel Scaloni");
        argentina.adicionarJogador(new Jogador("ARG01", "Emiliano Martinez", "Goleiro", 23, 30, br.unb.cic0197.copa2026.enums.StatusJogador.ATIVO, argentina));
        argentina.adicionarJogador(new Jogador("ARG02", "Lionel Messi", "Atacante", 10, 36, br.unb.cic0197.copa2026.enums.StatusJogador.ATIVO, argentina));
        argentina.adicionarJogador(new Jogador("ARG03", "Rodrigo De Paul", "Meio-Campo", 7, 28, br.unb.cic0197.copa2026.enums.StatusJogador.ATIVO, argentina));

        Selecao franca = new Selecao("FRA", "França", "C", "Didier Deschamps");
        franca.adicionarJogador(new Jogador("FRA01", "Hugo Lloris", "Goleiro", 1, 35, br.unb.cic0197.copa2026.enums.StatusJogador.ATIVO, franca));
        franca.adicionarJogador(new Jogador("FRA02", "Kylian Mbappé", "Atacante", 10, 25, br.unb.cic0197.copa2026.enums.StatusJogador.ATIVO, franca));
        franca.adicionarJogador(new Jogador("FRA03", "N'Golo Kanté", "Volante", 13, 31, br.unb.cic0197.copa2026.enums.StatusJogador.ATIVO, franca));

        Selecao portugal = new Selecao("POR", "Portugal", "D", "Roberto Martinez");
        portugal.adicionarJogador(new Jogador("POR01", "Diogo Costa", "Goleiro", 1, 24, br.unb.cic0197.copa2026.enums.StatusJogador.ATIVO, portugal));
        portugal.adicionarJogador(new Jogador("POR02", "Cristiano Ronaldo", "Atacante", 7, 39, br.unb.cic0197.copa2026.enums.StatusJogador.ATIVO, portugal));
        portugal.adicionarJogador(new Jogador("POR03", "Bruno Fernandes", "Meio-Campo", 8, 28, br.unb.cic0197.copa2026.enums.StatusJogador.ATIVO, portugal));

        selecoes.add(brasil);
        selecoes.add(argentina);
        selecoes.add(franca);
        selecoes.add(portugal);
    }
}
