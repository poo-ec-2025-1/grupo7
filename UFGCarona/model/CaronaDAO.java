package UFGCarona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CaronaDAO: Classe de Acesso a Dados para operações relacionadas a Caronas.
 * Gerencia a persistência de objetos Carona no banco de dados.
 */
public class CaronaDAO {

    /**
     * Salva um objeto Carona no banco de dados.
     * @param carona O objeto Carona a ser salvo.
     * @return true se a carona foi salva com sucesso, false caso contrário.
     */
    public boolean save(Carona carona) {
        // **Modificado: Adicionado 'vagas_disponiveis' ao SQL INSERT**
        String sql = "INSERT INTO Caronas(id, origem, destino, data_hora_partida, status, motorista_id, passageiro_id, vagas_disponiveis) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, carona.getId());
            pstmt.setString(2, carona.getOrigem());
            pstmt.setString(3, carona.getDestino());
            pstmt.setString(4, carona.getDataHoraPartida().toString()); // LocalDateTime para String
            pstmt.setString(5, carona.getStatus().name()); // Enum para String
            pstmt.setString(6, carona.getMotoristaId());
            pstmt.setString(7, carona.getPassageiroId()); // Pode ser null
            pstmt.setInt(8, carona.getVagasDisponiveis()); // **NOVO CAMPO**
            pstmt.executeUpdate();
            System.out.println("Carona salva: " + carona.getOrigem() + " -> " + carona.getDestino() + " com " + carona.getVagasDisponiveis() + " vagas.");
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar carona: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Atualiza o status de uma carona para FINALIZADA e associa um passageiro.
     * @param caronaId O ID da carona a ser finalizada.
     * @param passageiroId O ID do passageiro que finalizou a carona.
     * @return true se a carona foi atualizada com sucesso, false caso contrário.
     */
    public boolean finalizarCarona(String caronaId, String passageiroId) {
        String sql = "UPDATE Caronas SET status = ?, passageiro_id = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Carona.StatusCarona.FINALIZADA.name());
            pstmt.setString(2, passageiroId);
            pstmt.setString(3, caronaId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao finalizar carona: " + e.getMessage());
            return false;
        }
    }

    /**
     * Atualiza o status de uma carona para CONFIRMADA e associa um passageiro.
     * Diminui o número de vagas.
     * @param caronaId O ID da carona a ser solicitada/confirmada.
     * @param passageiroId O ID do passageiro que solicitou a carona.
     * @return true se a carona foi atualizada com sucesso, false caso contrário.
     */
    public boolean requestCarona(String caronaId, String passageiroId) {
        // Primeiro, obtenha a carona para verificar as vagas
        Optional<Carona> caronaOpt = findById(caronaId);
        if (caronaOpt.isEmpty()) {
            System.err.println("Carona não encontrada para solicitação: " + caronaId);
            return false;
        }
        Carona carona = caronaOpt.get();

        if (carona.getVagasDisponiveis() <= 0) {
            System.out.println("Não há mais vagas disponíveis para esta carona.");
            return false;
        }

        // Se houver vagas, atualize o status para CONFIRMADA e diminua as vagas
        String sql = "UPDATE Caronas SET status = ?, passageiro_id = ?, vagas_disponiveis = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Carona.StatusCarona.CONFIRMADA.name());
            pstmt.setString(2, passageiroId);
            pstmt.setInt(3, carona.getVagasDisponiveis() - 1); // Diminui uma vaga
            pstmt.setString(4, caronaId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao solicitar carona: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca todas as caronas disponíveis (PENDENTE e com vagas > 0) na plataforma.
     * Retorna uma lista de Caronas com detalhes do Motorista.
     * @return Uma lista de objetos Carona disponíveis.
     */
    public List<Carona> findAvailableRides() {
        List<Carona> caronasDisponiveis = new ArrayList<>();
        // Junta Carona com Usuario (para pegar nome do motorista)
        // **Modificado: Adicionado 'vagas_disponiveis' ao SELECT e condição WHERE**
        String sql = "SELECT c.id, c.origem, c.destino, c.data_hora_partida, c.status, " +
                     "c.motorista_id, c.passageiro_id, c.vagas_disponiveis, u.nome AS motorista_nome " +
                     "FROM Caronas c INNER JOIN Usuarios u ON c.motorista_id = u.id " +
                     "WHERE c.status = '" + Carona.StatusCarona.PENDENTE.name() + "' AND c.vagas_disponiveis > 0"; // Apenas PENDENTES e com vagas

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Carona carona = new Carona(
                    rs.getString("id"),
                    rs.getString("origem"),
                    rs.getString("destino"),
                    LocalDateTime.parse(rs.getString("data_hora_partida")),
                    Carona.StatusCarona.valueOf(rs.getString("status")),
                    rs.getString("motorista_id"),
                    rs.getString("passageiro_id"),
                    rs.getInt("vagas_disponiveis") // **NOVO CAMPO**
                );
                // Você pode adicionar a lógica para buscar o nome do motorista e adicioná-lo
                // ao objeto Carona ou formatá-lo diretamente na PrincipalView.
                System.out.println("Carona disponível carregada: ID " + carona.getId() + " - " + carona.getOrigem() + " -> " + carona.getDestino() +
                                   " (Motorista: " + rs.getString("motorista_nome") + ") Vagas: " + carona.getVagasDisponiveis());
                caronasDisponiveis.add(carona);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar caronas disponíveis: " + e.getMessage());
            e.printStackTrace();
        }
        return caronasDisponiveis;
    }

    /**
     * Busca uma Carona pelo seu ID.
     * @param id O ID da carona a ser buscada.
     * @return Um Optional contendo a Carona se encontrada, ou um Optional vazio.
     */
    public Optional<Carona> findById(String id) {
        // **Modificado: Adicionado 'vagas_disponiveis' ao SQL SELECT**
        String sql = "SELECT id, origem, destino, data_hora_partida, status, motorista_id, passageiro_id, vagas_disponiveis FROM Caronas WHERE id = ?";
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
                    Carona.StatusCarona.valueOf(rs.getString("status")),
                    rs.getString("motorista_id"),
                    rs.getString("passageiro_id"),
                    rs.getInt("vagas_disponiveis") // **NOVO CAMPO**
                );
                return Optional.of(carona);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carona por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
}