package br.unb.cic0197.copa2026.model;

import br.unb.cic0197.copa2026.enums.StatusJogador;
import java.io.Serializable;

public class Jogador implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nome;
    private String posicao;
    private int numero;
    private int idade;
    private StatusJogador status;
    private Selecao selecao;
    private String selecaoId;

    public Jogador(String id, String nome, String posicao, int numero, int idade, StatusJogador status, Selecao selecao) {
        this.id = id;
        this.nome = nome;
        this.posicao = posicao;
        this.numero = numero;
        this.idade = idade;
        this.status = status;
        this.selecao = selecao;
        this.selecaoId = selecao != null ? selecao.getId() : null;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPosicao() { return posicao; }
    public void setPosicao(String posicao) { this.posicao = posicao; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public StatusJogador getStatus() { return status; }
    public void setStatus(StatusJogador status) { this.status = status; }

    public Selecao getSelecao() { return selecao; }
    public void setSelecao(Selecao selecao) {
        this.selecao = selecao;
        this.selecaoId = selecao != null ? selecao.getId() : null;
    }

    public String getSelecaoId() {
        return selecaoId;
    }

    public void setSelecaoId(String selecaoId) {
        this.selecaoId = selecaoId;
    }

    @Override
    public String toString() {
        return "#" + numero + " " + nome + " - " + posicao + " (" + (selecao != null ? selecao.getPais() : "sem seleção") + ")";
    }
}
