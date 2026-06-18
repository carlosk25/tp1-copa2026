package br.unb.cic0197.copa2026.controller;

import br.unb.cic0197.copa2026.model.Usuario;
import br.unb.cic0197.copa2026.model.SolicitacaoCadastro;
import br.unb.cic0197.copa2026.service.UsuarioService;
import java.util.List;
import br.unb.cic0197.copa2026.exception.UsuarioInvalidoException;

// centraliza as chamadas de usuário para as telas não acessarem o service diretamente.
public class UsuarioGerenciador {

    private static final UsuarioService usuarioService = new UsuarioService();

    // retorna as solicitações pendentes de cadastro.
    public static List<SolicitacaoCadastro> listarSolicitacoes() {
        return usuarioService.obterTodasSolicitacoes();
    }

    // aprova uma solicitação e transforma ela em usuário do sistema.
    public static String aprovarSolicitacao(SolicitacaoCadastro solicitacao) {
        return usuarioService.aprovarSolicitacao(solicitacao);
    }

    // valida o login usando email e senha.
    public static Usuario autenticar(String email, String senha) throws UsuarioInvalidoException {
        return usuarioService.autenticar(email, senha);
    }

    // altera os dados de um usuário já cadastrado.
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

    // remove a solicitação quando o cadastro é recusado.
    public static void reprovarSolicitacao(SolicitacaoCadastro solicitacao) {

        usuarioService.reprovarSolicitacao(solicitacao);
    }

}
