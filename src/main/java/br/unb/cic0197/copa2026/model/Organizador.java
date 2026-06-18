package br.unb.cic0197.copa2026.model;

// representa o usuário organizador, usado para acesso mais limitado às funções.
public class Organizador extends Usuario {
    private static final long serialVersionUID = 1L;

    public Organizador(String nome, String email, String senha, String dataNascimento) {
        super(nome, email, senha, dataNascimento);
    }

    // identifica este usuário como organizador.
    @Override
    public String getTipoPerfil() {
        return "Organizador";
    }
}
