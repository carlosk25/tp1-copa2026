package br.unb.cic0197.copa2026.enums;

public enum StatusPartida {
    AGENDADA("Agendada"),
    EM_ANDAMENTO("Em andamento"),
    FINALIZADA("Finalizada");

    private final String label;

    StatusPartida(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
