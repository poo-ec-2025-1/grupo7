import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * VeiculoDAO: Objeto de Acesso a Dados para a classe Veiculo.
 * Encapsula a lógica de persistência (salvar, buscar) para Veiculos no DB.
 */
class VeiculoDAO {

    /** Salva um objeto Veiculo no DB. */
    public boolean save(Veiculo veiculo) {
        String sql = "INSERT INTO Veiculos(id, placa, modelo, marca, cor, ano, capacidade_passageiros) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, veiculo.getId());
            pstmt.setString(2, veiculo.getPlaca());
            pstmt.setString(3, veiculo.getModelo());
            pstmt.setString(4, veiculo.getMarca());
            pstmt.setString(5, veiculo.getCor());
            pstmt.setInt(6, veiculo.getAno());
            pstmt.setInt(7, veiculo.getCapacidadePassageiros());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar veículo: " + e.getMessage());
            return false;
        }
    }

    /** Busca um Veiculo pelo ID. */
    public Optional<Veiculo> findById(String id) {
        String sql = "SELECT id, placa, modelo, marca, cor, ano, capacidade_passageiros FROM Veiculos WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Veiculo veiculo = new Veiculo(
                    rs.getString("id"), rs.getString("placa"), rs.getString("modelo"),
                    rs.getString("marca"), rs.getString("cor"), rs.getInt("ano"),
                    rs.getInt("capacidade_passageiros")
                );
                return Optional.of(veiculo);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar veículo por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    /** Busca um Veiculo pela sua placa. */
    public Optional<Veiculo> findByPlaca(String placa) {
        String sql = "SELECT id, placa, modelo, marca, cor, ano, capacidade_passageiros FROM Veiculos WHERE placa = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, placa);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Veiculo veiculo = new Veiculo(
                    rs.getString("id"), rs.getString("placa"), rs.getString("modelo"),
                    rs.getString("marca"), rs.getString("cor"), rs.getInt("ano"),
                    rs.getInt("capacidade_passageiros")
                );
                return Optional.of(veiculo);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar veículo por placa: " + e.getMessage());
        }
        return Optional.empty();
    }
}