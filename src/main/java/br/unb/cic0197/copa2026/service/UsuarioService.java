package br.unb.cic0197.copa2026.service;

import br.unb.cic0197.copa2026.model.Usuario;
import br.unb.cic0197.copa2026.model.SolicitacaoCadastro;
import br.unb.cic0197.copa2026.model.Administrador;
import br.unb.cic0197.copa2026.model.Organizador;
import br.unb.cic0197.copa2026.model.ArbitroUser;
import br.unb.cic0197.copa2026.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import br.unb.cic0197.copa2026.model.*;
import br.unb.cic0197.copa2026.exception.UsuarioInvalidoException;

// concentra as regras de negócio de login, cadastro e gestão de usuários.
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService() {
        this.repository = new UsuarioRepository(); 
        inicializarAdminPadrao();                 
    }

    // garante que o sistema tenha pelo menos um administrador para começar o uso.
    private void inicializarAdminPadrao() {
        String emailAdmin = "master@copa.com";

        if (!repository.findById(emailAdmin).isPresent()) {
            // cria o administrador usando o construtor correto de 4 argumentos
            Administrador adminPadrao = new Administrador(
                    "Administrador Geral",
                    emailAdmin,
                    "admin123",
                    "01/01/1990"
            );

            // salva usando o método correto do seu repositório (save)
            repository.add(adminPadrao);
            System.out.println("🚀 Admin padrão pré-carregado via Camada de Serviço!");
        }
    }

    public List<Usuario> obtertodas() {
        return repository.findAll();
    }


    // salva um usuário diretamente no repositório.
    public void salvar(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            repository.add(usuario);
        } else {
            Optional<Usuario> existente = repository.findById(usuario.getEmail());
            if (!existente.isPresent()) {
                repository.add(usuario);
            } else {
                repository.update(usuario.getEmail(), usuario);
            }
        }
    }

    // valida email e senha informados na tela de login.
    public Usuario autenticar (String email, String senha) throws UsuarioInvalidoException {
        Optional<Usuario> usuarioOpt = repository.findById(email);
        if (!usuarioOpt.isPresent() || !usuarioOpt.get().getSenha().equals(senha)) {
            throw new UsuarioInvalidoException("E-mail ou senha inválidos!");
        }
        return usuarioOpt.get();
    }

    // cria uma solicitação de cadastro quando o email ainda não existe.
    public void cadastrarSolicitacao(SolicitacaoCadastro solicitacao) throws Exception {
        // verifica se o email já não está em uso no sistema
        if (repository.findById(solicitacao.getEmail()).isPresent()) {
            throw new UsuarioInvalidoException("E-mail já cadastrado no sistema!");
        }

        List<SolicitacaoCadastro> solicitacoes = repository.findAllSolicitacoes();
        boolean duplicado = solicitacoes.stream().anyMatch(s -> s.getEmail().equalsIgnoreCase(solicitacao.getEmail()));
        if (duplicado) {
            throw new UsuarioInvalidoException("Já existe uma solicitação pendente para este e-mail!");
        }

        solicitacoes.add(solicitacao);
        repository.salvarTodasSolicitacoes(solicitacoes);
    }


    // transforma a solicitação aprovada em usuário definitivo.
    public String aprovarSolicitacao(SolicitacaoCadastro solicitacao) {
        // pega a senha real definida pelo usuário no momento do cadastro
        String senhaCadastro = solicitacao.getSenha();

        Usuario novoUsuario;
        if (solicitacao.getTipoPerfilSolicitado().equalsIgnoreCase("Administrador")) {
            novoUsuario = new Administrador(solicitacao.getNome(), solicitacao.getEmail(), senhaCadastro, solicitacao.getDataNascimento());
        } else if (solicitacao.getTipoPerfilSolicitado().equalsIgnoreCase("Organizador")) {
            novoUsuario = new Organizador(solicitacao.getNome(), solicitacao.getEmail(), senhaCadastro, solicitacao.getDataNascimento());
        } else {
            novoUsuario = new ArbitroUser(solicitacao.getNome(), solicitacao.getEmail(), senhaCadastro, solicitacao.getDataNascimento());
        }


        novoUsuario.setPrimeiroAcesso(false);

        // grava o novo usuário ativo no repositório/arquivo
        repository.add(novoUsuario);

        // remove a solicitação pendente do arquivo de solicitações
        List<SolicitacaoCadastro> solicitacoes = repository.findAllSolicitacoes();
        solicitacoes.removeIf(s -> s.getEmail().equalsIgnoreCase(solicitacao.getEmail()));
        repository.salvarTodasSolicitacoes(solicitacoes);

        // retorna a própria senha utilizada para fins de exibição na tela
        return senhaCadastro;
    }
    // remove a solicitação sem criar usuário.
    public void reprovarSolicitacao(SolicitacaoCadastro solicitacao) {

        List<SolicitacaoCadastro> solicitacoes = repository.findAllSolicitacoes();

        solicitacoes.removeIf(s -> s.getEmail().equalsIgnoreCase(solicitacao.getEmail()));

        repository.salvarTodasSolicitacoes(solicitacoes);
    }

    // recria o objeto correto conforme o perfil escolhido na edição.
    public void editarUsuario(String nome, String emailOriginal, String emailNovo, String senha, String dataNascimento, boolean primeiroAcesso, String tipoPerfil) {
        Usuario usuarioAtualizado;

        if (tipoPerfil.equalsIgnoreCase("Administrador")) {
            usuarioAtualizado = new Administrador(nome, emailNovo, senha, dataNascimento);
        } else if (tipoPerfil.equalsIgnoreCase("Organizador")) {
            usuarioAtualizado = new Organizador(nome, emailNovo, senha, dataNascimento);
        } else {
            usuarioAtualizado = new ArbitroUser(nome, emailNovo, senha, dataNascimento);
        }

        usuarioAtualizado.setPrimeiroAcesso(primeiroAcesso);
        repository.update(emailOriginal, usuarioAtualizado);
    }

    // remove o usuário encontrado pelo email.
    public void excluirUsuario(String email) {
        // localiza o usuário existente pelo email e remove
        java.util.Optional<Usuario> usuarioOpt = repository.findById(email);
        if (usuarioOpt.isPresent()) {
            repository.delete(usuarioOpt.get());
        }
    }

    public List<SolicitacaoCadastro> obterTodasSolicitacoes() {
        return new UsuarioRepository().findAllSolicitacoes(); // Chama as solicitações pendentes
    }

}
