package UFGCarona.model;

import java.sql.*;
import java.util.Optional;

public class UsuarioDAO {
    private Connection connect() {
        return DatabaseManager.connect();
    }

    public boolean save(Usuario usuario) {
        String sql = "INSERT INTO Usuarios(id, nome, email, senha_hash, telefone, ativo) VALUES(?,?,?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            System.out.println("\n--- SALVANDO USUÁRIO NO DB (USUARIODAO.SAVE) ---");
            System.out.println("ID: " + usuario.getId());
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("Email: " + usuario.getEmail());
            System.out.println("Senha Hash: " + usuario.getSenhaHash());
            System.out.println("Telefone: " + usuario.getTelefone());
            System.out.println("Ativo: " + (usuario.isAtivo() ? 1 : 0));

            pstmt.setString(1, usuario.getId());
            pstmt.setString(2, usuario.getNome());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getSenhaHash());
            pstmt.setString(5, usuario.getTelefone());
            pstmt.setInt(6, usuario.isAtivo() ? 1 : 0);

            boolean success = pstmt.executeUpdate() > 0;
            System.out.println("Usuário salvo com sucesso? " + success);
            return success;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar usuário: " + e.getMessage());
            e.printStackTrace(); // Para depuração
            return false;
        }
    }

    /**
     * Busca um usuário pelo email e tenta instanciar o tipo correto (Passageiro ou MotoristaUFG).
     * @param email O email do usuário a ser buscado.
     * @return Um Optional contendo o objeto Usuario (Passageiro ou MotoristaUFG) se encontrado, ou Optional vazio.
     */
    public Optional<Usuario> findByEmail(String email) {
        String sqlUsuario = "SELECT id, nome, email, senha_hash, telefone, ativo FROM Usuarios WHERE email = ?";
        try (Connection conn = connect();
             PreparedStatement pstmtUsuario = conn.prepareStatement(sqlUsuario)) {

            System.out.println("\n--- BUSCANDO USUÁRIO POR EMAIL (USUARIODAO.FINDBYEMAIL) ---");
            System.out.println("Email buscado: " + email);

            pstmtUsuario.setString(1, email);
            ResultSet rsUsuario = pstmtUsuario.executeQuery();

            if (rsUsuario.next()) {
                String userId = rsUsuario.getString("id");
                String nome = rsUsuario.getString("nome");
                String emailDb = rsUsuario.getString("email");
                String senhaHash = rsUsuario.getString("senha_hash");
                String telefone = rsUsuario.getString("telefone");
                boolean ativo = rsUsuario.getInt("ativo") == 1;

                System.out.println("Usuário básico encontrado no DB:");
                System.out.println("  ID: " + userId);
                System.out.println("  Nome: " + nome);
                System.out.println("  Email: " + emailDb);
                System.out.println("  Senha Hash (do DB): " + senhaHash);
                System.out.println("  Ativo: " + ativo);

                // Tenta carregar dados específicos de Motorista
                String sqlMotorista = "SELECT numero_habilitacao, categoria_habilitacao, veiculo_id FROM Motoristas WHERE usuario_id = ?";
                try (PreparedStatement pstmtMotorista = conn.prepareStatement(sqlMotorista)) {
                    pstmtMotorista.setString(1, userId);
                    ResultSet rsMotorista = pstmtMotorista.executeQuery();

                    if (rsMotorista.next()) {
                        // É um Motorista, agora busca os dados do Veículo
                        String veiculoId = rsMotorista.getString("veiculo_id");
                        Veiculo veiculo = null;
                        if (veiculoId != null) {
                            String sqlVeiculo = "SELECT id, placa, modelo, marca, cor, ano, capacidade_passageiros FROM Veiculos WHERE id = ?";
                            try (PreparedStatement pstmtVeiculo = conn.prepareStatement(sqlVeiculo)) {
                                pstmtVeiculo.setString(1, veiculoId);
                                ResultSet rsVeiculo = pstmtVeiculo.executeQuery();
                                if (rsVeiculo.next()) {
                                    veiculo = new Veiculo(
                                        rsVeiculo.getString("id"),
                                        rsVeiculo.getString("placa"),
                                        rsVeiculo.getString("modelo"),
                                        rsVeiculo.getString("marca"),
                                        rsVeiculo.getString("cor"),
                                        rsVeiculo.getInt("ano"),
                                        rsVeiculo.getInt("capacidade_passageiros")
                                    );
                                    System.out.println("  Veículo encontrado para motorista: " + veiculo.getPlaca());
                                }
                            }
                        }

                        // Regenera o idUFG (assumindo que não é persistido diretamente na tabela Motoristas)
                        // Lógica de gerarIdUFG copiada do CadastroController
                        String idUFG = "UFG" + telefone.substring(telefone.length() - 4);
                        System.out.println("  idUFG gerado: " + idUFG);

                        System.out.println("  Usuário é um MotoristaUFG.");
                        return Optional.of(new MotoristaUFG(
                            userId, nome, emailDb, senhaHash, telefone, ativo,
                            rsMotorista.getString("numero_habilitacao"),
                            rsMotorista.getString("categoria_habilitacao"),
                            veiculo, // Pode ser null se o veículo não for encontrado
                            idUFG
                        ));
                    }
                }

                // Se não é Motorista, assume que é Passageiro
                System.out.println("  Usuário é um Passageiro.");
                return Optional.of(new Passageiro(
                    userId, nome, emailDb, senhaHash, telefone, ativo
                ));

            } else {
                System.out.println("Usuário com email " + email + " NÃO encontrado no DB.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por email: " + e.getMessage());
            e.printStackTrace(); // Para depuração
        }
        return Optional.empty();
    }

    public boolean validarLogin(String email, String senha) {
        System.out.println("\n--- VALIDANDO LOGIN (USUARIODAO.VALIDARLOGIN) ---");
        System.out.println("Email digitado: " + email);
        System.out.println("Senha digitada: " + senha);

        Optional<Usuario> usuario = findByEmail(email);

        System.out.println("Usuário encontrado (isPresent)? " + usuario.isPresent());

        if (usuario.isPresent()) {
            System.out.println("Usuário está Ativo? " + usuario.get().isAtivo());
            String senhaHashDoBanco = usuario.get().getSenhaHash();
            String senhaDigitadaComHash = "hashed_" + senha;

            System.out.println("Senha Hash do Banco: " + senhaHashDoBanco);
            System.out.println("Senha Digitada (com hash para comparação): " + senhaDigitadaComHash);
            
            boolean hashMatches = senhaHashDoBanco.equals(senhaDigitadaComHash);
            System.out.println("Hash do banco == Hash digitado? " + hashMatches);

            return usuario.get().isAtivo() && hashMatches;
        }
        return false;
    }
}