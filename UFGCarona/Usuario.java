import java.util.UUID;

abstract class Usuario {
    protected String id;
    protected String nome;
    protected String email;
    protected String senhaHash;
    protected String telefone;
    protected boolean ativo;

    public Usuario(String nome, String email, String senha, String telefone) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.email = email;
        this.senhaHash = gerarHashSenha(senha);
        this.telefone = telefone;
        this.ativo = true;

        if (!this.email.endsWith("@discente.ufg.br")) {
            throw new IllegalArgumentException("Erro na criação de Usuário: O e-mail '" + email + "' não é um e-mail de discente válido (@discente.ufg.br).");
        }
    }

    public Usuario(String id, String nome, String email, String senhaHash, String telefone, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.telefone = telefone;
        this.ativo = ativo;
    }

    public abstract void criarConta();

    public boolean fazerLogin(String email, String senha) {
        return this.email.equals(email) && verificarHashSenha(senha, this.senhaHash);
    }

    public void atualizarPerfil(String novoNome, String novoTelefone) {
        this.nome = novoNome;
        this.telefone = novoTelefone;
    }

    private String gerarHashSenha(String senha) { return "hashed_" + senha; }
    private boolean verificarHashSenha(String senhaDigitada, String hashArmazenado) { return ("hashed_" + senhaDigitada).equals(hashArmazenado); }

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
    public void setInativo() { this.ativo = false; }
}