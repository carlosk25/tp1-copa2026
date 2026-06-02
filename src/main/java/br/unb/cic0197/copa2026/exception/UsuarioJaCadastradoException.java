package br.unb.cic0197.copa2026.exception;

public class UsuarioJaCadastradoException extends Exception {
    private static final long serialVersionUID = 1L;

    public UsuarioJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}
