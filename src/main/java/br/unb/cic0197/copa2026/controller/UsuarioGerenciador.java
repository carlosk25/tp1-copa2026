package br.unb.cic0197.copa2026.controller;

import br.unb.cic0197.copa2026.model.Usuario;
import br.unb.cic0197.copa2026.model.SolicitacaoCadastro;
import br.unb.cic0197.copa2026.service.UsuarioService;
import java.util.List;
import br.unb.cic0197.copa2026.exception.UsuarioInvalidoException;

public class UsuarioGerenciador {

    private static final UsuarioService usuarioService = new UsuarioService();

    public static List<SolicitacaoCadastro> listarSolicitacoes() {
        return usuarioService.obterTodasSolicitacoes();
    }

    public static String aprovarSolicitacao(SolicitacaoCadastro solicitacao) {
        return usuarioService.aprovarSolicitacao(solicitacao);
    }

    public static Usuario autenticar(String email, String senha) throws UsuarioInvalidoException {
        return usuarioService.autenticar(email, senha);
    }

    public static void editarUsuario(String emailOriginal, String nome, String emailNovo, String dataNascimento,
            String senha, String tipoPerfil) {
        usuarioService.editarUsuario(nome, emailOriginal, emailNovo, senha, dataNascimento, false, tipoPerfil);
    }

    public static void excluirUsuario(String email) {
        usuarioService.excluirUsuario(email);
    }

    public static List<SolicitacaoCadastro> obterTodasSolicitacoes() {
        return usuarioService.obterTodasSolicitacoes();
    }

    public static List<Usuario> obterTodosUsuarios() {
        return usuarioService.obtertodas();
    }

    public static void adicionarSolicitacao(br.unb.cic0197.copa2026.model.SolicitacaoCadastro novaSolicitacao)
            throws Exception {

        usuarioService.cadastrarSolicitacao(novaSolicitacao);
    }

    public static void reprovarSolicitacao(SolicitacaoCadastro solicitacao) {

        usuarioService.reprovarSolicitacao(solicitacao);
    }

}
