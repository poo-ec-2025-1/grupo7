package UFGCarona.model;

import java.sql.*;
import java.io.File;

public class DatabaseManager {
    private static final String DB_FILE_NAME = "ufgcarona.db";
    // Obtém o caminho absoluto para o arquivo do banco de dados.
    // Isso ajuda a garantir que o banco seja criado no diretório esperado.
    private static final String URL = "jdbc:sqlite:" + new File(DB_FILE_NAME).getAbsolutePath();

    /**
     * Estabelece uma conexão com o banco de dados SQLite.
     * @return Um objeto Connection se a conexão for bem-sucedida, ou null em caso de erro.
     */
    public static Connection connect() {
        try {
            // Carrega o driver JDBC para SQLite
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }

    /**
     * Cria as tabelas necessárias no banco de dados se elas ainda não existirem.
     */
    public static void createTables() {
        // Array de comandos SQL para criar as tabelas
        String[] sqls = {
            "CREATE TABLE IF NOT EXISTS Usuarios (" +
                "id TEXT PRIMARY KEY," +
                "nome TEXT NOT NULL," +
                "email TEXT NOT NULL UNIQUE," +
                "senha_hash TEXT NOT NULL," +
                "telefone TEXT," +
                "ativo INTEGER DEFAULT 1)",

            "CREATE TABLE IF NOT EXISTS Veiculos (" +
                "id TEXT PRIMARY KEY," +
                "placa TEXT NOT NULL UNIQUE," +
                "modelo TEXT," +
                "marca TEXT," +
                "cor TEXT," +
                "ano INTEGER," +
                "capacidade_passageiros INTEGER)",
                
            "CREATE TABLE IF NOT EXISTS Motoristas (" +
                "usuario_id TEXT PRIMARY KEY," +
                "numero_habilitacao TEXT," +
                "categoria_habilitacao TEXT," +
                "veiculo_id TEXT," +
                "FOREIGN KEY(usuario_id) REFERENCES Usuarios(id)," +
                "FOREIGN KEY(veiculo_id) REFERENCES Veiculos(id))",
                
            // **Modificado: Tabela Caronas com o novo campo 'vagas_disponiveis'**
            "CREATE TABLE IF NOT EXISTS Caronas (" +
                "id TEXT PRIMARY KEY," +
                "origem TEXT NOT NULL," +
                "destino TEXT NOT NULL," +
                "data_hora_partida TEXT NOT NULL," +
                "status TEXT NOT NULL," + // PENDENTE, CONFIRMADA, FINALIZADA, CANCELADA
                "motorista_id TEXT NOT NULL," +
                "passageiro_id TEXT," + // Pode ser NULL
                "vagas_disponiveis INTEGER NOT NULL DEFAULT 1," + // **NOVO CAMPO COM VALOR PADRÃO**
                "FOREIGN KEY(motorista_id) REFERENCES Usuarios(id)," +
                "FOREIGN KEY(passageiro_id) REFERENCES Usuarios(id))"
        };

        // Tenta executar os comandos SQL para criar as tabelas
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            
            for (String sql : sqls) {
                stmt.execute(sql);
            }
            System.out.println("Tabelas criadas/atualizadas com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
            e.printStackTrace(); // Imprime o stack trace completo para depuração
        }
    }
}