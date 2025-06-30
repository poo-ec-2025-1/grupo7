/**
 * Interface IAvaliavel: Define um contrato para classes que podem ser avaliadas.
 */
interface IAvaliavel {
    void receberAvaliacao(Avaliacao avaliacao);
    double calcularMediaAvaliacoes();
}