package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.model.Selecao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * repositório responsável por persistir e recuperar seleções no arquivo selecoes.txt.
 * formato de cada linha: id;pais;grupo;tecnico.
 */
public class SelecaoRepository {

    // arquivo texto usado como armazenamento simples das seleções.
    private static final String ARQUIVO = "selecoes.txt";

    // carrega todas as seleções cadastradas no arquivo.
    public List<Selecao> findAll() {
        try {
            return carregar();
        } catch (IOException e) {
            throw new RuntimeException("Falha ao carregar seleções", e);
        }
    }

    // adiciona uma nova seleção preservando as seleções já cadastradas.
    public void add(Selecao selecao) {
        try {
            List<Selecao> selecoes = carregar();
            selecoes.add(selecao);
            salvar(selecoes);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar seleção", e);
        }
    }

    // atualiza a seleção pelo ID, mantendo o mesmo registro lógico.
    public void update(Selecao selecao) {
        try {
            List<Selecao> selecoes = carregar();

            for (int i = 0; i < selecoes.size(); i++) {
                if (selecoes.get(i).getId().equals(selecao.getId())) {
                    selecoes.set(i, selecao);
                    salvar(selecoes);
                    return;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Falha ao atualizar seleção", e);
        }
    }

    // remove a seleção pelo ID e regrava o arquivo.
    public void delete(Selecao selecao) {
        try {
            List<Selecao> selecoes = carregar();
            selecoes.removeIf(s -> s.getId().equals(selecao.getId()));
            salvar(selecoes);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao excluir seleção", e);
        }
    }

    public Optional<Selecao> findById(String id) {
        try {
            return carregar()
                    .stream()
                    .filter(s -> s.getId().equals(id))
                    .findFirst();
        } catch (IOException e) {
            throw new RuntimeException("Falha ao buscar seleção por id", e);
        }
    }

    // filtra seleções por grupo. Quando o grupo vem vazio, retorna todas.
    public List<Selecao> search(String grupo) {
        List<Selecao> resultado = new ArrayList<>();

        for (Selecao s : findAll()) {
            if (grupo == null || grupo.isBlank() ||
                    s.getGrupo().equalsIgnoreCase(grupo)) {
                resultado.add(s);
            }
        }

        return resultado;
    }

    // busca usada para associar partidas/jogadores pelo nome do país.
    public Optional<Selecao> findByPais(String pais) {
        return findAll()
                .stream()
                .filter(s -> s.getPais().equalsIgnoreCase(pais))
                .findFirst();
    }

    // regrava todo o arquivo selecoes.txt com a lista atualizada.
    public void salvar(List<Selecao> selecoes) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO));

        for (Selecao selecao : selecoes) {
            writer.write(
                    selecao.getId() + ";" +
                            selecao.getPais() + ";" +
                            selecao.getGrupo() + ";" +
                            selecao.getTecnico()
            );
            writer.newLine();
        }

        writer.close();
    }

    // lê o arquivo de seleções e monta os objetos usados pelo sistema.
    public List<Selecao> carregar() throws IOException {
        List<Selecao> selecoes = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return selecoes;
        }

        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        String linha;

        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");

            Selecao selecao = new Selecao(
                    dados[0],
                    dados[1],
                    dados[2],
                    dados[3]
            );

            selecoes.add(selecao);
        }

        reader.close();
        return selecoes;
    }
}