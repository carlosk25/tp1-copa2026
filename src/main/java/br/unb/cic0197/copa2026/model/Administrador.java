package br.unb.cic0197.copa2026.model;


// representa o usuário com permissão mais alta no sistema.
public class Administrador extends Usuario {
    private static final long serialVersionUID = 1L;

    public Administrador(String nome, String email, String senha, String dataNascimento) {
        super(nome, email, senha, dataNascimento);
    }

    // identifica este usuário como administrador.
    @Override
    public String getTipoPerfil() {
        return "Administrador";
    }

}
