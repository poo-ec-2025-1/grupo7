import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * UsuarioDAO: DAO para a classe Usuario.
 * Lida com a persistência de dados de usuários genéricos.
 */
class UsuarioDAO {

    /** Salva um objeto Usuario no DB. */
    public boolean save(Usuario usuario) {
        String sql = "INSERT INTO Usuarios(id, nome, email, senha_hash, telefone, ativo) VALUES(?,?,?,?,?,?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // DEBUG: Confirma o email antes da inserção.
            System.out.println("DEBUG USUARIO_DAO: Email do usuário: " + usuario.getEmail());

            pstmt.setString(1, usuario.getId());
            pstmt.setString(2, usuario.getNome());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getSenhaHash());
            pstmt.setString(5, usuario.getTelefone()); // Usando getTelefone()
            pstmt.setInt(6, usuario.isAtivo() ? 1 : 0);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar usuário: " + e.getMessage());
            return false;
        }
    }

    /** Busca um usuário pelo seu ID. */
    public Optional<Usuario> findById(String id) {
        String sql = "SELECT id, nome, email, senha_hash, telefone, ativo FROM Usuarios WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Em um cenário real, você criaria uma instância da subclasse correta.
                // Para este exemplo, apenas retornamos Optional.of(new Usuario(...)) ou um DTO.
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    /** Busca um usuário pelo seu email. */
    public Optional<Usuario> findByEmail(String email) {
        String sql = "SELECT id, nome, email, senha_hash, telefone, ativo FROM Usuarios WHERE email = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Similar ao findById, retornaria o objeto populado.
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por email: " + e.getMessage());
        }
        return Optional.empty();
    }
}