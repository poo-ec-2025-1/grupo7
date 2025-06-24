import java.time.LocalDateTime;

/**
 * Representa um Motorista no sistema. Herda de Usuario.
 * Adiciona dados de habilitação e veículo.
 */
class Motorista extends Usuario {
    protected String numeroHabilitacao;
    protected String categoriaHabilitacao;
    protected Veiculo veiculo; // Veículo associado ao motorista.

    /** Construtor para criar um NOVO motorista. */
    public Motorista(String nome, String email, String senha, String telefone,
                     String numeroHabilitacao, String categoriaHabilitacao, Veiculo veiculo) {
        super(nome, email, senha, telefone);
        this.numeroHabilitacao = numeroHabilitacao;
        this.categoriaHabilitacao = categoriaHabilitacao;
        this.veiculo = veiculo;
    }

    /** Construtor para carregar motorista do banco de dados. */
    public Motorista(String id, String nome, String email, String senhaHash, String telefone, boolean ativo,
                     String numeroHabilitacao, String categoriaHabilitacao, Veiculo veiculo) {
        super(id, nome, email, senhaHash, telefone, ativo);
        this.numeroHabilitacao = numeroHabilitacao;
        this.categoriaHabilitacao = categoriaHabilitacao;
        this.veiculo = veiculo;
    }

    @Override
    public void criarConta() { /* Lógica de criação de conta de motorista via DAO. */ }

    /** Métodos de negócio específicos do Motorista. */
    public void oferecerCarona(String origem, String destino, LocalDateTime dataHora, int vagas) { /* ... */ }
    public void aceitarPedidoCarona(String pedidoId) { /* ... */ }
    public void finalizarCarona(String caronaId) { /* ... */ }

    // Getters e Setters para atributos específicos do Motorista.
    public String getNumeroHabilitacao() { return numeroHabilitacao; }
    public String getCategoriaHabilitacao() { return categoriaHabilitacao; }
    public Veiculo getVeiculo() { return veiculo; }

    public void setNumeroHabilitacao(String numeroHabilitacao) { this.numeroHabilitacao = numeroHabilitacao; }
    public void setCategoriaHabilitacao(String categoriaHabilitacao) { this.categoriaHabilitacao = categoriaHabilitacao; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
}