import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

public class DatabaseManager {
    private static final String DB_FILE_NAME = "ufgcarona.db";
    private static final String URL = "jdbc:sqlite:" + new File(DB_FILE_NAME).getAbsolutePath();

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
            System.out.println("Conexão com o banco de dados SQLite estabelecida em: " + URL);
            System.out.println("Caminho absoluto do DB: " + new File(DB_FILE_NAME).getAbsolutePath());

            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
                System.out.println("PRAGMA foreign_keys = ON; executado.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC SQLite não encontrado.");
        }
        return conn;
    }

    public static void createTables() {
        String sqlVeiculos = "CREATE TABLE IF NOT EXISTS Veiculos (" +
                             "id TEXT PRIMARY KEY," +
                             "placa TEXT NOT NULL UNIQUE," +
                             "modelo TEXT," +
                             "marca TEXT," +
                             "cor TEXT," +
                             "ano INTEGER," +
                             "capacidade_passageiros INTEGER);";

        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios (" +
                             "id TEXT PRIMARY KEY," +
                             "nome TEXT," +
                             "email TEXT NOT NULL UNIQUE," +
                             "senha_hash TEXT," +
                             "telefone TEXT," +
                             "ativo INTEGER);";

        String sqlMotoristas = "CREATE TABLE IF NOT EXISTS Motoristas (" +
                               "usuario_id TEXT PRIMARY KEY," +
                               "numero_habilitacao TEXT," +
                               "categoria_habilitacao TEXT," +
                               "veiculo_id TEXT," +
                               "FOREIGN KEY(usuario_id) REFERENCES Usuarios(id)," +
                               "FOREIGN KEY(veiculo_id) REFERENCES Veiculos(id));";

        
        String sqlCaronas = "CREATE TABLE IF NOT EXISTS Caronas (" +
                            "id TEXT PRIMARY KEY," +
                            "origem TEXT," +
                            "destino TEXT," +
                            "data_hora_partida TEXT," +
                            "status TEXT," +
                            "motorista_id TEXT," +
                            "passageiro_id TEXT," + 
                            "FOREIGN KEY(motorista_id) REFERENCES Motoristas(usuario_id)," +
                            "FOREIGN KEY(passageiro_id) REFERENCES Usuarios(id));"; // passageiro_id referencia Usuarios

        String sqlAvaliacoes = "CREATE TABLE IF NOT EXISTS Avaliacoes (" +
                               "id TEXT PRIMARY KEY," +
                               "nota INTEGER," +
                               "comentario TEXT," +
                               "data_avaliacao TEXT," +
                               "motorista_id TEXT," +
                               "FOREIGN KEY(motorista_id) REFERENCES Motoristas(usuario_id));";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlVeiculos);
            stmt.execute(sqlUsuarios);
            stmt.execute(sqlMotoristas);
            stmt.execute(sqlCaronas); // Executar a criação da nova tabela
            stmt.execute(sqlAvaliacoes);
            System.out.println("Tabelas criadas ou já existentes.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}