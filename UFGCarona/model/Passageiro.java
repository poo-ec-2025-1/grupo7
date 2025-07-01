package UFGCarona.model;

/**
 * Classe Passageiro: Representa um usuário no papel de passageiro.
 * Herda de Usuario, adicionando funcionalidades específicas de um passageiro.
 */
public class Passageiro extends Usuario {

    /**
     * Construtor para criar um NOVO passageiro.
     */
    public Passageiro(String nome, String email, String senha, String telefone) {
        super(nome, email, senha, telefone);
    }

    /**
     * Construtor para carregar passageiro do banco de dados.
     */
    public Passageiro(String id, String nome, String email, String senhaHash, String telefone, boolean ativo) {
        super(id, nome, email, senhaHash, telefone, ativo);
    }

    @Override
    public void criarConta() {
        System.out.println("Conta de Passageiro criada para: " + this.getNome());
    }

    /**
     * Método para um passageiro pedir uma carona.
     * Retorna true se a solicitação foi bem-sucedida, false caso contrário.
     *
     * @param caronaDAO O DAO de Carona para interagir com o DB.
     * @param caronaId O ID da carona a ser solicitada.
     * @return true se a solicitação foi bem-sucedida, false caso contrário.
     */
    public boolean pedirCarona(CaronaDAO caronaDAO, String caronaId) {
        System.out.println("Passageiro " + this.getNome() + " solicitando carona ID: " + caronaId);
        // RETORNA O RESULTADO do caronaDAO.requestCarona()
        return caronaDAO.requestCarona(caronaId, this.getId()); // MUDANÇA AQUI: adicionado 'return'
    }
}
