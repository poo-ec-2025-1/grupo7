// UFGCarona.controller.OferecerCaronaController.java

package UFGCarona.controller;

import UFGCarona.model.Carona;
import UFGCarona.model.CaronaDAO;
import UFGCarona.model.MotoristaUFG;
import UFGCarona.view.OferecerCaronaView;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent; // Certifique-se que esta importação existe!
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
// import java.util.UUID; // Não precisa importar UUID aqui, pois o construtor da Carona já o gera

public class OferecerCaronaController {
    private final OferecerCaronaView view;
    private final MotoristaUFG motorista;
    private final CaronaDAO caronaDAO;
    private final PrincipalController principalController;

    public OferecerCaronaController(OferecerCaronaView view, MotoristaUFG motorista, PrincipalController principalController) {
        this.view = view;
        this.motorista = motorista;
        this.principalController = principalController;
        this.caronaDAO = new CaronaDAO();
        configurarAcoes();
    }

    private void configurarAcoes() {
        // Esta linha é a que está dando problema
        view.getBtnConfirmar().addActionListener(this::confirmarOfertaCarona);
        view.getBtnCancelar().addActionListener(e -> view.dispose());
    }

    /**
     * Trata a ação de confirmar a oferta da carona.
     * Valida os dados, cria a Carona e tenta salvá-la no banco de dados.
     * **IMPORTANTE: A assinatura deste método deve ser (ActionEvent e)!**
     */
    private void confirmarOfertaCarona(ActionEvent e) { // <-- Verifique esta linha!
        String origem = view.getTxtOrigem().getText().trim();
        String destino = view.getTxtDestino().getText().trim();
        String dataHoraStr = view.getTxtDataHora().getText().trim();
        int vagas = (int) view.getSpVagas().getValue();

        if (origem.isEmpty() || destino.isEmpty() || dataHoraStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Preencha todos os campos obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (vagas <= 0) {
            JOptionPane.showMessageDialog(view, "O número de vagas deve ser maior que zero.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDateTime dataHoraPartida;
        try {
            dataHoraPartida = LocalDateTime.parse(dataHoraStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            if (dataHoraPartida.isBefore(LocalDateTime.now())) {
                JOptionPane.showMessageDialog(view, "A data e hora da carona não pode ser no passado.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(view, "Formato de Data/Hora inválido. Use dd/MM/yyyy HH:mm (Ex: 31/12/2025 14:30).", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Carona novaCarona = new Carona(
            origem,
            destino,
            dataHoraPartida,
            motorista.getId(),
            vagas
        );

        if (caronaDAO.save(novaCarona)) {
            principalController.carregarCaronasAposOferta();
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Falha ao oferecer a carona. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}