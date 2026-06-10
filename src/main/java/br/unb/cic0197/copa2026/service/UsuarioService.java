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
    }

    public List<Usuario> obtertodas() {
        return repository.findAll();
    }

    public List<SolicitacaoCadastro> obterTodasSolicitacoes() {
        return repository.findAllSolicitacoes();
    }

    public Optional<Usuario> obterporid(String email) {
        return repository.findById(email);
    }

    public void salvar(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            repository.add(usuario);
        } else {
            Optional<Usuario> existente = repository.findById(usuario.getEmail());
            if (!existente.isPresent()) {
                repository.add(usuario);
            } else {
                repository.update(usuario);
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
        // Verifica se o e-mail já não está em uso no sistema
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

    public void atualizarSenhaPrimeiroAcesso (String email, String novaSenha) throws UsuarioInvalidoException {
        Optional<Usuario> usuarioOpt = repository.findById(email);
        if (!usuarioOpt.isPresent()) {
            throw new UsuarioInvalidoException("Usuário não encontrado para atualização de senha.");
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setSenha(novaSenha);
        usuario.setPrimeiroAcesso(false); 
        repository.update(usuario);
    }

    public String aprovarSolicitacao(SolicitacaoCadastro solicitacao) {
        String primeiroNome = solicitacao.getNome().trim().split(" ")[0];
        String data = solicitacao.getDataNascimento().trim();
        String ano = data.length() >= 4 ? data.substring(data.length() - 4) : "2026";
        String senhaInicial = primeiroNome + ano;

        Usuario novoUsuario;
        if (solicitacao.getTipoPerfilSolicitado().equalsIgnoreCase("Administrador")) {
            novoUsuario = new Administrador(solicitacao.getNome(), solicitacao.getEmail(), senhaInicial, solicitacao.getDataNascimento());
        } else if (solicitacao.getTipoPerfilSolicitado().equalsIgnoreCase("Organizador")) {
            novoUsuario = new Organizador(solicitacao.getNome(), solicitacao.getEmail(), senhaInicial, solicitacao.getDataNascimento());
        } else {
            novoUsuario = new ArbitroUser(solicitacao.getNome(), solicitacao.getEmail(), senhaInicial, solicitacao.getDataNascimento());
        }

        repository.add(novoUsuario);

        List<SolicitacaoCadastro> solicitacoes = repository.findAllSolicitacoes();
        solicitacoes.removeIf(s -> s.getEmail().equalsIgnoreCase(solicitacao.getEmail()));
        repository.salvarTodasSolicitacoes(solicitacoes);

        return senhaInicial;
    }
  
    public void editarUsuario(String nome, String email, String senha, String dataNascimento, boolean primeiroAcesso, String tipoPerfil) {
        Usuario usuarioAtualizado;

        if (tipoPerfil.equalsIgnoreCase("Administrador")) {
            usuarioAtualizado = new Administrador(nome, email, senha, dataNascimento);
        } else if (tipoPerfil.equalsIgnoreCase("Organizador")) {
            usuarioAtualizado = new Organizador(nome, email, senha, dataNascimento);
        } else {
            usuarioAtualizado = new ArbitroUser(nome, email, senha, dataNascimento);
        }

        usuarioAtualizado.setPrimeiroAcesso(primeiroAcesso);
        repository.update(usuarioAtualizado);
    }

    public void excluirUsuario(String email) {
        // Localiza o usuário existente pelo e-mail e remove
        java.util.Optional<Usuario> usuarioOpt = repository.findById(email);
        if (usuarioOpt.isPresent()) {
            repository.delete(usuarioOpt.get());
        }
    }

}
