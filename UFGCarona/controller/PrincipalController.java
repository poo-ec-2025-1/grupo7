package UFGCarona.controller;

import UFGCarona.model.Carona;
import UFGCarona.model.CaronaDAO;
import UFGCarona.model.MotoristaDAO; // Ainda necessário para o save do motorista, mas não para o findById aqui.
import UFGCarona.model.MotoristaUFG; // Importar a classe MotoristaUFG
import UFGCarona.model.Usuario; // Importar a classe Usuario
import UFGCarona.view.OferecerCaronaView; // Importar a OferecerCaronaView
import UFGCarona.view.PrincipalView;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.util.List;
// import java.util.Optional; // Não é mais necessário para verificar o tipo de usuário

public class PrincipalController {
    private final PrincipalView view;
    private final CaronaDAO caronaDAO;
    private final Usuario usuarioLogado; // O usuário que fez login (agora pode ser MotoristaUFG ou Passageiro)

    // O MotoristaDAO não é mais necessário aqui para verificar o tipo de usuário,
    // mas pode ser útil para outras operações futuras que envolvam Motoristas.
    // Manter por enquanto, ou remover se não for mais usado.
    private final MotoristaDAO motoristaDAO;

    /**
     * Construtor do PrincipalController.
     * @param view A PrincipalView associada.
     * @param usuarioLogado O objeto Usuario logado na aplicação (já do tipo correto).
     */
    public PrincipalController(PrincipalView view, Usuario usuarioLogado) {
        this.view = view;
        this.caronaDAO = new CaronaDAO();
        this.usuarioLogado = usuarioLogado;
        this.motoristaDAO = new MotoristaDAO(); // Mantido por consistência, pode ser removido se não usado.
        configurarAcoes();
        carregarCaronasDisponiveis();
        // Habilita/desabilita botão de oferecer carona com base no tipo de usuário
        verificarTipoUsuarioEHabilitarBotoes();
    }

    private void configurarAcoes() {
        view.getBtnOferecerCarona().addActionListener(this::oferecerCarona);
        view.getBtnSolicitarCarona().addActionListener(this::solicitarCarona);
        // view.getBtnAvaliar().addActionListener(this::avaliarCarona); // Descomentar quando implementar
    }

    /**
     * Verifica se o usuário logado é um motorista e habilita/desabilita
     * o botão "Oferecer Carona" de acordo.
     */
    private void verificarTipoUsuarioEHabilitarBotoes() {
        // **Modificado: Usa instanceof para verificar o tipo do objeto usuarioLogado**
        boolean isMotorista = (usuarioLogado instanceof MotoristaUFG);
        view.getBtnOferecerCarona().setEnabled(isMotorista);
        System.out.println("Usuário logado: " + usuarioLogado.getEmail() + " | É Motorista? " + isMotorista);
    }

    /**
     * Carrega as caronas disponíveis do banco de dados e as exibe na lista da view.
     */
    private void carregarCaronasDisponiveis() {
        List<Carona> caronas = caronaDAO.findAvailableRides();
        view.carregarCaronas(caronas);
        System.out.println("Caronas disponíveis carregadas: " + caronas.size());
    }

    /**
     * Trata a ação de clicar no botão "Oferecer Carona".
     * Abre a tela de oferta de carona se o usuário for um motorista.
     */
    private void oferecerCarona(ActionEvent e) {
        // **Modificado: Cast seguro para MotoristaUFG**
        if (usuarioLogado instanceof MotoristaUFG) {
            MotoristaUFG motoristaLogado = (MotoristaUFG) usuarioLogado;
            // Abre a tela de Oferecer Carona como um JDialog modal
            OferecerCaronaView oferecerView = new OferecerCaronaView(view);
            new OferecerCaronaController(oferecerView, motoristaLogado, this);
            oferecerView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(view,
                "Você precisa ser um motorista para oferecer caronas.",
                "Permissão Negada",
                JOptionPane.WARNING_MESSAGE);
            view.getBtnOferecerCarona().setEnabled(false); // Garante que o botão seja desabilitado
        }
    }

    /**
     * Chamado pelo OferecerCaronaController quando uma carona é oferecida com sucesso.
     * Recarrega a lista de caronas na tela principal.
     */
    public void carregarCaronasAposOferta() {
        carregarCaronasDisponiveis();
        JOptionPane.showMessageDialog(view, "Carona oferecida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Trata a ação de clicar no botão "Solicitar Carona".
     */
    private void solicitarCarona(ActionEvent e) {
        Carona caronaSelecionada = view.getCaronaSelecionada();
        if (caronaSelecionada != null) {
            // Lógica para permitir apenas passageiros solicitarem suas caronas
            if (usuarioLogado.getId().equals(caronaSelecionada.getMotoristaId())) {
                JOptionPane.showMessageDialog(view, "Você não pode solicitar sua própria carona.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (caronaDAO.requestCarona(caronaSelecionada.getId(), usuarioLogado.getId())) {
                JOptionPane.showMessageDialog(view, "Carona solicitada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarCaronasDisponiveis(); // Atualiza a lista após a solicitação
            } else {
                JOptionPane.showMessageDialog(view, "Falha ao solicitar carona. Ela pode já ter sido solicitada, não ter vagas ou ter sido finalizada.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(view, "Selecione uma carona na lista primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}