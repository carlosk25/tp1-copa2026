package br.unb.cic0197.copa2026.controller;

import br.unb.cic0197.copa2026.model.Usuario;

// guarda temporariamente o usuário logado durante a execução do sistema.
public class SessaoSistema {
    private static Usuario usuarioLogado;

    // inicia a sessão quando o login é realizado com sucesso.
    public static void iniciarSessao(Usuario usuario) {
        usuarioLogado = usuario;
    }

    // permite que as telas saibam qual usuário está usando o sistema.
    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
  
    // limpa a sessão no logout.
    public static void encerrarSessao() {
        usuarioLogado = null;
    }
}
