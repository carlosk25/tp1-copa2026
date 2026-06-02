package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.model.Arbitro;
import br.unb.cic0197.copa2026.model.DesignacaoArbitro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DesignacaoArbitroRepository {

    private static final String ARQUIVO = "designacoes.txt";

    public void salvar(List<DesignacaoArbitro> designacoes) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO));

        for (DesignacaoArbitro d : designacoes) {
            Arbitro a = d.getArbitro();
            writer.write(
                    (a != null ? a.getNome() : "") + ";" +
                            (a != null ? a.getNacionalidade() : "") + ";" +
                            (a != null ? a.getExperiencia() : "") + ";" +
                            d.getJogo()
            );
            writer.newLine();
        }

        writer.close();
    }

    public List<DesignacaoArbitro> carregar() throws IOException {
        List<DesignacaoArbitro> designacoes = new ArrayList<>();

        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return designacoes;
        }

        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        String linha;

        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");

            Arbitro a = new Arbitro(
                    dados.length > 0 ? dados[0] : "",
                    dados.length > 1 ? dados[1] : "",
                    dados.length > 2 ? dados[2] : ""
            );

            String jogo = dados.length > 3 ? dados[3] : "";

            designacoes.add(new DesignacaoArbitro(a, jogo));
        }

        reader.close();

        return designacoes;
    }

    public void adicionar(DesignacaoArbitro d) throws IOException {
        List<DesignacaoArbitro> list = carregar();
        list.add(d);
        salvar(list);
    }

    public void removerPorJogo(String jogo) throws IOException {
        List<DesignacaoArbitro> list = carregar();
        list.removeIf(d -> d.getJogo().equals(jogo));
        salvar(list);
    }
}
