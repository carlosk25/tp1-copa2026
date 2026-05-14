package br.unb.cic0197.copa2026.model;

public class Jogador {
    private String id;
    private String nome;
    private String posicao;
    private int numero;
    private int idade;
    private Selecao selecao;

    public Jogador(String id, String nome, String posicao, int numero, int idade, Selecao selecao) {
        this.id = id;
        this.nome = nome;
        this.posicao = posicao;
        this.numero = numero;
        this.idade = idade;
        this.selecao = selecao;
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

    public Selecao getSelecao() { return selecao; }
    public void setSelecao(Selecao selecao) { this.selecao = selecao; }

    @Override
    public String toString() {
        return "#" + numero + " " + nome + " - " + posicao;
    }
}
