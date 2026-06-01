package br.unb.cic0197.copa2026.enums;

public enum FaseCompeticao {
    GRUPOS("Grupos"),
    OITAVAS("Oitavas"),
    QUARTAS("Quartas"),
    SEMIFINAL("Semifinal"),
    FINAL("Final");

    private final String label;

    FaseCompeticao(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
