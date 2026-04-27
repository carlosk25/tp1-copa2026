package br.unb.cic0197.copa2026.view;

import javax.swing.*;
import java.awt.*;

public class TelaPartida extends JPanel{

    public TelaPartida() {
        setLayout(new GridLayout(0, 2, 10, 10));

        add(new JLabel("Data:"));
        JTextField txtData = new JTextField();
        add(txtData);

        add(new JLabel("Horário:"));
        JTextField txtHora = new JTextField();
        add(txtHora);

        add(new JLabel("Estádio:"));
        JTextField txtEstadio = new JTextField();
        add(txtEstadio);

        add(new JLabel("Seleção 1:"));
        JTextField txtSelecaoA = new JTextField();
        add(txtSelecaoA);

        add(new JLabel("Seleção 2:"));
        JTextField txtSelecaoB = new JTextField();
        add(txtSelecaoB);

        add(new JLabel("Fase da Competição:"));
        JComboBox<String> cbFase = new JComboBox<>(new String[]{
                "Grupos", "Oitavas", "Quartas", "Semifinal", "Final"
        });
        add(cbFase);

        add(new JLabel("Status da Partida:"));
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{
                "Agendada", "Em andamento", "Finalizada"
        });
        add(cbStatus);

        add(new JLabel("Resultado:"));
        JTextField txtResultado = new JTextField();
        add(txtResultado);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnListar = new JButton("Listar");

        add(btnSalvar);
        add(btnEditar);
        add(btnExcluir);
        add(btnListar);
    }
}
