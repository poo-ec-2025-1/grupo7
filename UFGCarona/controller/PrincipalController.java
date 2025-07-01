package UFGCarona.controller;

import UFGCarona.model.Carona;
import UFGCarona.model.CaronaDAO;
import UFGCarona.model.MotoristaDAO; 
import UFGCarona.model.MotoristaUFG; 
import UFGCarona.model.Usuario;
import UFGCarona.view.OferecerCaronaView;
import UFGCarona.view.PrincipalView;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.util.List;


public class PrincipalController {
    private final PrincipalView view;
    private final CaronaDAO caronaDAO;
    private final Usuario usuarioLogado; 

    
    private final MotoristaDAO motoristaDAO;


    public PrincipalController(PrincipalView view, Usuario usuarioLogado) {
        this.view = view;
        this.caronaDAO = new CaronaDAO();
        this.usuarioLogado = usuarioLogado;
        this.motoristaDAO = new MotoristaDAO(); 
        configurarAcoes();
        carregarCaronasDisponiveis();
    
        verificarTipoUsuarioEHabilitarBotoes();
    }

    private void configurarAcoes() {
        view.getBtnOferecerCarona().addActionListener(this::oferecerCarona);
        view.getBtnSolicitarCarona().addActionListener(this::solicitarCarona);
   
    }

    private void verificarTipoUsuarioEHabilitarBotoes() {
   
        boolean isMotorista = (usuarioLogado instanceof MotoristaUFG);
        view.getBtnOferecerCarona().setEnabled(isMotorista);
        System.out.println("Usuário logado: " + usuarioLogado.getEmail() + " | É Motorista? " + isMotorista);
    }

    
    private void carregarCaronasDisponiveis() {
        List<Carona> caronas = caronaDAO.findAvailableRides();
        view.carregarCaronas(caronas);
        System.out.println("Caronas disponíveis carregadas: " + caronas.size());
    }

  
    private void oferecerCarona(ActionEvent e) {

        if (usuarioLogado instanceof MotoristaUFG) {
            MotoristaUFG motoristaLogado = (MotoristaUFG) usuarioLogado;
         
            OferecerCaronaView oferecerView = new OferecerCaronaView(view);
            new OferecerCaronaController(oferecerView, motoristaLogado, this);
            oferecerView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(view,
                "Você precisa ser um motorista para oferecer caronas.",
                "Permissão Negada",
                JOptionPane.WARNING_MESSAGE);
            view.getBtnOferecerCarona().setEnabled(false); 
        }
    }

    
    public void carregarCaronasAposOferta() {
        carregarCaronasDisponiveis();
        JOptionPane.showMessageDialog(view, "Carona oferecida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

  
    private void solicitarCarona(ActionEvent e) {
        Carona caronaSelecionada = view.getCaronaSelecionada();
        if (caronaSelecionada != null) {
         
            if (usuarioLogado.getId().equals(caronaSelecionada.getMotoristaId())) {
                JOptionPane.showMessageDialog(view, "Você não pode solicitar sua própria carona.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (caronaDAO.requestCarona(caronaSelecionada.getId(), usuarioLogado.getId())) {
                JOptionPane.showMessageDialog(view, "Carona solicitada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarCaronasDisponiveis(); 
            } else {
                JOptionPane.showMessageDialog(view, "Falha ao solicitar carona. Ela pode já ter sido solicitada, não ter vagas ou ter sido finalizada.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(view, "Selecione uma carona na lista primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}
