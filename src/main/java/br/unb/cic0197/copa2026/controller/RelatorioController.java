package br.unb.cic0197.copa2026.controller;

import br.unb.cic0197.copa2026.service.PartidaService;
import br.unb.cic0197.copa2026.service.JogadorService;
import java.util.ArrayList;
import java.util.List;

public class RelatorioController {
    private final PartidaService partidaService;
    private final JogadorService jogadorService;

    public RelatorioController() {
        this.partidaService = new PartidaService();
        this.jogadorService = new JogadorService();
    }

    public List<Object[]> obterDadosConsolidados() {
        List<Object[]> dadosTabela = new ArrayList<>();

       
        try {
            
            List<br.unb.cic0197.copa2026.model.Partida> partidas = partidaService.obterTodas();

            // Linha de métrica global (Resumo)
            dadosTabela.add(new Object[]{
                    "MÉTRICA GLOBAL",
                    "Total de Partidas da Competição",
                    "Copa do Mundo 2026",
                    partidas.size() + " partidas",
                    "-"
            });

            
            for (br.unb.cic0197.copa2026.model.Partida p : partidas) {
                dadosTabela.add(new Object[]{
                        "PARTIDA",
                        p.getSelecaoA() + " x " + p.getSelecaoB(), // Envolvendo as seleções
                        "Fase: " + p.getFase(),

                        p.getData()
                });
            }
        } catch (Exception e) {
            System.err.println("Erro ao consolidar partidas no controller: " + e.getMessage());
        }

        /* 
        try {
            
            List<br.unb.cic0197.copa2026.model.Jogador> jogadores = jogadorService.obterTodos();

            for (br.unb.cic0197.copa2026.model.Jogador j : jogadores) {
                dadosTabela.add(new Object[]{
                        "JOGADOR",
                        j.getNome(),
                        "Camisa Nº " + j.getNumero(),
                        j.getPosicao(),
                        j.getIdade() + " anos"
                });
            }
        } catch (Exception e) {
            System.err.println("Erro ao consolidar jogadores no controller: " + e.getMessage());
        }
        */
        return dadosTabela;
    }
}

