package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository {
    private static final String ARQUIVO_USERS = "usuarios.txt";
    private static final String ARQUIVO_SOLICITACOES = "solicitacoes.txt";
    private static final String SEPARADOR = "\\|";

    public List<Usuario> findAll() {
        try {
            return carregarUsuarios();
        } catch (IOException e) {
            throw new RuntimeException("Falha ao carregar usuários", e);
        }
    }

    public void add(Usuario usuario) {
        try {
            List<Usuario> usuarios = carregarUsuarios();
            usuarios.add(usuario);
            salvarUsuarios(usuarios);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar usuário", e);
        }
    }

    public void update(Usuario usuario) {
        try {
            List<Usuario> usuarios = carregarUsuarios();
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getEmail().equalsIgnoreCase(usuario.getEmail())) {
                    usuarios.set(i, usuario);
                    break;
                }
            }
            salvarUsuarios(usuarios);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao atualizar usuário", e);
        }
    }

    public void delete(Usuario usuario) {
        try {
            List<Usuario> usuarios = carregarUsuarios();
            usuarios.removeIf(u -> u.getEmail().equalsIgnoreCase(usuario.getEmail()));
            salvarUsuarios(usuarios);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao excluir usuário", e);
        }
    }

    public Optional<Usuario> findById(String email) {
        return findAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public List<SolicitacaoCadastro> findAllSolicitacoes() {
        List<SolicitacaoCadastro> lista = new ArrayList<>();
        File file = new File(ARQUIVO_SOLICITACOES);
        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] dados = linha.split(SEPARADOR);
                lista.add(new SolicitacaoCadastro(dados[0], dados[1], dados[2], dados[3]));
            }
        } catch (IOException e) {
            throw new RuntimeException("Falha ao ler solicitações", e);
        }
        return lista;
    }

    public void salvarTodasSolicitacoes(List<SolicitacaoCadastro> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_SOLICITACOES))) {
            for (SolicitacaoCadastro s : lista) {
                bw.write(s.getNome() + "|" + s.getEmail() + "|" + s.getDataNascimento() + "|" + s.getTipoPerfilSolicitado());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar solicitações", e);
        }
    }

    
    private List<Usuario> carregarUsuarios() throws IOException {
        List<Usuario> lista = new ArrayList<>();
        File file = new File(ARQUIVO_USERS);
        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] dados = linha.split(SEPARADOR);

                String nome = dados[0];
                String email = dados[1];
                String senha = dados[2];
                String dataNasc = dados[3];
                boolean primeiroAcesso = Boolean.parseBoolean(dados[4]);
                String tipoPerfil = dados[5];

                Usuario u;
                if (tipoPerfil.equalsIgnoreCase("Administrador")) {
                    u = new Administrador(nome, email, senha, dataNasc);
                } else if (tipoPerfil.equalsIgnoreCase("Organizador")) {
                    u = new Organizador(nome, email, senha, dataNasc);
                } else {
                    u = new ArbitroUser(nome, email, senha, dataNasc);
                }
                u.setPrimeiroAcesso(primeiroAcesso);
                lista.add(u);
            }
        }
        return lista;
    }

    private void salvarUsuarios(List<Usuario> lista) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_USERS))) {
            for (Usuario u : lista) {
                bw.write(u.getNome() + "|" + u.getEmail() + "|" + u.getSenha() + "|" +
                        u.getDataNascimento() + "|" + u.isPrimeiroAcesso() + "|" + u.getTipoPerfil());
                bw.newLine();
            }
        }
    }
}
