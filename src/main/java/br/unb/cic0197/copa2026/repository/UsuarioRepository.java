package br.unb.cic0197.copa2026.repository;

import br.unb.cic0197.copa2026.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// cuida da leitura e escrita de usuários e solicitações nos arquivos txt.
public class UsuarioRepository {
    private static final String ARQUIVO_USERS = "usuarios.txt";
    private static final String ARQUIVO_SOLICITACOES = "solicitacoes.txt";
    private static final String SEPARADOR = "\\|";

    // retorna todos os usuários cadastrados.
    public List<Usuario> findAll() {
        try {
            return carregarUsuarios();
        } catch (IOException e) {
            throw new RuntimeException("Falha ao carregar usuários", e);
        }
    }

    // adiciona um usuário novo ao arquivo.
    public void add(Usuario usuario) {
        try {
            List<Usuario> usuarios = carregarUsuarios();
            usuarios.add(usuario);
            salvarUsuarios(usuarios);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar usuário", e);
        }
    }

    // atualiza um usuário usando o email original como chave.
    public void update(String emailOriginal, Usuario usuario) {
        try {
            List<Usuario> usuarios = carregarUsuarios();
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getEmail().equalsIgnoreCase(emailOriginal)) {
                    usuarios.set(i, usuario);
                    break;
                }
            }
            salvarUsuarios(usuarios);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao atualizar usuário", e);
        }
    }

    // remove um usuário existente pelo email.
    public void delete(Usuario usuario) {
        try {
            List<Usuario> usuarios = carregarUsuarios();
            usuarios.removeIf(u -> u.getEmail().equalsIgnoreCase(usuario.getEmail()));
            salvarUsuarios(usuarios);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao excluir usuário", e);
        }
    }

    // busca um usuário pelo email.
    public Optional<Usuario> findById(String email) {
        return findAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    // carrega as solicitações de cadastro pendentes.
    public List<SolicitacaoCadastro> findAllSolicitacoes() {
        List<SolicitacaoCadastro> lista = new ArrayList<>();
        File file = new File(ARQUIVO_SOLICITACOES);
        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] dados = linha.split(SEPARADOR);
                lista.add(new SolicitacaoCadastro(dados[0], dados[1], dados[2], dados[3], dados[4]));
            }
        } catch (IOException e) {
            throw new RuntimeException("Falha ao ler solicitações", e);
        }
        return lista;
    }

    // regrava a lista de solicitações depois de aprovar ou reprovar.
    public void salvarTodasSolicitacoes(List<SolicitacaoCadastro> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_SOLICITACOES))) {
            for (SolicitacaoCadastro s : lista) {
                bw.write(s.getNome() + "|" + s.getEmail() + "|" + s.getSenha() + "|" + s.getDataNascimento() + "|" + s.getTipoPerfilSolicitado());
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar solicitações", e);
        }
    }

    
    // monta os usuários corretos de acordo com o perfil salvo no arquivo.
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

    // salva todos os usuários no formato nome;email;senha;data;perfil.
    private void salvarUsuarios(List<Usuario> lista) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_USERS))) {
            for (Usuario u : lista) {
                bw.write(u.getNome() + "|" + u.getEmail() + "|" + u.getSenha() + "|" +
                        u.getDataNascimento() + "|" + u.isPrimeiroAcesso() + "|" + u.getTipoPerfil());
                bw.newLine();
            }
            bw.flush();
        }
    }
}
