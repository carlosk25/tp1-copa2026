package br.unb.cic0197.copa2026.app;

import br.unb.cic0197.copa2026.model.Arbitro;
import br.unb.cic0197.copa2026.model.DesignacaoArbitro;
import br.unb.cic0197.copa2026.model.Estadio;
import br.unb.cic0197.copa2026.model.Jogador;
import br.unb.cic0197.copa2026.model.Partida;
import br.unb.cic0197.copa2026.model.Selecao;
import br.unb.cic0197.copa2026.repository.ArbitroRepository;
import br.unb.cic0197.copa2026.repository.DesignacaoArbitroRepository;
import br.unb.cic0197.copa2026.repository.EstadioRepository;
import br.unb.cic0197.copa2026.repository.JogadorRepository;
import br.unb.cic0197.copa2026.repository.PartidaRepository;
import br.unb.cic0197.copa2026.repository.SelecaoRepository;
import br.unb.cic0197.copa2026.view.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CopaApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel container;

    private final SelecaoRepository selecaoRepository;
    private final JogadorRepository jogadorRepository;
    private final EstadioRepository estadioRepository;
    private final ArbitroRepository arbitroRepository;
    private final DesignacaoArbitroRepository designacaoArbitroRepository;
    private final PartidaRepository partidaRepository;

    private List<Selecao> selecoes = new ArrayList<>();
    private List<Jogador> jogadores = new ArrayList<>();
    private List<Estadio> estadios = new ArrayList<>();
    private List<Arbitro> arbitros = new ArrayList<>();
    private List<DesignacaoArbitro> designacoes = new ArrayList<>();
    private List<Partida> partidas = new ArrayList<>();

    public CopaApp() {
        selecaoRepository = new SelecaoRepository();
        jogadorRepository = new JogadorRepository();
        estadioRepository = new EstadioRepository();
        arbitroRepository = new ArbitroRepository();
        designacaoArbitroRepository = new DesignacaoArbitroRepository();
        partidaRepository = new PartidaRepository();

        loadData();
        initUI();
        setupScreens();
        setupSaveOnClose();
    }

    public void start() {
        mostrarTela("login");
        setVisible(true);
    }

    private void loadData() {
        try {
            selecoes = selecaoRepository.carregar();
            jogadores = jogadorRepository.carregar(selecoes);
            estadios = estadioRepository.carregar();
            arbitros = arbitroRepository.carregar();
            designacoes = designacaoArbitroRepository.carregar();
            partidas = partidaRepository.findAll();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar dados: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            selecoes = new ArrayList<>();
            jogadores = new ArrayList<>();
            estadios = new ArrayList<>();
            arbitros = new ArrayList<>();
            designacoes = new ArrayList<>();
            partidas = new ArrayList<>();
        }
    }

    private void initUI() {
        setTitle("Copa 2026 - Sistema de Gerenciamento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        BufferedImage imageVizia = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        setIconImage(imageVizia);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        container.setBackground(new Color(245, 245, 245));

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            System.out.println("Usando LookAndFeel padrão");
        }
    }

    private void setupScreens() {
        container.add(new TelaLogin(this), "login");
        container.add(new TelaCadastro(this), "cadastro");
        container.add(new TelaMenu(this), "menu");
        container.add(new TelaJogadores(this), "jogadores");
        container.add(new TelaPartida(this), "partidas");
        container.add(new TelaSelecao(this), "selecoes");
        container.add(new EstadioView(this), "estadios");
        container.add(new ArbitroView(this), "arbitros");
        container.add(new TelaRelatorio(this), "relatorios");

        add(container);
    }

    private void setupSaveOnClose() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveAll();
            }
        });
    }

    public void mostrarTela(String nomeTela) {
        cardLayout.show(container, nomeTela);
    }

    public void adicionarSelecao(Selecao selecao) {
        selecoes.add(selecao);
        saveSelecoes();
    }

    public void removerSelecaoPorPais(String pais) {
        selecoes.removeIf(s -> s.getPais().equalsIgnoreCase(pais));
        saveSelecoes();
    }

    public Selecao findSelecaoPorPais(String pais) {
        for (Selecao selecao : selecoes) {
            if (selecao.getPais().equalsIgnoreCase(pais)) {
                return selecao;
            }
        }
        return null;
    }

    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
        saveJogadores();
    }

    public void removerJogadorPorNome(String nome) {
        jogadores.removeIf(j -> j.getNome().equalsIgnoreCase(nome));
        saveJogadores();
    }

    public void adicionarEstadio(Estadio estadio) {
        estadios.add(estadio);
        saveEstadios();
    }

    public void adicionarArbitro(Arbitro arbitro) {
        arbitros.add(arbitro);
        saveArbitros();
    }

    public List<Selecao> getSelecoes() {
        return selecoes;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public List<Estadio> getEstadios() {
        return estadios;
    }

    public List<Arbitro> getArbitros() {
        return arbitros;
    }

    public List<DesignacaoArbitro> getDesignacoes() {
        return designacoes;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void saveAll() {
        saveSelecoes();
        saveJogadores();
        saveEstadios();
        saveArbitros();
        saveDesignacoes();
        savePartidas();
    }

    private void saveSelecoes() {
        try {
            selecaoRepository.salvar(selecoes);
        } catch (IOException e) {
            showSaveError("seleções", e);
        }
    }

    private void saveJogadores() {
        try {
            jogadorRepository.salvar(jogadores);
        } catch (IOException e) {
            showSaveError("jogadores", e);
        }
    }

    private void saveEstadios() {
        try {
            estadioRepository.salvar(estadios);
        } catch (IOException e) {
            showSaveError("estádios", e);
        }
    }

    private void saveArbitros() {
        try {
            arbitroRepository.salvar(arbitros);
        } catch (IOException e) {
            showSaveError("árbitros", e);
        }
    }

    private void saveDesignacoes() {
        try {
            designacaoArbitroRepository.salvar(designacoes);
        } catch (IOException e) {
            showSaveError("designações", e);
        }
    }

    private void savePartidas() {
        // As partidas são salvas automaticamente pelo PartidaRepository
        // através de add/update/delete em TelaPartida via PartidaService
        // Não precisa de salvamento manual aqui
    }

    private void showSaveError(String tipo, IOException e) {
        JOptionPane.showMessageDialog(this,
                "Erro ao salvar " + tipo + ": " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public String gerarId() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CopaApp().setVisible(true);
        });
    }
}
