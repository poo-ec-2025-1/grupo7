package UFGCarona.model;

import java.sql.*;
import java.util.Optional;

public class MotoristaDAO {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private Connection connect() {
        return DatabaseManager.connect();
    }

    public boolean save(Motorista motorista) {
        // Primeiro salva na tabela Usuarios
        if (!usuarioDAO.save(motorista)) {
            return false;
        }
        
        // Depois salva na tabela Motoristas
        String sql = "INSERT INTO Motoristas(usuario_id, numero_habilitacao, categoria_habilitacao, veiculo_id) VALUES(?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, motorista.getId());
            pstmt.setString(2, motorista.getNumeroHabilitacao());
            pstmt.setString(3, motorista.getCategoriaHabilitacao());
            // Garante que o ID do veículo é salvo, mesmo que o veículo seja nulo (o que não deveria acontecer ao cadastrar)
            pstmt.setString(4, motorista.getVeiculo() != null ? motorista.getVeiculo().getId() : null);
            
            boolean success = pstmt.executeUpdate() > 0;
            System.out.println("Motorista salvo com sucesso? " + success);
            return success;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar motorista: " + e.getMessage());
            e.printStackTrace(); // Para depuração
            return false;
        }
    }
    
    /**
     * Busca um motorista pelo ID.
     * Este método agora é mais simples, apenas verifica a existência na tabela Motoristas.
     * O carregamento completo de MotoristaUFG (com dados de Usuario e Veiculo)
     * é feito em UsuarioDAO.findByEmail.
     * @param id O ID do usuário (motorista) a ser buscado.
     * @return Um Optional contendo um Motorista genérico se encontrado, ou Optional vazio.
     */
    public Optional<Motorista> findById(String id) {
        String sql = "SELECT usuario_id, numero_habilitacao, categoria_habilitacao, veiculo_id FROM Motoristas WHERE usuario_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Retorna um Motorista genérico, sem carregar todos os detalhes de Usuario ou Veiculo.
                // O carregamento completo é feito em UsuarioDAO.findByEmail.
                return Optional.of(new Motorista(
                    rs.getString("usuario_id"),
                    null, null, null, null, true, // Dados básicos do usuário não são carregados aqui
                    rs.getString("numero_habilitacao"),
                    rs.getString("categoria_habilitacao"),
                    null // Veículo não é carregado aqui
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar motorista por ID: " + e.getMessage());
            e.printStackTrace(); // Para depuração
        }
        return Optional.empty();
    }
}