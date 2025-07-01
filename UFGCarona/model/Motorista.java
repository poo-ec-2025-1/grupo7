package UFGCarona.model;

import java.time.LocalDateTime;

public class Motorista extends Usuario {
    protected String numeroHabilitacao;
    protected String categoriaHabilitacao;
    protected Veiculo veiculo;

    public Motorista(String nome, String email, String senha, String telefone,
                     String numeroHabilitacao, String categoriaHabilitacao, Veiculo veiculo) {
        super(nome, email, senha, telefone);
        this.numeroHabilitacao = numeroHabilitacao;
        this.categoriaHabilitacao = categoriaHabilitacao;
        this.veiculo = veiculo;
    }

    public Motorista(String id, String nome, String email, String senhaHash, String telefone, boolean ativo,
                     String numeroHabilitacao, String categoriaHabilitacao, Veiculo veiculo) {
        super(id, nome, email, senhaHash, telefone, ativo);
        this.numeroHabilitacao = numeroHabilitacao;
        this.categoriaHabilitacao = categoriaHabilitacao;
        this.veiculo = veiculo;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    @Override
    public void criarConta() {
        new MotoristaDAO().save(this);
    }

    public void oferecerCarona(String origem, String destino, LocalDateTime dataHora, int vagas) { /* ... */ }
    public void aceitarPedidoCarona(String pedidoId) { /* ... */ }
    public void finalizarCarona(String caronaId) { /* ... */ }

    public String getNumeroHabilitacao() { return numeroHabilitacao; }
    public String getCategoriaHabilitacao() { return categoriaHabilitacao; }

    public void setNumeroHabilitacao(String numeroHabilitacao) { this.numeroHabilitacao = numeroHabilitacao; }
    public void setCategoriaHabilitacao(String categoriaHabilitacao) { this.categoriaHabilitacao = categoriaHabilitacao; }
}