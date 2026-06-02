package br.unb.cic0197.copa2026.model;

public class ResultadoPartida {
    private int golsA;
    private int golsB;
    private String eventos;

    public ResultadoPartida(int golsA, int golsB, String eventos) {
        this.golsA = golsA;
        this.golsB = golsB;
        this.eventos = eventos != null ? eventos : "";
    }

    public int getGolsA() {
        return golsA;
    }

    public void setGolsA(int golsA) {
        this.golsA = golsA;
    }

    public int getGolsB() {
        return golsB;
    }

    public void setGolsB(int golsB) {
        this.golsB = golsB;
    }

    public String getEventos() {
        return eventos;
    }

    public void setEventos(String eventos) {
        this.eventos = eventos != null ? eventos : "";
    }
}
