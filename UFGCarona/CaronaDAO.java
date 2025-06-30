import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CaronaDAO: Objeto de Acesso a Dados para a classe Carona.
 * Gerencia a persistência de Caronas e as operações de caronas (oferecer, buscar, solicitar).
 */
class CaronaDAO {

    /** Salva um objeto Carona no DB. */
    public boolean save(Carona carona) {
        String sql = "INSERT INTO Caronas(id, origem, destino, data_hora_partida, status, motorista_id, passageiro_id) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, carona.getId());
            pstmt.setString(2, carona.getOrigem());
            pstmt.setString(3, carona.getDestino());
            pstmt.setString(4, carona.getDataHoraPartida().toString()); // Converte LocalDateTime para String
            pstmt.setString(5, carona.getStatus().name()); // Converte enum para String
            pstmt.setString(6, carona.getMotoristaId());
            pstmt.setString(7, carona.getPassageiroId()); // Pode ser null
            pstmt.executeUpdate();
            System.out.println("Carona salva: " + carona.getOrigem() + " -> " + carona.getDestino());
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar carona: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca todas as caronas disponíveis (PENDENTE) na plataforma.
     * Retorna uma lista de Caronas com detalhes do Motorista.
     */
    public List<Carona> findAvailableRides() {
        List<Carona> caronasDisponiveis = new ArrayList<>();
        // Junta Carona com Usuario (para pegar nome do motorista)
        String sql = "SELECT c.id, c.origem, c.destino, c.data_hora_partida, c.status, " +
                     "c.motorista_id, c.passageiro_id, u.nome AS motorista_nome " +
                     "FROM Caronas c INNER JOIN Usuarios u ON c.motorista_id = u.id " +
                     "WHERE c.status = '" + StatusCarona.PENDENTE.name() + "'"; // Busca apenas caronas PENDENTES

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) { // ExecuteQuery para SELECT

            while (rs.next()) {
                Carona carona = new Carona(
                    rs.getString("id"),
                    rs.getString("origem"),
                    rs.getString("destino"),
                    LocalDateTime.parse(rs.getString("data_hora_partida")), // Converte String para LocalDateTime
                    StatusCarona.valueOf(rs.getString("status")), // Converte String para enum
                    rs.getString("motorista_id"),
                    rs.getString("passageiro_id")
                );
                System.out.println("Carona disponível: ID " + carona.getId() + " - " + carona.getOrigem() + " -> " + carona.getDestino() +
                                   " (Motorista: " + rs.getString("motorista_nome") + ")");
                caronasDisponiveis.add(carona);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar caronas disponíveis: " + e.getMessage());
        }
        return caronasDisponiveis;
    }

    /**
     * Um passageiro solicita uma carona, atualizando seu status e associando o passageiro.
     * @param caronaId O ID da carona a ser solicitada.
     * @param passageiroId O ID do passageiro que solicita.
     * @return true se a solicitação foi bem-sucedida, false caso contrário.
     */
    public boolean requestCarona(String caronaId, String passageiroId) {
        String sql = "UPDATE Caronas SET status = ?, passageiro_id = ? WHERE id = ? AND status = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, StatusCarona.CONFIRMADA.name()); // Mude para CONFIRMADA
            pstmt.setString(2, passageiroId);
            pstmt.setString(3, caronaId);
            pstmt.setString(4, StatusCarona.PENDENTE.name()); // Só pode solicitar se estiver PENDENTE
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Carona ID " + caronaId + " solicitada com sucesso pelo passageiro ID " + passageiroId);
                return true;
            } else {
                System.out.println("Falha ao solicitar carona ID " + caronaId + ". Carona não encontrada ou já solicitada/finalizada.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao solicitar carona: " + e.getMessage());
            return false;
        }
    }

    /** Busca uma Carona pelo seu ID. */
    public Optional<Carona> findById(String id) {
        String sql = "SELECT id, origem, destino, data_hora_partida, status, motorista_id, passageiro_id FROM Caronas WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Carona carona = new Carona(
                    rs.getString("id"),
                    rs.getString("origem"),
                    rs.getString("destino"),
                    LocalDateTime.parse(rs.getString("data_hora_partida")),
                    StatusCarona.valueOf(rs.getString("status")),
                    rs.getString("motorista_id"),
                    rs.getString("passageiro_id")
                );
                return Optional.of(carona);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carona por ID: " + e.getMessage());
        }
        return Optional.empty();
    }
}