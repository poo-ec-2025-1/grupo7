package UFGCarona.model;

/**
 * Interface IAvaliavel: Define um contrato para classes que podem ser avaliadas.
 */
public interface IAvaliavel {
    void receberAvaliacao(Avaliacao avaliacao);
    double calcularMediaAvaliacoes();
}
