import java.util.UUID;

/**
 * Superclasse abstrata para todos os usuários do sistema.
 * Define atributos e comportamentos comuns (ID, nome, email, login).
 */
abstract class Usuario {
    protected String id;
    protected String nome;
    protected String email;
    protected String senhaHash; // Senha armazenada como hash (segurança)
    protected String telefone;
    protected boolean ativo;

    /** Construtor para criar um NOVO usuário. */
    public Usuario(String nome, String email, String senha, String telefone) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.email = email;
        this.senhaHash = gerarHashSenha(senha);
        this.telefone = telefone;
        this.ativo = true;
    }

    /** Construtor para carregar um usuário do banco de dados. */
    public Usuario(String id, String nome, String email, String senhaHash, String telefone, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.telefone = telefone;
        this.ativo = ativo;
    }

    /** Método abstrato: subclasses devem implementar sua lógica de criação de conta. */
    public abstract void criarConta();

    /** Lógica básica de login. */
    public boolean fazerLogin(String email, String senha) {
        return this.email.equals(email) && verificarHashSenha(senha, this.senhaHash);
    }

    /** Atualiza informações do perfil do usuário. */
    public void atualizarPerfil(String novoNome, String novoTelefone) {
        this.nome = novoNome;
        this.telefone = novoTelefone;
    }

    private String gerarHashSenha(String senha) { return "hashed_" + senha; } // Simplificado
    private boolean verificarHashSenha(String senhaDigitada, String hashArmazenado) { return ("hashed_" + senhaDigitada).equals(hashArmazenado); } // Simplificado

    // Getters e Setters (para acesso e manipulação por DAOs).
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public boolean isAtivo() { return ativo; }
    public String getSenhaHash() { return senhaHash; }

    public void setId(String id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}