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

    @Override
    public String obterDadosMetricaConsolidada() {
        // Implementação polimórfica das responsabilidades do organizador
        return "Acesso gerencial: Homologação de delegações, escalas de arbitragem e relatórios técnicos.";
    }
}
