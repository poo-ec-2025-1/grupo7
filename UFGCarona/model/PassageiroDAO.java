package UFGCarona.model;

import java.sql.*;
import java.util.Optional;

public class PassageiroDAO {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean save(Passageiro passageiro) {
        return usuarioDAO.save(passageiro);
    }

    public Optional<Passageiro> findById(String id) {
        String sql = "SELECT id, nome, email, senha_hash, telefone, ativo FROM Usuarios WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Passageiro passageiro = new Passageiro(
                    rs.getString("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha_hash"),
                    rs.getString("telefone"),
                    rs.getInt("ativo") == 1
                );
                return Optional.of(passageiro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar passageiro por ID: " + e.getMessage());
        }
        return Optional.empty();
    }
}