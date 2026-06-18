package br.unb.cic0197.copa2026.model;

import java.io.Serializable;

// classe base para os perfis que conseguem acessar o sistema.
public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String email;
    private String senha;
    private String dataNascimento;
    private boolean primeiroAcesso;

    // construtor comum para todos os tipos de usuário.
    public Usuario(String nome, String email, String senha, String dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.primeiroAcesso = true;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getDataNascimento() { return dataNascimento; }

    public boolean isPrimeiroAcesso() { return primeiroAcesso; }
    public void setPrimeiroAcesso(boolean primeiroAcesso) { this.primeiroAcesso = primeiroAcesso; }

    // cada subclasse informa o nome do seu perfil no sistema.
    public abstract String getTipoPerfil();

}
