package br.unb.cic0197.copa2026.model;

public class DesignacaoArbitro {
    private Arbitro arbitro;
    private String jogo;

    public DesignacaoArbitro(Arbitro arbitro, String jogo){
        this.arbitro = arbitro;
        this.jogo = jogo;
    }

    public Arbitro getArbitro(){
        return arbitro;
    }
    public void setArbitro(Arbitro arbitro){
        this.arbitro = arbitro;
    }

    public String getJogo(){
        return jogo;
    }

    public void setJogo(String jogo){
        this.jogo = jogo;
    }
}