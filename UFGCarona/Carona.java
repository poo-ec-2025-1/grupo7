import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Classe Carona: representa uma viagem oferecida ou solicitada.
 * Contém detalhes da viagem, seu status, e quem oferece/solicita.
 */
class Carona {
    private String id; // ID único da carona.
    private String origem;
    private String destino;
    private LocalDateTime dataHoraPartida;
    private StatusCarona status; // Status atual da carona.
    private String motoristaId; // ID do motorista que oferece a carona
    private String passageiroId; // ID do passageiro que solicitou/aceitou a carona

    /**
     * Construtor para criar uma NOVA carona (oferecida pelo motorista).
     */
    public Carona(String origem, String destino, LocalDateTime dataHoraPartida, String motoristaId) {
        this.id = UUID.randomUUID().toString();
        this.origem = origem;
        this.destino = destino;
        this.dataHoraPartida = dataHoraPartida;
        this.status = StatusCarona.PENDENTE; // Carona é oferecida, aguardando passageiro.
        this.motoristaId = motoristaId;
        this.passageiroId = null; // Inicialmente sem passageiro
    }

    /**
     * Construtor para carregar carona do banco de dados.
     */
    public Carona(String id, String origem, String destino, LocalDateTime dataHoraPartida,
                  StatusCarona status, String motoristaId, String passageiroId) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.dataHoraPartida = dataHoraPartida;
        this.status = status;
        this.motoristaId = motoristaId;
        this.passageiroId = passageiroId;
    }

    public String getId() { return id; }
    public String getOrigem() { return origem; }
    public String getDestino() { return destino; }
    public LocalDateTime getDataHoraPartida() { return dataHoraPartida; }
    public StatusCarona getStatus() { return status; }
    public String getMotoristaId() { return motoristaId; }
    public String getPassageiroId() { return passageiroId; }

    public void setStatus(StatusCarona status) { this.status = status; }
    public void setMotoristaId(String motoristaId) { this.motoristaId = motoristaId; }
    public void setPassageiroId(String passageiroId) { this.passageiroId = passageiroId; }
}