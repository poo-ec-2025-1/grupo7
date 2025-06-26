import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * PassageiroDAO: Objeto de Acesso a Dados para a classe Passageiro.
 * Lida com a persistência de Passageiros.
 */
class PassageiroDAO {
    private UsuarioDAO usuarioDAO;

    public PassageiroDAO() {
        this.usuarioDAO = new UsuarioDAO();
    }

    /** Salva um objeto Passageiro no DB. */
    public boolean save(Passageiro passageiro) {
        if (!usuarioDAO.save(passageiro)) {
            System.err.println("Erro ao salvar a parte USUARIO do Passageiro.");
            return false;
        }


        System.out.println("Passageiro salvo: " + passageiro.getEmail());
        return true;
    }

    /** Busca um Passageiro pelo seu ID. */
    public Optional<Passageiro> findById(String id) {
        // Busca os dados do Usuario para reconstruir o Passageiro
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