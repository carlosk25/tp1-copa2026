package br.unb.cic0197.copa2026.model;

public class Organizador extends Usuario {
    private static final long serialVersionUID = 1L;

    public Organizador(String nome, String email, String senha, String dataNascimento) {
        super(nome, email, senha, dataNascimento);
    }

    @Override
    public String getTipoPerfil() {
        return "Organizador";
    }
}
