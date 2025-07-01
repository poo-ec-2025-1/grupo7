package UFGCarona.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CaronaDAO {

  
    public boolean save(Carona carona) {
       
        String sql = "INSERT INTO Caronas(id, origem, destino, data_hora_partida, status, motorista_id, passageiro_id, vagas_disponiveis) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, carona.getId());
            pstmt.setString(2, carona.getOrigem());
            pstmt.setString(3, carona.getDestino());
            pstmt.setString(4, carona.getDataHoraPartida().toString()); 
            pstmt.setString(5, carona.getStatus().name()); 
            pstmt.setString(6, carona.getMotoristaId());
            pstmt.setString(7, carona.getPassageiroId()); 
            pstmt.setInt(8, carona.getVagasDisponiveis()); 
            pstmt.executeUpdate();
            System.out.println("Carona salva: " + carona.getOrigem() + " -> " + carona.getDestino() + " com " + carona.getVagasDisponiveis() + " vagas.");
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar carona: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


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

   
    public boolean requestCarona(String caronaId, String passageiroId) {
      
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

  
        String sql = "UPDATE Caronas SET status = ?, passageiro_id = ?, vagas_disponiveis = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Carona.StatusCarona.CONFIRMADA.name());
            pstmt.setString(2, passageiroId);
            pstmt.setInt(3, carona.getVagasDisponiveis() - 1); 
            pstmt.setString(4, caronaId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao solicitar carona: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

 
    public List<Carona> findAvailableRides() {
        List<Carona> caronasDisponiveis = new ArrayList<>();
      
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
                    rs.getInt("vagas_disponiveis") 
                );
             
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

   
     
    public Optional<Carona> findById(String id) {
      
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
                    rs.getInt("vagas_disponiveis")
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
