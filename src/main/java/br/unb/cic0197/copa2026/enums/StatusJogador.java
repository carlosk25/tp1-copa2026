package br.unb.cic0197.copa2026.enums;

public enum StatusJogador {
    ATIVO("Ativo"),
    LESIONADO("Lesionado"),
    SUSPENSO("Suspenso");

    private final String label;

    StatusJogador(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
