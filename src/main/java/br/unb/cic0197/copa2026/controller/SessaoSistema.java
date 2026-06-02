package br.unb.cic0197.copa2026.controller;

import br.unb.cic0197.copa2026.model.Usuario;

public class SessaoSistema {
    private static Usuario usuarioLogado;

    public static void iniciarSessao(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
  
    public static void encerrarSessao() {
        usuarioLogado = null;
    }
}
