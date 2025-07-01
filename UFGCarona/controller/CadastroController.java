package UFGCarona.controller;

import UFGCarona.model.*;
import UFGCarona.view.CadastroView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

public class CadastroController {
    private final CadastroView view;
    
    public CadastroController(CadastroView view) {
        this.view = view;
        configurarAcoes();
    }

    private void configurarAcoes() {
        view.getBtnCadastrar().addActionListener(this::cadastrarUsuario);
        view.getBtnCancelar().addActionListener(e -> view.dispose());
    }

    private void cadastrarUsuario(ActionEvent e) {
        try {
            if (!validarCampos()) return;

            if (view.getTipoUsuario().equals("Motorista")) {
                cadastrarMotorista();
            } else {
                cadastrarPassageiro();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, 
                ex.getMessage(), 
                "Erro no Cadastro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        if (view.getNome().isEmpty() || 
            view.getEmail().isEmpty() || 
            view.getSenha().isEmpty()) {
            
            showError("Preencha todos os campos obrigatórios");
            return false;
        }

        if (view.getTipoUsuario().equals("Motorista")) {
            if (view.getNumeroHabilitacao().isEmpty() || 
                view.getPlacaVeiculo().isEmpty()) {
                
                showError("Motoristas devem preencher habilitação e placa");
                return false;
            }
            
            if (!validarPlaca(view.getPlacaVeiculo())) {
                return false;
            }
        }
        return true;
    }

    private boolean validarPlaca(String placa) {
        String placaFormatada = placa.trim().toUpperCase();
        String regex = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$|^[A-Z]{3}-[0-9]{4}$";
        
        if (!placaFormatada.matches(regex)) {
            showError("Formato de placa inválido!\n"
                + "Use: ABC1D23 (Mercosul) ou ABC-1234 (antigo)");
            return false;
        }
        return true;
    }

    private void cadastrarMotorista() throws Exception {
        String placa = view.getPlacaVeiculo().trim().toUpperCase();
        // Verificação extra de placa existente
        Optional<Veiculo> veiculoExistente = new VeiculoDAO().findByPlaca(placa);
        if (veiculoExistente.isPresent()) {
            throw new Exception("Placa " + placa + " já está cadastrada");
        }

        
        System.out.println("--- DADOS DO VEÍCULO (CADASTRO) ---");
        System.out.println("Placa informada: " + placa);
        
        Veiculo veiculo = new Veiculo(
            placa,
            "Modelo Padrão",  // Pode substituir por campos da view
            "Marca Padrão",
            "Cor Padrão",
            2020,
            4
        );

        
        System.out.println("\n--- DADOS DO MOTORISTA (CADASTRO) ---");
        System.out.println("Nome: " + view.getNome());
        System.out.println("Email: " + view.getEmail());
        System.out.println("Telefone: " + view.getTelefone());
        System.out.println("Nº Habilitação: " + view.getNumeroHabilitacao());
        
        MotoristaUFG motorista = new MotoristaUFG(
            view.getNome(),
            view.getEmail(),
            view.getSenha(),
            view.getTelefone(),
            view.getNumeroHabilitacao(),
            "B", // Categoria padrão
            veiculo,
            gerarIdUFG(view.getTelefone())
        );
        
        System.out.println("ID gerado para o Motorista (Usuário): " + motorista.getId());
        System.out.println("Hash da Senha gerado (em Motorista/Usuario): " + motorista.getSenhaHash());
        System.out.println("ID do Veículo gerado (em Veiculo): " + motorista.getVeiculo().getId());
        System.out.println("Placa do Veículo (em Veiculo): " + motorista.getVeiculo().getPlaca());
        System.out.println("Ativo: " + motorista.isAtivo());
        

        if (!new UsuarioController().cadastrarUsuario(motorista, "MOTORISTA")) {
            throw new Exception("Falha ao finalizar cadastro do motorista");
        }

        showSuccess("Motorista cadastrado com sucesso!");
        view.dispose();
    }

    private void cadastrarPassageiro() throws Exception {
        Passageiro passageiro = new Passageiro(
            view.getNome(),
            view.getEmail(),
            view.getSenha(),
            view.getTelefone()
        );

        if (!new UsuarioController().cadastrarUsuario(passageiro, "PASSAGEIRO")) {
            throw new Exception("Falha ao cadastrar passageiro");
        }

        showSuccess("Passageiro cadastrado com sucesso!");
        view.dispose();
    }

    private String gerarIdUFG(String telefone) {
        return "UFG" + telefone.substring(telefone.length() - 4);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(view, message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}