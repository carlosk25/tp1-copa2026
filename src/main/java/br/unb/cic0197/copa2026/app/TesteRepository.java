//Enquanto a gente não integra a interface gráfica com a lógica, vamos testando aqui se as classes de repositório estão funcionando.


/*package br.unb.cic0197.cx'opa2026.app;

import br.unb.cic0197.copa2026.model.Selecao;
import br.unb.cic0197.copa2026.repository.SelecaoRepository;

import java.util.ArrayList;
import java.util.List;

public class TesteRepository {

    public static void main(String[] args) {

        try {

            Selecao brasil = new Selecao(
                    "1",
                    "Brasil",
                    "G",
                    "Dorival"
            );

            Selecao argentina = new Selecao(
                    "2",
                    "Argentina",
                    "C",
                    "Scaloni"
            );

            List<Selecao> selecoes = new ArrayList<>();
            selecoes.add(brasil);
            selecoes.add(argentina);

            SelecaoRepository repository =
                    new SelecaoRepository();

            repository.salvar(selecoes);

            System.out.println("Seleções salvas!");

            List<Selecao> carregadas =
                    repository.carregar();

            System.out.println("\nSeleções carregadas:");

            for (Selecao s : carregadas) {
                System.out.println(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/

package br.unb.cic0197.copa2026.app;

import br.unb.cic0197.copa2026.exception.Copa2026Exception;
import br.unb.cic0197.copa2026.model.Jogador;
import br.unb.cic0197.copa2026.model.Selecao;
import br.unb.cic0197.copa2026.repository.SelecaoRepository;
import br.unb.cic0197.copa2026.service.SelecaoService;

import java.util.ArrayList;
import java.util.List;

class TesteRepository {

    public static void main(String[] args) {

        try {
            testarRepositorySelecao();
            testarServiceSelecao();

            System.out.println("\nTodos os testes terminaram.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testarRepositorySelecao() throws Exception {
        System.out.println("=== Testando SelecaoRepository ===");

        Selecao brasil = new Selecao("1", "Brasil", "G", "Dorival");
        Selecao argentina = new Selecao("2", "Argentina", "C", "Scaloni");

        List<Selecao> selecoes = new ArrayList<>();
        selecoes.add(brasil);
        selecoes.add(argentina);

        SelecaoRepository repository = new SelecaoRepository();

        repository.salvar(selecoes);

        List<Selecao> carregadas = repository.carregar();

        for (Selecao s : carregadas) {
            System.out.println(s);
        }
    }

    private static void testarServiceSelecao() {
        System.out.println("\n=== Testando SelecaoService ===");

        Selecao brasil = new Selecao("1", "Brasil", "G", "Dorival");

        Jogador jogador1 = new Jogador(
                "1",
                "Neymar",
                "Atacante",
                10,
                33,
                Jogador.StatusJogador.ATIVO,
                brasil
        );

        Jogador jogador2 = new Jogador(
                "2",
                "Vini Jr.",
                "Meia",
                10,
                25,
                Jogador.StatusJogador.ATIVO,
                brasil
        );

        SelecaoService service = new SelecaoService();

        try {
            service.adicionarJogador(brasil, jogador1);
            System.out.println("Jogador 1 adicionado.");

            service.adicionarJogador(brasil, jogador2);
            System.out.println("Jogador 2 adicionado.");

        } catch (Copa2026Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
