package UFGCarona.view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OferecerCaronaView extends JDialog {
    private JTextField txtOrigem;
    private JTextField txtDestino;
    private JTextField txtDataHora;
    private JSpinner spVagas;
    private JButton btnConfirmar;
    private JButton btnCancelar;

    public OferecerCaronaView(JFrame parent) {
        super(parent, "Oferecer Carona", true);
        setSize(350, 250);
        setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        
        // Componentes
        panel.add(new JLabel("Origem:"));
        txtOrigem = new JTextField();
        panel.add(txtOrigem);
        
        panel.add(new JLabel("Destino:"));
        txtDestino = new JTextField();
        panel.add(txtDestino);
        
        panel.add(new JLabel("Data/Hora:"));
        txtDataHora = new JTextField(LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        panel.add(txtDataHora);
        
        panel.add(new JLabel("Vagas disponíveis:"));
        spVagas = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        panel.add(spVagas);
        
        btnConfirmar = new JButton("Confirmar");
        btnCancelar = new JButton("Cancelar");
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnConfirmar);
        buttonPanel.add(btnCancelar);
        
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Getters
    public JTextField getTxtOrigem() { return txtOrigem; }
    public JTextField getTxtDestino() { return txtDestino; }
    public JTextField getTxtDataHora() { return txtDataHora; }
    public JSpinner getSpVagas() { return spVagas; }
    public JButton getBtnConfirmar() { return btnConfirmar; }
    public JButton getBtnCancelar() { return btnCancelar; }
}