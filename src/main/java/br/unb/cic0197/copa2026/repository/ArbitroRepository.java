package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.model.Arbitro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArbitroRepository {

    private static final String ARQUIVO = "arbitros.txt";

    public void salvar(List<Arbitro> arbitros) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO));

        for (Arbitro a : arbitros) {
            writer.write(
                    a.getNome() + ";" +
                            a.getNacionalidade() + ";" +
                            a.getExperiencia()
            );
            writer.newLine();
        }

        writer.close();
    }

    public List<Arbitro> carregar() throws IOException {
        List<Arbitro> arbitros = new ArrayList<>();

        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return arbitros;
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

            arbitros.add(a);
        }

        reader.close();

        return arbitros;
    }

    public void adicionar(Arbitro arbitro) throws IOException {
        List<Arbitro> list = carregar();
        list.add(arbitro);
        salvar(list);
    }

    public Arbitro buscarPorNome(String nome) throws IOException {
        for (Arbitro a : carregar()) {
            if (a.getNome().equals(nome)) return a;
        }
        return null;
    }

    public void remover(String nome) throws IOException {
        List<Arbitro> list = carregar();
        list.removeIf(a -> a.getNome().equals(nome));
        salvar(list);
    }
}
