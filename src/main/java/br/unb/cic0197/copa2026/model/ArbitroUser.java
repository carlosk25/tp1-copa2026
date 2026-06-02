package br.unb.cic0197.copa2026.model;

public class ArbitroUser extends Usuario {
    private static final long serialVersionUID = 1L;

    public ArbitroUser (String nome, String email, String senha, String dataNascimento) {
        super(nome, email, senha, dataNascimento);
    }

    @Override
    public String getTipoPerfil() {
        return "Arbitro";
    }

    @Override
    public String obterDadosMetricaConsolidada() {
        // Implementação polimórfica dos detalhes funcionais
        return "Acesso restrito: Lançamento operacional de chaves, placares e atletas.";
    }
}
