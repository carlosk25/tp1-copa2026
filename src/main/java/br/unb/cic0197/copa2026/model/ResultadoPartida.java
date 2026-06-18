package br.unb.cic0197.copa2026.model;

// guarda o placar e os eventos registrados de uma partida.
public class ResultadoPartida {
    private int golsA;
    private int golsB;
    private String eventos;

    // cria o resultado com gols das duas seleções e descrição dos eventos.
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
