package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.model.Jogador;
import br.unb.cic0197.copa2026.model.Selecao;

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
                            (jogador.getStatus() != null ? jogador.getStatus().name() : "ATIVO") + ";" +
                            (jogador.getSelecao() != null ? jogador.getSelecao().getId() : "")
            );

            writer.newLine();
        }

        writer.close();
    }

        public List<Jogador> carregar(List<Selecao> selecoes) throws IOException {
                List<Jogador> jogadores = new ArrayList<>();

                File arquivo = new File(ARQUIVO);

                if (!arquivo.exists()) {
                        return jogadores;
                }

                BufferedReader reader = new BufferedReader(new FileReader(arquivo));

                String linha;

                while ((linha = reader.readLine()) != null) {
                        String[] dados = linha.split(";");

                        String id = dados[0];
                        String nome = dados[1];
                        String posicao = dados[2];
                        int numero = Integer.parseInt(dados[3]);
                        int idade = Integer.parseInt(dados[4]);

                        Jogador.StatusJogador status = Jogador.StatusJogador.ATIVO;
                        String selecaoId = null;

                        if (dados.length == 6) {
                                
                                selecaoId = dados[5];
                        } else if (dados.length >= 7) {
                                
                                try {
                                        status = Jogador.StatusJogador.valueOf(dados[5]);
                                } catch (IllegalArgumentException e) {
                                        status = Jogador.StatusJogador.ATIVO;
                                }
                                selecaoId = dados[6];
                        }

                        Selecao sel = null;
                        if (selecaoId != null && !selecaoId.isBlank()) {
                                for (Selecao s : selecoes) {
                                        if (s.getId().equals(selecaoId)) {
                                                sel = s;
                                                break;
                                        }
                                }
                        }

                        if (sel == null) {
                                sel = new Selecao(selecaoId == null ? "" : selecaoId, "", "", "");
                        }

                        Jogador jogador = new Jogador(
                                        id,
                                        nome,
                                        posicao,
                                        numero,
                                        idade,
                                        status,
                                        sel
                        );

                        jogadores.add(jogador);

                        if (sel != null) {
                                sel.adicionarJogador(jogador);
                        }
                }

                reader.close();

                return jogadores;
        }

        public void adicionar(Jogador jogador) throws IOException {
                List<Jogador> jogadores = carregar(new ArrayList<>());
                jogadores.add(jogador);
                salvar(jogadores);
        }

        public Jogador buscarPorId(String id) throws IOException {
                List<Jogador> jogadores = carregar(new ArrayList<>());

                for (Jogador j : jogadores) {
                        if (j.getId().equals(id)) return j;
                }

                return null;
        }

        public void remover(String id) throws IOException {
                List<Jogador> jogadores = carregar(new ArrayList<>());
                jogadores.removeIf(j -> j.getId().equals(id));
                salvar(jogadores);
        }
}