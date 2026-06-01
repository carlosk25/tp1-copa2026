package br.unb.cic0197.copa2026.model;

import java.io.Serializable;
import java.util.UUID;

import br.unb.cic0197.copa2026.enums.FaseCompeticao;
import br.unb.cic0197.copa2026.enums.StatusPartida;

public class Partida implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String data;
    private String horario;
    private String estadio;
    private String selecaoA;
    private String selecaoB;
    private FaseCompeticao fase;
    private StatusPartida status;
    private ResultadoPartida resultado;
    private Arbitro arbitro;

    public Partida(String data, String horario, String estadio, String selecaoA, String selecaoB,
                   FaseCompeticao fase, StatusPartida status) {
        this.id = UUID.randomUUID().toString();
        this.data = data;
        this.horario = horario;
        this.estadio = estadio;
        this.selecaoA = selecaoA;
        this.selecaoB = selecaoB;
        this.fase = fase;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getSelecaoA() {
        return selecaoA;
    }

    public void setSelecaoA(String selecaoA) {
        this.selecaoA = selecaoA;
    }

    public String getSelecaoB() {
        return selecaoB;
    }

    public void setSelecaoB(String selecaoB) {
        this.selecaoB = selecaoB;
    }

    public FaseCompeticao getFase() {
        return fase;
    }

    public void setFase(FaseCompeticao fase) {
        this.fase = fase;
    }

    public StatusPartida getStatus() {
        return status;
    }

    public void setStatus(StatusPartida status) {
        this.status = status;
    }

    public ResultadoPartida getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoPartida resultado) {
        this.resultado = resultado;
    }

    public Arbitro getArbitro() {
        return arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
    }

    public String getArbitroNome() {
        return arbitro == null ? "-" : arbitro.getNome();
    }

    public String getPlacarFormatado() {
        if (resultado == null) {
            return "-";
        }
        return resultado.getGolsA() + " x " + resultado.getGolsB();
    }

    public boolean envolveSelecao(String selecao) {
        if (selecao == null || selecao.isBlank()) {
            return false;
        }
        return selecaoA.equalsIgnoreCase(selecao) || selecaoB.equalsIgnoreCase(selecao);
    }
}
