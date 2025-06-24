import java.util.UUID;

/**
 * Classe Veiculo: representa o veículo do motorista.
 */
class Veiculo {
    private String id; // ID único para persistência.
    private String placa;
    private String modelo;
    private String marca;
    private String cor;
    private int ano;
    private int capacidadePassageiros;

    /** Construtor para criar um NOVO veículo. */
    public Veiculo(String placa, String modelo, String marca, String cor, int ano, int capacidadePassageiros) {
        this.id = UUID.randomUUID().toString();
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.cor = cor;
        this.ano = ano;
        this.capacidadePassageiros = capacidadePassageiros;
    }

    /** Construtor para carregar veículo do banco de dados. */
    public Veiculo(String id, String placa, String modelo, String marca, String cor, int ano, int capacidadePassageiros) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.cor = cor;
        this.ano = ano;
        this.capacidadePassageiros = capacidadePassageiros;
    }

    // Getters e Setters.
    public String getId() { return id; }
    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public String getMarca() { return marca; }
    public String getCor() { return cor; }
    public int getAno() { return ano; }
    public int getCapacidadePassageiros() { return capacidadePassageiros; }

    public void setId(String id) { this.id = id; }
    public void setPlaca(String placa) { this.placa = placa; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setCor(String cor) { this.cor = cor; }
    public void setAno(int ano) { this.ano = ano; }
    public void setCapacidadePassageiros(int capacidadePassageiros) { this.capacidadePassageiros = capacidadePassageiros; }
}