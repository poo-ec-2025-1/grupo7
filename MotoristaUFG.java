import java.util.ArrayList;
import java.util.List;

/**
 * Representa um Motorista da UFG. Herda de Motorista e implementa IAvaliavel.
 * Adiciona identificador UFG e gerencia avaliações.
 */
class MotoristaUFG extends Motorista implements IAvaliavel {
    private String idUFG; // Identificador UFG (matrícula/Siape).
    private List<Avaliacao> avaliacoesRecebidas; // Lista de avaliações para este motorista.

    /** Construtor para criar um NOVO MotoristaUFG. */
    public MotoristaUFG(String nome, String email, String senha, String telefone,
                        String numeroHabilitacao, String categoriaHabilitacao, Veiculo veiculo,
                        String idUFG) {
        super(nome, email, senha, telefone, numeroHabilitacao, categoriaHabilitacao, veiculo);
        this.idUFG = idUFG;
        this.avaliacoesRecebidas = new ArrayList<>();
    }

    /** Construtor para carregar MotoristaUFG do banco de dados. */
    public MotoristaUFG(String id, String nome, String email, String senhaHash, String telefone, boolean ativo,
                        String numeroHabilitacao, String categoriaHabilitacao, Veiculo veiculo,
                        String idUFG) {
        super(id, nome, email, senhaHash, telefone, ativo, numeroHabilitacao, categoriaHabilitacao, veiculo);
        this.idUFG = idUFG;
        this.avaliacoesRecebidas = new ArrayList<>();
    }

    @Override
    public void receberAvaliacao(Avaliacao avaliacao) {
        if (avaliacao != null) this.avaliacoesRecebidas.add(avaliacao);
    }

    @Override
    public double calcularMediaAvaliacoes() {
        if (avaliacoesRecebidas.isEmpty()) return 0.0;
        double somaNotas = 0;
        for (Avaliacao avaliacao : avaliacoesRecebidas) {
            somaNotas += avaliacao.getNota();
        }
        return somaNotas / avaliacoesRecebidas.size();
    }

    /** Método específico para verificar vínculo UFG. */
    public void verificarCredenciaisUFG() { /* ... */ }

    public String getIdUFG() { return idUFG; }
    public void setIdUFG(String idUFG) { this.idUFG = idUFG; }
    public void setAvaliacoesRecebidas(List<Avaliacao> avaliacoesRecebidas) { this.avaliacoesRecebidas = avaliacoesRecebidas; }
}