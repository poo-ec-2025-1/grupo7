package UFGCarona.view;

import UFGCarona.model.Carona; // Certifique-se de que esta importação existe
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PrincipalView extends JFrame {
    private JButton btnOferecerCarona;
    private JButton btnSolicitarCarona;
    private JButton btnAvaliar;
    private JList<String> lstCaronas;
    private List<Carona> listaCaronaObjects; // Para guardar os objetos Carona associados à lista

    public PrincipalView() {
        setTitle("UFGCarona - Painel Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel superior para botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnOferecerCarona = new JButton("Oferecer Carona");
        btnSolicitarCarona = new JButton("Solicitar Carona");
        btnAvaliar = new JButton("Avaliar Carona");

        panelBotoes.add(btnOferecerCarona);
        panelBotoes.add(btnSolicitarCarona);
        panelBotoes.add(btnAvaliar);

        // Lista de caronas disponíveis
        lstCaronas = new JList<>();
        lstCaronas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Apenas uma carona pode ser selecionada

        JScrollPane scrollPane = new JScrollPane(lstCaronas);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Caronas Disponíveis"));

        // Adicionar componentes ao JFrame
        add(panelBotoes, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Método para carregar as caronas na JList
    public void carregarCaronas(List<Carona> caronas) {
        this.listaCaronaObjects = caronas; // Armazena a lista de objetos Carona
        DefaultListModel<String> model = new DefaultListModel<>();

        if (caronas != null && !caronas.isEmpty()) {
            for (Carona carona : caronas) {
                model.addElement(formatarCarona(carona));
            }
        } else {
            model.addElement("Nenhuma carona disponível no momento.");
        }
        lstCaronas.setModel(model);
    }

    // Método auxiliar para formatar a exibição da carona na lista
    private String formatarCarona(Carona carona) {
        // Formato da data para exibição
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataHoraFormatada = carona.getDataHoraPartida().format(formatter);

        return String.format("De: %s -> Para: %s | Data/Hora: %s | Vagas: %d | Status: %s",
                carona.getOrigem(),
                carona.getDestino(),
                dataHoraFormatada,
                carona.getVagasDisponiveis(), // **Modificado: Inclui vagas**
                carona.getStatus().name());
    }

    // Método para obter a carona selecionada (útil para solicitar ou avaliar)
    public Carona getCaronaSelecionada() {
        int selectedIndex = lstCaronas.getSelectedIndex();
        if (selectedIndex != -1 && listaCaronaObjects != null && selectedIndex < listaCaronaObjects.size()) {
            // Garante que "Nenhuma carona disponível" não seja um índice válido para um objeto Carona
            if (listaCaronaObjects.isEmpty()) {
                return null;
            }
            return listaCaronaObjects.get(selectedIndex);
        }
        return null;
    }

    // Getters para os botões (para que o Controller possa adicionar ActionListeners)
    public JButton getBtnOferecerCarona() { return btnOferecerCarona; }
    public JButton getBtnSolicitarCarona() { return btnSolicitarCarona; }
    public JButton getBtnAvaliar() { return btnAvaliar; }
}