import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UFGCaronaApp {
    public static void main(String[] args) {
        // 1. Inicializa o banco de dados e cria as tabelas.
        DatabaseManager.createTables();

        // 2. Cria instâncias dos DAOs.
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        MotoristaDAO motoristaDAO = new MotoristaDAO();
        PassageiroDAO passageiroDAO = new PassageiroDAO(); // Novo DAO
        CaronaDAO caronaDAO = new CaronaDAO(); // Novo DAO

        // --- DEMONSTRAÇÃO: Salvar um novo Motorista UFG
        System.out.println("\n--- DEMONSTRAÇÃO: Salvar um novo Motorista UFG ---");
        Veiculo meuCarro = new Veiculo("PLACA" + System.currentTimeMillis(), "Fusca", "Volkswagen", "Azul", 1970, 3);
        MotoristaUFG motoristaCarlos = new MotoristaUFG(
            "Carlos Pereira", "carlos.pereira." + System.currentTimeMillis() + "@ufg.br",
            "senhaNova", "62998765432",
            "11223344556", "B", meuCarro, "201998765");

        System.out.println("DEBUG APP: Email de motoristaCarlos antes de salvar: " + motoristaCarlos.getEmail());
        boolean salvoMotorista = motoristaDAO.save(motoristaCarlos);
        if (salvoMotorista) {
            System.out.println("Motorista Carlos salvo no DB com ID: " + motoristaCarlos.getId());
        } else {
            System.out.println("Falha ao salvar Motorista Carlos.");
        }

        // --- DEMONSTRAÇÃO: Salvar um novo Passageiro
        System.out.println("\n--- DEMONSTRAÇÃO: Salvar um novo Passageiro ---");
        Passageiro passageiroAna = new Passageiro(
            "Ana Souza", "ana.souza." + System.currentTimeMillis() + "@ufg.br",
            "senhaAna", "62991112222");
        boolean salvoPassageiro = passageiroDAO.save(passageiroAna);
        if (salvoPassageiro) {
            System.out.println("Passageiro Ana salvo no DB com ID: " + passageiroAna.getId());
        } else {
            System.out.println("Falha ao salvar Passageiro Ana.");
        }


        // --- DEMONSTRAÇÃO: Motorista oferece uma Carona
        System.out.println("\n--- DEMONSTRAÇÃO: Motorista Carlos oferece uma Carona ---");
        Carona caronaOferecida = new Carona(
            "Campus Samambaia", "Terminal Bandeiras", LocalDateTime.now().plusHours(2),
            motoristaCarlos.getId()); // Motorista oferece a carona
        boolean salvoCarona = caronaDAO.save(caronaOferecida);
        if (salvoCarona) {
            System.out.println("Carona oferecida e salva com ID: " + caronaOferecida.getId());
        } else {
            System.out.println("Falha ao oferecer e salvar carona.");
        }

        // --- DEMONSTRAÇÃO: Listar Caronas Disponíveis ---
        System.out.println("\n--- DEMONSTRAÇÃO: Listar Caronas Disponíveis ---");
        List<Carona> caronasDisponiveis = caronaDAO.findAvailableRides();
        if (caronasDisponiveis.isEmpty()) {
            System.out.println("Nenhuma carona disponível no momento.");
        } else {
            System.out.println("Caronas disponíveis encontradas (" + caronasDisponiveis.size() + "):");
            for (Carona c : caronasDisponiveis) {
                System.out.println("- ID: " + c.getId() + ", Origem: " + c.getOrigem() + ", Destino: " + c.getDestino() + ", Horário: " + c.getDataHoraPartida());
            }
        }

        // --- DEMONSTRAÇÃO: Passageiro solicita uma Carona
        System.out.println("\n--- DEMONSTRAÇÃO: Passageiro Ana solicita uma Carona ---");
        if (!caronasDisponiveis.isEmpty()) {
            Carona caronaParaSolicitar = caronasDisponiveis.get(0); // Pega a primeira carona disponível
            boolean solicitacaoSucesso = passageiroAna.pedirCarona(caronaDAO, caronaParaSolicitar.getId());
            if (solicitacaoSucesso) {
                System.out.println("Passageiro Ana solicitou carona ID: " + caronaParaSolicitar.getId());
            } else {
                System.out.println("Falha na solicitação da carona ID: " + caronaParaSolicitar.getId());
            }
        } else {
            System.out.println("Não há caronas para solicitar no momento.");
        }


        //  Exemplo de login
        System.out.println("\n--- Exemplo de login (motorista Joao) ---");
        Veiculo meuCarro2 = new Veiculo("ABC" + System.currentTimeMillis(), "Palio", "Fiat", "Prata", 2018, 4);
        MotoristaUFG motoristaJoao = new MotoristaUFG(
            "João Silva", "joao.silva." + System.currentTimeMillis() + "@ufg.br",
            "senha123", "62991234567",
            "12345678901", "B", meuCarro2, "202012345");

        if (motoristaJoao.fazerLogin(motoristaJoao.getEmail(), "senha123")) {
            System.out.println("Login de " + motoristaJoao.getNome() + " bem-sucedido.");
        } else {
            System.out.println("Falha no login de " + motoristaJoao.getNome() + ".");
        }
    }
}