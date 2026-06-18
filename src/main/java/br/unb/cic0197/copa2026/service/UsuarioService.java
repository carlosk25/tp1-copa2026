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

public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService() {
        this.repository = new UsuarioRepository(); 
        inicializarAdminPadrao();                 
    }

    private void inicializarAdminPadrao() {
        String emailAdmin = "master@copa.com";

        if (!repository.findById(emailAdmin).isPresent()) {
            // Cria o administrador usando o construtor correto de 4 argumentos
            Administrador adminPadrao = new Administrador(
                    "Administrador Geral",
                    emailAdmin,
                    "admin123",
                    "01/01/1990"
            );

            // Salva usando o método correto do seu repositório (save)
            repository.add(adminPadrao);
            System.out.println("🚀 Admin padrão pré-carregado via Camada de Serviço!");
        }
    }

    public List<Usuario> obtertodas() {
        return repository.findAll();
    }


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

    public Usuario autenticar (String email, String senha) throws UsuarioInvalidoException {
        Optional<Usuario> usuarioOpt = repository.findById(email);
        if (!usuarioOpt.isPresent() || !usuarioOpt.get().getSenha().equals(senha)) {
            throw new UsuarioInvalidoException("E-mail ou senha inválidos!");
        }
        return usuarioOpt.get();
    }

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
    public void reprovarSolicitacao(SolicitacaoCadastro solicitacao) {

        List<SolicitacaoCadastro> solicitacoes = repository.findAllSolicitacoes();

        solicitacoes.removeIf(s -> s.getEmail().equalsIgnoreCase(solicitacao.getEmail()));

        repository.salvarTodasSolicitacoes(solicitacoes);
    }

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
