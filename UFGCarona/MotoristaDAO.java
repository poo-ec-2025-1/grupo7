import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * MotoristaDAO: DAO para a classe MotoristaUFG.
 * Orquestra a persistência de um MotoristaUFG, incluindo seus dados de Usuario e Veiculo.
 */
class MotoristaDAO {
    private VeiculoDAO veiculoDAO; // Dependência para persistir Veiculos.
    private UsuarioDAO usuarioDAO; // Dependência para persistir Usuarios.

    public MotoristaDAO() {
        this.veiculoDAO = new VeiculoDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    /** Salva um MotoristaUFG no DB, usando transações para garantir consistência. */
    public boolean save(MotoristaUFG motorista) {
        Connection conn = null;
        try {
            conn = DatabaseManager.connect();
            conn.setAutoCommit(false); // Inicia uma transação.

            // 1. Salvar o Veículo e verificar se foi bem-sucedido.
            if (!veiculoDAO.save(motorista.getVeiculo())) {
                conn.rollback();
                return false;
            }

            // 2. Salvar o Usuário e verificar se foi bem-sucedido.
            if (!usuarioDAO.save(motorista)) {
                conn.rollback();
                return false;
            }

            // 3. Salvar a parte específica do Motorista.
            String sql = "INSERT INTO Motoristas(usuario_id, numero_habilitacao, categoria_habilitacao, veiculo_id) VALUES(?,?,?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, motorista.getId());
                pstmt.setString(2, motorista.getNumeroHabilitacao());
                pstmt.setString(3, motorista.getCategoriaHabilitacao());
                pstmt.setString(4, motorista.getVeiculo().getId());
                pstmt.executeUpdate();
            }

            conn.commit(); // Confirma a transação.
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao salvar Motorista UFG: " + e.getMessage());
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ... */ }
            }
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { /* ... */ }
            }
        }
    }

    /** Busca um MotoristaUFG pelo ID do usuário. */
    public Optional<MotoristaUFG> findById(String usuarioId) {
        String sql = "SELECT u.id, u.nome, u.email, u.senha_hash, u.telefone, u.ativo, " +
                     "m.numero_habilitacao, m.categoria_habilitacao, m.veiculo_id " +
                     "FROM Usuarios u INNER JOIN Motoristas m ON u.id = m.usuario_id WHERE u.id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) { // Se encontrar uma linha.
                // 1. Recuperar o objeto Veiculo associado.
                Optional<Veiculo> optVeiculo = new VeiculoDAO().findById(rs.getString("veiculo_id"));
                if (optVeiculo.isEmpty()) { return Optional.empty(); }
                Veiculo veiculo = optVeiculo.get();

                // 2. Criar o objeto MotoristaUFG populado.
                MotoristaUFG motorista = new MotoristaUFG(
                    rs.getString("id"), rs.getString("nome"), rs.getString("email"),
                    rs.getString("senha_hash"), rs.getString("telefone"), rs.getInt("ativo") == 1,
                    rs.getString("numero_habilitacao"), rs.getString("categoria_habilitacao"),
                    veiculo, "dummyIDUFG" // idUFG não está no schema simplificado.
                );
                return Optional.of(motorista);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar Motorista UFG por ID: " + e.getMessage());
        }
        return Optional.empty();
    }
}