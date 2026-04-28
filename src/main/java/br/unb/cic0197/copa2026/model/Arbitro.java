package br.unb.cic0197.copa2026.model;

public class Arbitro {
    private String nome;
    private String nacionalidade;
    private String experiencia;

    public Arbitro(String nome, String nacionalidade, String experiencia){
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.experiencia = experiencia;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getNacionalidade(){
        return nacionalidade;
    }
    public void setNacionalidade(String nacionalidade){
        this.nacionalidade = nacionalidade;
    }
    public String getExperiencia(){
        return experiencia;
    }
    public void setExperiencia(String experiencia){
        this.experiencia = experiencia;
    }
}