package br.unb.cic0197.copa2026.enums;

// estados possíveis de uma partida dentro do sistema.
public enum StatusPartida {
    AGENDADA("Agendada"),
    EM_ANDAMENTO("Em andamento"),
    FINALIZADA("Finalizada"),
    CANCELADA("Cancelada");

    private final String descricao;

    StatusPartida(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
