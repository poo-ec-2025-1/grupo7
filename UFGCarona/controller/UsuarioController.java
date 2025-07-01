package UFGCarona.controller;

import UFGCarona.model.Passageiro;
import UFGCarona.model.Usuario;
import UFGCarona.model.MotoristaUFG;
import UFGCarona.model.VeiculoDAO;
import UFGCarona.model.MotoristaDAO;
import UFGCarona.model.PassageiroDAO;
import UFGCarona.model.UsuarioDAO;

public class UsuarioController {
    public boolean cadastrarUsuario(Usuario usuario, String tipoUsuario) {
        try {
            if (tipoUsuario.equalsIgnoreCase("MOTORISTA")) {
                MotoristaUFG motorista = (MotoristaUFG) usuario;
                
                  VeiculoDAO veiculoDAO = new VeiculoDAO();
                if (!veiculoDAO.save(motorista.getVeiculo())) {
                    System.err.println("Erro ao salvar veículo do motorista");
                    return false;
                }
                
           
                return new MotoristaDAO().save(motorista);
            } else {
                Passageiro passageiro = (Passageiro) usuario;
                return new PassageiroDAO().save(passageiro);
            }
        } catch (Exception e) {
            System.err.println("Erro no cadastro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return new UsuarioDAO().findByEmail(email).orElse(null);
    }
}
