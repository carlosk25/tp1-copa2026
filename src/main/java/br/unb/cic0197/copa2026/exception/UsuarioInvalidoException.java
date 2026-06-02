package br.unb.cic0197.copa2026.exception;

public class UsuarioInvalidoException extends Exception {
    private static final long serialVersionUID = 1L;

    public UsuarioInvalidoException(String mensagem) {
        super(mensagem);
    }
}
