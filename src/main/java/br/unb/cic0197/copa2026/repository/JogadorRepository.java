package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.model.Jogador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JogadorRepository {

    private static final String ARQUIVO = "jogadores.txt";

    public void salvar(List<Jogador> jogadores)
            throws IOException {

        BufferedWriter writer =
                new BufferedWriter(
                        new FileWriter(ARQUIVO)
                );

        for (Jogador jogador : jogadores) {

            writer.write(
                    jogador.getId() + ";" +
                            jogador.getNome() + ";" +
                            jogador.getPosicao() + ";" +
                            jogador.getNumero() + ";" +
                            jogador.getIdade() + ";" +
                            jogador.getSelecao().getId()
            );

            writer.newLine();
        }

        writer.close();
    }
}