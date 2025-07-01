package UFGCarona.model;

import java.time.LocalDateTime;
import java.util.UUID; // Importado para gerar IDs

/**
 * Classe Carona: representa uma carona oferecida ou solicitada.
 */
public class Carona {
    private String id;
    private String origem;
    private String destino;
    private LocalDateTime dataHoraPartida;
    private StatusCarona status; // Enum para status da carona
    private String motoristaId;
    private String passageiroId; // ID do passageiro que aceitou a carona (pode ser null)
    private int vagasDisponiveis; // **NOVO CAMPO**

    // Enum para o status da carona
    public enum StatusCarona {
        PENDENTE,       // Carona oferecida, aguardando passageiro
        CONFIRMADA,     // Carona aceita por um passageiro
        FINALIZADA,     // Carona concluída
        CANCELADA       // Carona cancelada
    }

    /**
     * Construtor para uma NOVA carona (o ID é gerado automaticamente).
     * @param origem Ponto de partida da carona.
     * @param destino Ponto de chegada da carona.
     * @param dataHoraPartida Data e hora de partida.
     * @param motoristaId ID do motorista que oferece a carona.
     * @param vagasDisponiveis Número de vagas disponíveis na carona.
     */
    public Carona(String origem, String destino, LocalDateTime dataHoraPartida, String motoristaId, int vagasDisponiveis) {
        this.id = UUID.randomUUID().toString();
        this.origem = origem;
        this.destino = destino;
        this.dataHoraPartida = dataHoraPartida;
        this.status = StatusCarona.PENDENTE; // Toda nova carona começa como PENDENTE
        this.motoristaId = motoristaId;
        this.passageiroId = null; // Inicia sem passageiro associado
        this.vagasDisponiveis = vagasDisponiveis;
    }

    /**
     * Construtor para carregar carona do banco de dados (o ID já existe).
     * Este construtor foi atualizado para incluir vagasDisponiveis.
     * @param id ID único da carona.
     * @param origem Ponto de partida da carona.
     * @param destino Ponto de chegada da carona.
     * @param dataHoraPartida Data e hora de partida.
     * @param status Status atual da carona.
     * @param motoristaId ID do motorista que oferece a carona.
     * @param passageiroId ID do passageiro que aceitou a carona (pode ser null).
     * @param vagasDisponiveis Número de vagas disponíveis na carona.
     */
    public Carona(String id, String origem, String destino, LocalDateTime dataHoraPartida,
                  StatusCarona status, String motoristaId, String passageiroId, int vagasDisponiveis) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.dataHoraPartida = dataHoraPartida;
        this.status = status;
        this.motoristaId = motoristaId;
        this.passageiroId = passageiroId;
        this.vagasDisponiveis = vagasDisponiveis;
    }


    // Getters
    public String getId() { return id; }
    public String getOrigem() { return origem; }
    public String getDestino() { return destino; }
    public LocalDateTime getDataHoraPartida() { return dataHoraPartida; }
    public StatusCarona getStatus() { return status; }
    public String getMotoristaId() { return motoristaId; }
    public String getPassageiroId() { return passageiroId; }
    public int getVagasDisponiveis() { return vagasDisponiveis; } // **NOVO GETTER**

    // Setters (se necessário para lógica de negócio, mas evite setters para campos que não devem mudar)
    public void setId(String id) { this.id = id; }
    public void setOrigem(String origem) { this.origem = origem; }
    public void setDestino(String destino) { this.destino = destino; }
    public void setDataHoraPartida(LocalDateTime dataHoraPartida) { this.dataHoraPartida = dataHoraPartida; }
    public void setStatus(StatusCarona status) { this.status = status; }
    public void setMotoristaId(String motoristaId) { this.motoristaId = motoristaId; }
    public void setPassageiroId(String passageiroId) { this.passageiroId = passageiroId; }
    public void setVagasDisponiveis(int vagasDisponiveis) { this.vagasDisponiveis = vagasDisponiveis; } // **NOVO SETTER**
}