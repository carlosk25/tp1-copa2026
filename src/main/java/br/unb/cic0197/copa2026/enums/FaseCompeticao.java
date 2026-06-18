package br.unb.cic0197.copa2026.enums;

// fases possíveis da competição usadas no cadastro de partidas.
public enum FaseCompeticao {
    GRUPO("Fase de Grupo"),
    OITAVAS_DE_FINAL("Oitavas de Final"),
    QUARTAS_DE_FINAL("Quartas de Final"),
    SEMIFINAIS("Semifinais"),
    FINAL("Final");

    private final String descricao;

    FaseCompeticao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
