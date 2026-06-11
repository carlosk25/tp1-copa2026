package br.unb.cic0197.copa2026.model;


public class Administrador extends Usuario {
    private static final long serialVersionUID = 1L;

    public Administrador(String nome, String email, String senha, String dataNascimento) {
        super(nome, email, senha, dataNascimento);
    }

    @Override
    public String getTipoPerfil() {
        return "Administrador";
    }

    @Override
    public String obterDadosMetricaConsolidada() {

        return "Acesso irrestrito: Auditoria de Logs e Modificações de Infraestrutura.";
    }
}

