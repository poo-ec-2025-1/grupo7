package UFGCarona.controller;

import UFGCarona.model.Carona;
import UFGCarona.model.CaronaDAO;
import UFGCarona.model.MotoristaDAO;
import UFGCarona.model.Usuario;

public class CaronaController {
    private final CaronaDAO caronaDAO;
    private final MotoristaDAO motoristaDAO;

    public CaronaController() {
        this.caronaDAO = new CaronaDAO();
        this.motoristaDAO = new MotoristaDAO();
    }

    public boolean oferecerCarona(Carona carona, Usuario motorista) {
       
        if (motoristaDAO.findById(motorista.getId()).isEmpty()) {
            return false;
        }
        return caronaDAO.save(carona);
    }

    public boolean finalizarCarona(String caronaId) {
        return caronaDAO.finalizarCarona(caronaId);
    }

    public boolean solicitarCarona(String caronaId, String passageiroId) {
        return caronaDAO.requestCarona(caronaId, passageiroId);
    }
}
