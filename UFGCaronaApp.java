import java.time.LocalDateTime;
import java.util.Optional;

// Nenhuma declaração de import para as classes locais é necessária
// pois todas estão no mesmo pacote padrão.

public class UFGCaronaApp {
    public static void main(String[] args) {
        // 1. Inicializa o banco de dados e cria as tabelas.
        DatabaseManager.createTables();

        // 2. Cria instâncias dos DAOs para interagir com o DB.
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        MotoristaDAO motoristaDAO = new MotoristaDAO();

        // --- DEMONSTRAÇÃO: Salvar um novo Motorista UFG ---
        System.out.println("--- Salvando um novo Motorista UFG ---");
        // Usa System.currentTimeMillis() para garantir unicidade da placa e email a cada execução.
        Veiculo meuCarro = new Veiculo("PLACA" + System.currentTimeMillis(), "Fusca", "Volkswagen", "Azul", 1970, 3);
        MotoristaUFG motoristaCarlos = new MotoristaUFG(
            "Carlos Pereira", "carlos.pereira." + System.currentTimeMillis() + "@ufg.br",
            "senhaNova", "62998765432",
            "11223344556", "B", meuCarro, "201998765");

        // DEBUG: Email antes de salvar.
        System.out.println("DEBUG APP: Email de motoristaCarlos antes de salvar: " + motoristaCarlos.getEmail());

        boolean salvo = motoristaDAO.save(motoristaCarlos);
        if (salvo) {
            System.out.println("Motorista Carlos salvo no DB com ID: " + motoristaCarlos.getId());
        } else {
            System.out.println("Falha ao salvar Motorista Carlos.");
        }


        //Recuperar o Motorista Carlos do DB
        System.out.println("\n--- Recuperando Motorista Carlos do DB ---");
        // Tenta recuperar o motorista recém-salvo.
        Optional<MotoristaUFG> motoristaRecuperadoOpt = motoristaDAO.findById(motoristaCarlos.getId());

        if (motoristaRecuperadoOpt.isPresent()) {
            MotoristaUFG motoristaRecuperado = motoristaRecuperadoOpt.get();
            System.out.println("Motorista recuperado: " + motoristaRecuperado.getNome());
            System.out.println("Email: " + motoristaRecuperado.getEmail());
            System.out.println("Placa do veículo: " + motoristaRecuperado.getVeiculo().getPlaca());
            System.out.println("Telefone: " + motoristaRecuperado.getTelefone()); // Correção: getTelefone()
            System.out.println("ID UFG (dummy): " + motoristaRecuperado.getIdUFG());

            // Teste de login com o objeto recuperado do DB.
            if (motoristaRecuperado.fazerLogin(motoristaRecuperado.getEmail(), "senhaNova")) {
                System.out.println("Login com motorista recuperado bem-sucedido.");
            } else {
                System.out.println("Falha no login com motorista recuperado.");
            }
        } else {
            System.out.println("Motorista Carlos não encontrado no DB.");
        }

        //Exemplo de login
        System.out.println("\n--- Exemplo de login (sem persistência explícita aqui) ---");
        // Cria um novo motorista
        Veiculo meuCarro2 = new Veiculo("ABC" + System.currentTimeMillis(), "Palio", "Fiat", "Prata", 2018, 4);
        MotoristaUFG motoristaJoao = new MotoristaUFG(
            "João Silva", "joao.silva." + System.currentTimeMillis() + "@ufg.br",
            "senha123", "62991234567",
            "12345678901", "B", meuCarro2, "202012345");

        // Usa o e-mail EXATO do objeto motoristaJoao para o login para que funcione.
        if (motoristaJoao.fazerLogin(motoristaJoao.getEmail(), "senha123")) { // CORREÇÃO AQUI
            System.out.println("Login de " + motoristaJoao.getNome() + " bem-sucedido.");
        } else {
            System.out.println("Falha no login de " + motoristaJoao.getNome() + ".");
        }
    }
}