package UFGCarona.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Classe Avaliacao: representa o feedback dado em uma carona.
 */
public class Avaliacao {
    private String id; // ID único para a avaliação.
    private int nota; // Nota da avaliação (ex: 1 a 5).
    private String comentario;
    private LocalDateTime dataAvaliacao; // Data/hora da avaliação.

    public Avaliacao(int nota, String comentario) {
        this.id = UUID.randomUUID().toString();
        this.nota = nota;
        this.comentario = comentario;
        this.dataAvaliacao = LocalDateTime.now();
    }

    // Getters.
    public String getId() { return id; }
    public int getNota() { return nota; }
    public String getComentario() { return comentario; }
    public LocalDateTime getDataAvaliacao() { return dataAvaliacao; }
}
