package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.enums.FaseCompeticao;
import br.unb.cic0197.copa2026.enums.StatusPartida;
import br.unb.cic0197.copa2026.model.Arbitro;
import br.unb.cic0197.copa2026.model.Partida;
import br.unb.cic0197.copa2026.model.ResultadoPartida;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// faz a persistência das partidas no arquivo partidas.txt.
public class PartidaRepository {
    private static final String ARQUIVO = "partidas.txt";

    // carrega todas as partidas salvas no arquivo.
    public List<Partida> findAll() {
        try {
            return carregar();
        } catch (IOException e) {
            throw new RuntimeException("Falha ao carregar partidas", e);
        }
    }

    // adiciona uma nova partida e regrava o arquivo.
    public void add(Partida partida) {
        try {
            List<Partida> partidas = carregar();
            partidas.add(partida);
            salvar(partidas);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar partida", e);
        }
    }

    // procura a partida pelo id e substitui seus dados.
    public void update(Partida partida) {
        try {
            List<Partida> partidas = carregar();
            boolean atualizado = false;
            for (int i = 0; i < partidas.size(); i++) {
                if (partidas.get(i).getId().equals(partida.getId())) {
                    partidas.set(i, partida);
                    atualizado = true;
                    break;
                }
            }
            if (!atualizado) {
                partidas.add(partida);
            }
            salvar(partidas);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao atualizar partida", e);
        }
    }

    // remove a partida selecionada pelo id.
    public void delete(Partida partida) {
        try {
            List<Partida> partidas = carregar();
            partidas.removeIf(p -> p.getId().equals(partida.getId()));
            salvar(partidas);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao excluir partida", e);
        }
    }

    public Optional<Partida> findById(String id) {
        try {
            return carregar().stream().filter(p -> p.getId().equals(id)).findFirst();
        } catch (IOException e) {
            throw new RuntimeException("Falha ao buscar partida por id", e);
        }
    }

    // filtra partidas sem alterar os dados gravados.
    public List<Partida> search(String selecao, FaseCompeticao fase, String data, String arbitro) {
        try {
            List<Partida> partidas = carregar();
            List<Partida> resultado = new ArrayList<>();
            for (Partida partida : partidas) {
                boolean matchSelecao = selecao == null || selecao.equals("Todas") || partida.getSelecaoA().equalsIgnoreCase(selecao) || partida.getSelecaoB().equalsIgnoreCase(selecao);
                boolean matchFase = fase == null || partida.getFase() == fase;
                boolean matchData = data == null || data.isBlank() || partida.getData().contains(data);
                boolean matchArbitro = arbitro == null || arbitro.equals("Todos") || partida.getArbitroNome().equalsIgnoreCase(arbitro);
                if (matchSelecao && matchFase && matchData && matchArbitro) {
                    resultado.add(partida);
                }
            }
            return resultado;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao buscar partidas", e);
        }
    }

    // lê o arquivo txt e monta os objetos partida.
    private List<Partida> carregar() throws IOException {
        List<Partida> partidas = new ArrayList<>();
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return partidas;
        }
        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        String linha;
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";", -1);
            String id = dados.length > 0 ? dados[0] : "";
            String data = dados.length > 1 ? dados[1] : "";
            String horario = dados.length > 2 ? dados[2] : "";
            String estadio = dados.length > 3 ? dados[3] : "";
            String selecaoA = dados.length > 4 ? dados[4] : "";
            String selecaoB = dados.length > 5 ? dados[5] : "";
            String arbitroNome = dados.length > 6 ? dados[6] : "";
            String faseTexto = dados.length > 7 ? dados[7] : "GRUPO";
            String statusTexto = dados.length > 8 ? dados[8] : "AGENDADA";
            int golsA = dados.length > 9 && !dados[9].isBlank() ? Integer.parseInt(dados[9]) : 0;
            int golsB = dados.length > 10 && !dados[10].isBlank() ? Integer.parseInt(dados[10]) : 0;
            String eventos = dados.length > 11 ? dados[11] : "";

            Arbitro arbitro = null;
            if (!arbitroNome.isBlank()) {
                arbitro = new Arbitro(arbitroNome, "", "");
            }
            FaseCompeticao fase = parseFase(faseTexto);
            StatusPartida status = parseStatus(statusTexto);
            ResultadoPartida resultado = new ResultadoPartida(golsA, golsB, eventos);
            if (golsA == 0 && golsB == 0 && eventos.isBlank()) {
                resultado = null;
            }

            partidas.add(new Partida(id, data, horario, estadio, selecaoA, selecaoB, arbitro, fase, status, resultado));
        }
        reader.close();
        return partidas;
    }

    // grava todas as partidas novamente no formato separado por ponto e vírgula.
    private void salvar(List<Partida> partidas) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO));
        for (Partida partida : partidas) {
            writer.write(
                    partida.getId() + ";" +
                            partida.getData() + ";" +
                            partida.getHorario() + ";" +
                            partida.getEstadio() + ";" +
                            partida.getSelecaoA() + ";" +
                            partida.getSelecaoB() + ";" +
                            (partida.getArbitro() != null ? partida.getArbitro().getNome() : "") + ";" +
                            (partida.getFase() != null ? partida.getFase().name() : "GRUPO") + ";" +
                            (partida.getStatus() != null ? partida.getStatus().name() : "AGENDADA") + ";" +
                            (partida.getResultado() != null ? partida.getResultado().getGolsA() : 0) + ";" +
                            (partida.getResultado() != null ? partida.getResultado().getGolsB() : 0) + ";" +
                            (partida.getResultado() != null ? partida.getResultado().getEventos() : "")
            );
            writer.newLine();
        }
        writer.close();
    }

    // converte o texto do arquivo para o enum de fase.
    private FaseCompeticao parseFase(String faseTexto) {
        try {
            return FaseCompeticao.valueOf(faseTexto);
        } catch (IllegalArgumentException e) {
            return FaseCompeticao.GRUPO;
        }
    }

    // converte o texto do arquivo para o enum de status.
    private StatusPartida parseStatus(String statusTexto) {
        try {
            return StatusPartida.valueOf(statusTexto);
        } catch (IllegalArgumentException e) {
            return StatusPartida.AGENDADA;
        }
    }
}
