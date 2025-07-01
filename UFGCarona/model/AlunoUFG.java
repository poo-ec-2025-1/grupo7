package UFGCarona.model;

import java.util.ArrayList;
import java.util.List;

class AlunoUFG extends Usuario {
    private String matriculaUFG;

    public AlunoUFG(String nome, String email, String senha, String telefone, String matriculaUFG) {
        super(nome, email, senha, telefone);
        this.matriculaUFG = matriculaUFG;
    }

    public AlunoUFG(String id, String nome, String email, String senhaHash, String telefone, boolean ativo, String matriculaUFG) {
        super(id, nome, email, senhaHash, telefone, ativo);
        this.matriculaUFG = matriculaUFG;
    }

    @Override
    public void criarConta() {
        System.out.println("Conta de Aluno UFG criada para: " + this.getNome());
    }

    public String getMatriculaUFG() { return matriculaUFG; }
    public void setMatriculaUFG(String matriculaUFG) { this.matriculaUFG = matriculaUFG; }
}
