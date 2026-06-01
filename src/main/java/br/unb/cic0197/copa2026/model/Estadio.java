package br.unb.cic0197.copa2026.model;

import java.io.Serializable;

public class Estadio implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String localizacao;
    private int capacidade;

    public Estadio(String nome, String localizacao, int capacidade){
        this.nome = nome;
        this.localizacao = localizacao;
        this.capacidade = capacidade;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getLocalizacao(){
        return localizacao;
    }
    public void setLocalizacao(String localizacao){
        this.localizacao = localizacao;
    }
    public int getCapacidade(){
        return capacidade;
    }
    public void setCapacidade(int capacidade){
        this.capacidade = capacidade;
    }
}