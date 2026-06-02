package br.unb.cic0197.copa2026.controller;

import br.unb.cic0197.copa2026.model.Usuario;
import br.unb.cic0197.copa2026.model.Administrador;
import br.unb.cic0197.copa2026.model.SolicitacaoCadastro;
import br.unb.cic0197.copa2026.services.UsuarioService;
import java.util.List;
import br.unb.cic0197.copa2026.exception.UsuarioInvalidoException;


public class UsuarioGerenciador {
  
    private static final UsuarioService usuarioService = new UsuarioService();

    public static List<Usuario> listarTodos() {
        return usuarioService.obtertodas();
    }

    public static List<SolicitacaoCadastro> listarSolicitacoes() {
        return usuarioService.obterTodasSolicitacoes();
    }

    public static String aprovarSolicitacao(SolicitacaoCadastro solicitacao) {
        return usuarioService.aprovarSolicitacao(solicitacao);
    }

    public static Usuario autenticar(String email, String senha) throws UsuarioInvalidoException {
        return usuarioService.autenticar(email, senha);
    }

    public static void atualizarSenhaPrimeiroAcesso(String email, String novaSenha) throws UsuarioInvalidoException {
        usuarioService.atualizarSenhaPrimeiroAcesso(email, novaSenha);
    }

    public static void editarUsuario(String email, String nome, String emailRepetido, String dataNascimento, String senha, String tipoPerfil) {
      
        boolean primeiroAcesso = false;
        usuarioService.editarUsuario(nome, email, senha, dataNascimento, primeiroAcesso, tipoPerfil);
    }

    public static void excluirUsuario(String email) {
        usuarioService.excluirUsuario(email);
    }
}
