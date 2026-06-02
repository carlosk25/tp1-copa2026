package br.unb.cic0197.copa2026.model;

import java.io.Serializable;

public class SolicitacaoCadastro implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String email;
    private String dataNascimento;
    private String tipoPerfilSolicitado;

    public SolicitacaoCadastro(String nome, String email, String dataNascimento, String tipoPerfilSolicitado) {
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.tipoPerfilSolicitado = tipoPerfilSolicitado;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getDataNascimento() { return dataNascimento; }
    public String getTipoPerfilSolicitado() { return tipoPerfilSolicitado; }
}
