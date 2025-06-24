import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Classe Carona: representa uma viagem oferecida ou solicitada.
 */
class Carona {
    private String id; // ID único da carona.
    private String origem;
    private String destino;
    private LocalDateTime dataHoraPartida;
    private StatusCarona status; // Status atual da carona (enum).

    public Carona(String origem, String destino, LocalDateTime dataHoraPartida) {
        this.id = UUID.randomUUID().toString();
        this.origem = origem;
        this.destino = destino;
        this.dataHoraPartida = dataHoraPartida;
        this.status = StatusCarona.PENDENTE;
    }

    // Getters e Setters.
    public String getId() { return id; }
    public String getOrigem() { return origem; }
    public String getDestino() { return destino; }
    public LocalDateTime getDataHoraPartida() { return dataHoraPartida; }
    public StatusCarona getStatus() { return status; }
    public void setStatus(StatusCarona status) { this.status = status; }
}