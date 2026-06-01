package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.model.Selecao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SelecaoRepository {

    private static final String ARQUIVO = "selecoes.txt";

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