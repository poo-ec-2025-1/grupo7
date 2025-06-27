import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UFGCaronaApp {
    public static void main(String[] args) {
        DatabaseManager.createTables();

        VeiculoDAO veiculoDAO = new VeiculoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        MotoristaDAO motoristaDAO = new MotoristaDAO();
        PassageiroDAO passageiroDAO = new PassageiroDAO();
        CaronaDAO caronaDAO = new CaronaDAO();

        System.out.println("\n--- Demonstração: Salvar Motorista UFG ---");
        Veiculo meuCarro = new Veiculo("PLACA" + System.currentTimeMillis(), "Fusca", "Volkswagen", "Azul", 1970, 3);
        MotoristaUFG motoristaCarlos = new MotoristaUFG(
            "Carlos Pereira", "carlos.pereira." + System.currentTimeMillis() + "@discente.ufg.br",
            "senhaNova", "62998765432",
            "11223344556", "B", meuCarro, "201998765");

        boolean salvoMotorista = motoristaDAO.save(motoristaCarlos);
        if (salvoMotorista) {
            System.out.println("Motorista Carlos salvo no DB com ID: " + motoristaCarlos.getId());
        } else {
            System.out.println("Falha ao salvar Motorista Carlos.");
        }

        System.out.println("\n--- Demonstração: Salvar Passageiro ---");
        Passageiro passageiroAna = new Passageiro(
            "Ana Souza", "ana.souza." + System.currentTimeMillis() + "@discente.ufg.br",
            "senhaAna", "62991112222");
        boolean salvoPassageiro = passageiroDAO.save(passageiroAna);
        if (salvoPassageiro) {
            System.out.println("Passageiro Ana salvo no DB com ID: " + passageiroAna.getId());
        } else {
            System.out.println("Falha ao salvar Passageiro Ana.");
        }

        System.out.println("\n--- Demonstração: Motorista Carlos oferece uma Carona ---");
        Carona caronaOferecida = new Carona(
            "Campus Samambaia", "Terminal Bandeiras", LocalDateTime.now().plusHours(2),
            motoristaCarlos.getId());
        boolean salvoCarona = caronaDAO.save(caronaOferecida);
        if (salvoCarona) {
            System.out.println("Carona oferecida e salva com ID: " + caronaOferecida.getId());
        } else {
            System.out.println("Falha ao oferecer e salvar carona.");
        }

        System.out.println("\n--- Demonstração: Listar Caronas Disponíveis ---");
        List<Carona> caronasDisponiveis = caronaDAO.findAvailableRides();
        if (caronasDisponiveis.isEmpty()) {
            System.out.println("Nenhuma carona disponível no momento.");
        } else {
            System.out.println("Caronas disponíveis encontradas (" + caronasDisponiveis.size() + "):");
            for (Carona c : caronasDisponiveis) {
                System.out.println("- ID: " + c.getId() + ", Origem: " + c.getOrigem() + ", Destino: " + c.getDestino() + ", Horário: " + c.getDataHoraPartida());
            }
        }

        System.out.println("\n--- Demonstração: Passageiro Ana solicita uma Carona ---");
        if (!caronasDisponiveis.isEmpty()) {
            Carona caronaParaSolicitar = caronasDisponiveis.get(0);
            boolean solicitacaoSucesso = passageiroAna.pedirCarona(caronaDAO, caronaParaSolicitar.getId());
            if (solicitacaoSucesso) {
                System.out.println("Passageiro Ana solicitou carona ID: " + caronaParaSolicitar.getId());
            } else {
                System.out.println("Falha na solicitação da carona ID: " + caronaParaSolicitar.getId());
            }
        } else {
            System.out.println("Não há caronas para solicitar no momento.");
        }

        System.out.println("\n--- Teste: Criação de Aluno UFG (e-mail INVÁLIDO) ---");
        try {
            AlunoUFG alunoInvalido = new AlunoUFG(
                "Maria Teste", "maria.teste." + System.currentTimeMillis() + "@gmail.com", "senhaMaria", "62993334444", "202112345");
            System.out.println("ERRO: Aluno com e-mail inválido criado com sucesso (NÃO DEVERIA ACONTECER).");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCESSO: Erro esperado ao tentar criar Aluno com e-mail inválido:");
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- Teste: Criação de Aluno UFG (e-mail VÁLIDO) ---");
        try {
            AlunoUFG alunoValido = new AlunoUFG(
                "Pedro Valido", "pedro.valido." + System.currentTimeMillis() + "@discente.ufg.br", "senhaPedro", "62995556666", "202254321");
            System.out.println("SUCESSO: Aluno Pedro criado com e-mail válido: " + alunoValido.getEmail());
        } catch (IllegalArgumentException e) {
            System.out.println("ERRO: Falha ao criar Aluno com e-mail VÁLIDO (NÃO DEVERIA ACONTECER):");
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- Exemplo: Login de Motorista (Joao) ---");
        Veiculo meuCarro2 = new Veiculo("ABC" + System.currentTimeMillis(), "Palio", "Fiat", "Prata", 2018, 4);
        MotoristaUFG motoristaJoao = new MotoristaUFG(
            "João Silva", "joao.silva." + System.currentTimeMillis() + "@discente.ufg.br",
            "senha123", "62991234567",
            "12345678901", "B", meuCarro2, "202012345");

        if (motoristaJoao.fazerLogin(motoristaJoao.getEmail(), "senha123")) {
            System.out.println("Login de " + motoristaJoao.getNome() + " bem-sucedido.");
        } else {
            System.out.println("Falha no login de " + motoristaJoao.getNome() + ".");
        }
    }
}