public class CadastroView extends JPanel {
    private CopaApp app;

    public CadastroView(CopaApp app) {
        this.app = app;
        setLayout(new GridLayout(9, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Campos baseados na modelagem de domínio
        add(new JLabel("Nome Completo:")); add(new JTextField());
        add(new JLabel("Data de Nascimento:")); add(new JTextField());
        add(new JLabel("E-mail:")); add(new JTextField());
        add(new JLabel("Senha:")); add(new JPasswordField());
        add(new JLabel("Confirmar Senha:")); add(new JPasswordField());

        // Campo fundamental para definir a classe (Herança)
        add(new JLabel("Tipo de Acesso:"));
        String[] perfis = {"Administrador", "Organizador", "Operador"};
        add(new JComboBox<>(perfis));

        JButton btnVoltar = new JButton("Voltar");
        JButton btnSalvar = new JButton("Finalizar Cadastro");

        btnVoltar.addActionListener(e -> app.mostrarTela("login"));
        btnSalvar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            app.mostrarTela("login");
        });

        add(btnVoltar); add(btnSalvar);
    }
}
