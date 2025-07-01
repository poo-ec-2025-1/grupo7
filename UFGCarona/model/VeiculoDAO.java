package UFGCarona.model;

import java.sql.*;
import java.util.Optional;

public class VeiculoDAO {
    private Connection connect() {
        return DatabaseManager.connect();
    }

    public boolean save(Veiculo veiculo) {
        String sql = "INSERT INTO Veiculos(id, placa, modelo, marca, cor, ano, capacidade_passageiros) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, veiculo.getId());
            pstmt.setString(2, veiculo.getPlaca().toUpperCase());
            pstmt.setString(3, veiculo.getModelo());
            pstmt.setString(4, veiculo.getMarca());
            pstmt.setString(5, veiculo.getCor());
            pstmt.setInt(6, veiculo.getAno());
            pstmt.setInt(7, veiculo.getCapacidadePassageiros());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar veículo: " + e.getMessage());
            return false;
        }
    }

    public Optional<Veiculo> findByPlaca(String placa) {
        String sql = "SELECT * FROM Veiculos WHERE UPPER(placa) = UPPER(?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, placa.trim());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(new Veiculo(
                    rs.getString("id"),
                    rs.getString("placa"),
                    rs.getString("modelo"),
                    rs.getString("marca"),
                    rs.getString("cor"),
                    rs.getInt("ano"),
                    rs.getInt("capacidade_passageiros")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar veículo: " + e.getMessage());
        }
        return Optional.empty();
    }
}