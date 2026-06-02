package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.model.Estadio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EstadioRepository {

    private static final String ARQUIVO = "estadios.txt";

    public void salvar(List<Estadio> estadios) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO));

        for (Estadio e : estadios) {
            writer.write(
                    e.getNome() + ";" +
                            e.getLocalizacao() + ";" +
                            e.getCapacidade()
            );
            writer.newLine();
        }

        writer.close();
    }

    public List<Estadio> carregar() throws IOException {
        List<Estadio> estadios = new ArrayList<>();

        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return estadios;
        }

        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        String linha;

        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");

            Estadio e = new Estadio(
                    dados.length > 0 ? dados[0] : "",
                    dados.length > 1 ? dados[1] : "",
                    dados.length > 2 ? Integer.parseInt(dados[2]) : 0
            );

            estadios.add(e);
        }

        reader.close();

        return estadios;
    }

    public void adicionar(Estadio estadio) throws IOException {
        List<Estadio> list = carregar();
        list.add(estadio);
        salvar(list);
    }

    public Estadio buscarPorNome(String nome) throws IOException {
        for (Estadio e : carregar()) {
            if (e.getNome().equals(nome)) return e;
        }
        return null;
    }

    public void remover(String nome) throws IOException {
        List<Estadio> list = carregar();
        list.removeIf(e -> e.getNome().equals(nome));
        salvar(list);
    }
}
