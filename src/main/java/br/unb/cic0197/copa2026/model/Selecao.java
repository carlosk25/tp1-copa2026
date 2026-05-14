package br.unb.cic0197.copa2026.model;

import java.util.ArrayList;
import java.util.List;

public class Selecao {
    private String id;
    private String pais;
    private String grupo;
    private String tecnico;
    private List<Jogador> jogadores;

    public Selecao(String id, String pais, String grupo, String tecnico) {
        this.id = id;
        this.pais = pais;
        this.grupo = grupo;
        this.tecnico = tecnico;
        this.jogadores = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public String getTecnico() { return tecnico; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }

    public List<Jogador> getJogadores() { return jogadores; }
    public void setJogadores(List<Jogador> jogadores) { this.jogadores = jogadores; }

    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

    public void removerJogador(Jogador jogador) {
        jogadores.remove(jogador);
    }

    @Override
    public String toString() {
        return pais + " (Grupo " + grupo + ")";
    }
}
