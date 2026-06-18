package br.unb.cic0197.copa2026.model;

import br.unb.cic0197.copa2026.enums.FaseCompeticao;
import br.unb.cic0197.copa2026.enums.StatusPartida;

import java.util.UUID;

// representa uma partida cadastrada na competição.
public class Partida {
    private String id;
    private String data;
    private String horario;
    private String estadio;
    private String selecaoA;
    private String selecaoB;
    private Arbitro arbitro;
    private FaseCompeticao fase;
    private StatusPartida status;
    private ResultadoPartida resultado;

    // cria uma partida nova gerando o id automaticamente.
    public Partida(String data, String horario, String estadio, String selecaoA, String selecaoB, FaseCompeticao fase, StatusPartida status) {
        this(UUID.randomUUID().toString(), data, horario, estadio, selecaoA, selecaoB, null, fase, status, null);
    }

    // construtor usado principalmente quando a partida vem do arquivo txt.
    public Partida(String id, String data, String horario, String estadio, String selecaoA, String selecaoB,
                   Arbitro arbitro, FaseCompeticao fase, StatusPartida status, ResultadoPartida resultado) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.estadio = estadio;
        this.selecaoA = selecaoA;
        this.selecaoB = selecaoB;
        this.arbitro = arbitro;
        this.fase = fase;
        this.status = status;
        this.resultado = resultado;
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

    public Arbitro getArbitro() {
        return arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
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

    // evita erro quando a partida ainda não possui árbitro vinculado.
    public String getArbitroNome() {
        return arbitro != null ? arbitro.getNome() : "";
    }

    // formata o resultado para aparecer na tabela da tela.
    public String getPlacarFormatado() {
        if (resultado == null) {
            return "";
        }
        return resultado.getGolsA() + " x " + resultado.getGolsB();
    }
}
